package com.techworld.admin.order;

import com.techworld.common.entity.Order;
import com.techworld.common.entity.OrderStatus;
import com.techworld.common.entity.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    @Autowired private OrderRepository repository;

    public List<Order> listAll(){
        return repository.findAll();
    }

    public Order get(Integer id) throws OrderNotFoundException {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new OrderNotFoundException("Could not find any orders with ID " + id);
        }
    }

    public void delete(Integer id) throws OrderNotFoundException {
        Long count = repository.countById(id);
        if(count == null || count == 0){
            throw new OrderNotFoundException("Could not find any orders with ID " + id);
        }
        repository.deleteById(id);
    }

    public Order save(Order orderInForm){
        Order orderInDB = repository.findById(orderInForm.getId()).get();

        orderInForm.setOrderTime(orderInDB.getOrderTime());
        orderInForm.setCustomer(orderInDB.getCustomer());
        orderInForm.setAddressLine(orderInDB.getAddressLine());
        orderInForm.setDistrict(orderInDB.getDistrict());
        orderInForm.setEmail(orderInDB.getEmail());
        orderInForm.setFullName(orderInDB.getFullName());
        orderInForm.setPaymentMethod(orderInDB.getPaymentMethod());
        orderInForm.setPhoneNumber(orderInDB.getPhoneNumber());
        orderInForm.setProvince(orderInDB.getProvince());
        orderInForm.setTotal(orderInDB.getTotal());
        orderInForm.setWard(orderInDB.getWard());
        orderInForm.setNote(orderInDB.getNote());
        orderInForm.setOrderDetails(orderInDB.getOrderDetails());

        return repository.save(orderInForm);
    }

    public Long countOrder(){
        return repository.count();
    }
    public int totalIncome(){
        return repository.sumIncome();
    }

    public List<Order> listOrderByNewStatus(){
        return repository.findOrderByStatus(OrderStatus.NEW);
    }

    public Map<String, String> getValuePercent(){
        Map<String, String> valuePercent = new HashMap<>();
        List<Order> listOrderByCanceled = repository.findOrderByStatus(OrderStatus.CANCELED);
        List<Order> listOrderByCompleted = repository.findOrderByStatus(OrderStatus.DELIVERED);
        long listOrder = repository.count();

        double countOrderByCanceled = listOrderByCanceled.size() * 100.0 / listOrder;
        double countOrderByCompleted = listOrderByCompleted.size() * 100.0 / listOrder;
        double countOrderByInProgress = (listOrder - listOrderByCanceled.size() - listOrderByCompleted.size()) * 100.0 / listOrder;

        // Format percentages to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedCountOrderByCanceled = df.format(countOrderByCanceled);
        String formattedCountOrderByCompleted = df.format(countOrderByCompleted);
        String formattedCountOrderByInProgress = df.format(countOrderByInProgress);

        valuePercent.put("formattedCountOrderByCanceled", formattedCountOrderByCanceled);
        valuePercent.put("formattedCountOrderByCompleted", formattedCountOrderByCompleted);
        valuePercent.put("formattedCountOrderByInProgress", formattedCountOrderByInProgress);

        return valuePercent;
    }

    public Map<String, String> getValuePayment(){
        Map<String, String> valuePercent = new HashMap<>();
        List<Order> listOrderByCOD = repository.findOrderByPayment(PaymentMethod.COD);
        List<Order> listOrderByVnPay = repository.findOrderByPayment(PaymentMethod.VNPay);
        long listOrder = repository.count();

        double countOrderByCOD = listOrderByCOD.size() * 100.0 / listOrder;
        double countOrderByVnPay = listOrderByVnPay.size() * 100.0 / listOrder;

        DecimalFormat df = new DecimalFormat("0.00");
        String formattedCountOrderByCOD = df.format(countOrderByCOD);
        String formattedCountOrderByVnPay = df.format(countOrderByVnPay);

        valuePercent.put("formattedCountOrderByCOD", formattedCountOrderByCOD);
        valuePercent.put("formattedCountOrderByVnPay", formattedCountOrderByVnPay);

        return valuePercent;
    }
}
