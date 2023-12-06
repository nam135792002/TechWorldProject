package com.techworld.admin.order;

import com.techworld.admin.Message;
import com.techworld.common.entity.Order;
import com.techworld.common.entity.OrderStatus;
import com.techworld.common.entity.OrderTrack;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class OrderController {

    @Autowired private OrderService orderService;

    @GetMapping("/orders")
    public String listFirstPage(Model model){
        List<Order> listOrders = orderService.listAll();

        model.addAttribute("listOrders",listOrders);
        return "orders/orders";
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer id, Model model,
                                   RedirectAttributes rs, HttpServletRequest request){
        try {
            Order order = orderService.get(id);
            model.addAttribute("order",order);

            return "orders/order_detail_modal";
        } catch (OrderNotFoundException e) {
            rs.addFlashAttribute("message", new Message("error",e.getMessage()));
            return "redirect:/orders";
        }
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, RedirectAttributes rs){
        try {
            orderService.delete(id);
            rs.addFlashAttribute("message",new Message("success", "The order ID " + id + " has been deleted."));
        }catch (OrderNotFoundException ex){
            rs.addFlashAttribute("message", new Message("error",ex.getMessage()));
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try{
            Order order = orderService.get(id);
            model.addAttribute("pageTitle","Edit Order (ID: " + id + ")");
            model.addAttribute("order",order);

            return "orders/order_form";
        }catch (OrderNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", new Message("error",ex.getMessage()));
            return "redirect:/orders";
        }
    }

    @PostMapping("/orders/save")
    public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ParseException {
        updateOrderTrack(order, request);

        orderService.save(order);
        redirectAttributes.addFlashAttribute("message",new Message("success","The order ID " + order.getId() + " has been updated successfully."));
        return "redirect:/orders";
    }

    private void updateOrderTrack(Order order, HttpServletRequest request) throws ParseException {
        String[] trackIds = request.getParameterValues("trackId");
        String[] trackStatuses = request.getParameterValues("trackStatus");
        String[] trackNotes = request.getParameterValues("trackNotes");
        String[] trackDates = request.getParameterValues("trackDate");

        List<OrderTrack> orderTracks = order.getOrderTracks();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        for (int i = 0; i < trackIds.length; i++){
            OrderTrack orderTrack = new OrderTrack();
            Integer trackId = Integer.parseInt(trackIds[i]);
            if (trackId > 0){
                orderTrack.setId(trackId);
            }

            orderTrack.setOrder(order);
            orderTrack.setStatus(OrderStatus.valueOf(trackStatuses[i]));
            orderTrack.setNotes(trackNotes[i]);
            System.out.println(dateFormat.parse(trackDates[i]));
            orderTrack.setUpdatedTime(dateFormat.parse(trackDates[i]));
            System.out.println(trackDates[i]);
            orderTracks.add(orderTrack);
        }
    }
}
