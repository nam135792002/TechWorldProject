package com.techworld.order;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    @Autowired private OrderService orderService;
    @Autowired private CustomerService customerService;

    @PostMapping("/orders/return")
    public ResponseEntity<?> handOrderReturnRequest(@RequestBody OrderReturnRequest returnRequest, HttpServletRequest servletRequest){
        Customer customer = null;

        try {
            customer = getAuthenticatedCustomer(servletRequest);
        }catch (CustomerNotFoundException ex){
            return new ResponseEntity<>("Authenticated required", HttpStatus.BAD_REQUEST);
        }

        try{
            orderService.setOrderReturnRequested(returnRequest,customer);
        }catch (OrderNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new OrderReturnResponse(returnRequest.getOrderId()),HttpStatus.OK);
    }

    @PostMapping("/orders/cancel")
    public ResponseEntity<?> handOrderCancelRequest(@RequestBody OrderCancelRequest cancelRequest, HttpServletRequest servletRequest){
        Customer customer = null;

        try {
            customer = getAuthenticatedCustomer(servletRequest);
        }catch (CustomerNotFoundException ex){
            return new ResponseEntity<>("Authenticated required", HttpStatus.BAD_REQUEST);
        }

        try{
            orderService.setOrderCancelRequested(cancelRequest,customer);
        }catch (OrderNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new OrderCancelResponse(cancelRequest.getOrderId()),HttpStatus.OK);
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if(email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }

        return customerService.getCustomerByEmail(email);
    }

}
