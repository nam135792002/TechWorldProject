package com.techworld.admin.customer;

import com.techworld.admin.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CustomerRestController {
    @Autowired  private CustomerService customerService;

    @GetMapping("/customers/{id}/enabled/{status}")
    public ResponseEntity<?> updateCustomerEnabledStatus(@PathVariable("id") Integer id,
                                                      @PathVariable("status") boolean enabled,
                                                      RedirectAttributes redirectAttributes){
        customerService.updateCustomerEnabledStatus(id,enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The customer ID " + id + " has been " + status;
        return ResponseEntity.ok(message);
    }
}
