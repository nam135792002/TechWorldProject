package com.techworld.admin.customer;

import com.techworld.admin.Message;
import com.techworld.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired private CustomerService customerService;

    @GetMapping("/customers")
    public String listFirstPage(Model model){
        List<Customer> listCustomers = customerService.listByPage();
        model.addAttribute("listCustomers",listCustomers);
        return "customers/customers";
    }

    @GetMapping("/customers/detail/{id}")
    public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            Customer customer = customerService.get(id);
            model.addAttribute("customer",customer);

            return "customers/customer_detail_modal";
        }catch (CustomerNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",new Message("error", ex.getMessage()));
            return "redirect:/customers";
        }
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try {
            customerService.delete(id);
            redirectAttributes.addFlashAttribute("message",new Message("success","The customer ID " + id + " has been deleted successfully"));
        } catch (CustomerNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",new Message("error", e.getMessage()));
        }
        return "redirect:/customers";
    }
}
