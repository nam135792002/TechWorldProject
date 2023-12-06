package com.techworld.order;

import com.techworld.checkout.CheckoutInfo;
import com.techworld.common.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Address address, List<CartItem> cartItems, PaymentMethod paymentMethod, CheckoutInfo checkoutInfo, String note){
        Order newOrder = new Order();
        newOrder.setOrderTime(new Date());
        String paymentString = paymentMethod.name();

        if(paymentString.equals("VNPay")){
            newOrder.setStatus(OrderStatus.PAID);
        }else{
            newOrder.setStatus(OrderStatus.NEW);
        }

        newOrder.setCustomer(customer);
        newOrder.setTotal(checkoutInfo.getProductTotal());
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.copyShippingAddress(address);

        Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
        for (CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setSubtotal(cartItem.getSubTotal());

            orderDetails.add(orderDetail);
        }

        OrderTrack orderTrack = new OrderTrack(OrderStatus.NEW.defaultDescription(), new Date(), OrderStatus.NEW, newOrder);
        newOrder.addTrack(orderTrack);

        newOrder.setNote(note);

        return orderRepository.save(newOrder);
    }

    public List<Order> listForCustomer(Customer customer){
        return orderRepository.findAll(customer.getId());
    }

    public Order getOrder(Integer id, Customer customer){
        return orderRepository.findByIdAndCustomer(id,customer);
    }

    public void setOrderReturnRequested(OrderReturnRequest request, Customer customer) throws OrderNotFoundException {
        Order order = orderRepository.findByIdAndCustomer(request.getOrderId(),customer);
        if (order == null){
            throw new OrderNotFoundException("Order ID " + request.getOrderId() + " not found");
        }

        if (order.isReturnRequested()) return;
        OrderTrack track = new OrderTrack();
        track.setOrder(order);
        track.setUpdatedTime(new Date());
        track.setStatus(OrderStatus.RETURN_REQUESTED);

        String notes = "Reason: " + request.getReason();
        if(!"".equals(request.getNote())){
            notes += ", " + request.getNote();
        }

        track.setNotes(notes);

        order.getOrderTracks().add(track);
        order.setStatus(OrderStatus.RETURN_REQUESTED);

        orderRepository.save(order);
    }

    public void setOrderCancelRequested(OrderCancelRequest request, Customer customer) throws OrderNotFoundException {
        Order order = orderRepository.findByIdAndCustomer(request.getOrderId(),customer);
        if (order == null){
            throw new OrderNotFoundException("Order ID " + request.getOrderId() + " not found");
        }

        if (order.isCanceled()) return;
        OrderTrack track = new OrderTrack();
        track.setOrder(order);
        track.setUpdatedTime(new Date());
        track.setStatus(OrderStatus.CANCELED);

        String notes = "";
        if(!"".equals(request.getNote())){
            notes += request.getNote();
        }else{
            notes = null;
        }

        track.setNotes(notes);

        order.getOrderTracks().add(track);
        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);
    }
}
