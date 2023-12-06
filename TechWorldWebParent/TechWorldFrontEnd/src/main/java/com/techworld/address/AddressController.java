package com.techworld.address;

import com.techworld.Utility;
import com.techworld.common.entity.Address;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Province;
import com.techworld.customer.CustomerService;
import com.techworld.province.ProvinceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AddressController {

    @Autowired private AddressService addressService;
    @Autowired private CustomerService customerService;
    @Autowired private ProvinceService provinceService;

    @GetMapping("/address_book")
    public String showAddressBook(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatsCustomer(request);
        List<Address> listAddresses = addressService.listAddressBook(customer);
        boolean usePrimaryAddressAsDefault = true;
        for(Address address : listAddresses){
            if(address.isDefaultForShipping()){
                usePrimaryAddressAsDefault = false;
                break;
            }
        }

        model.addAttribute("listAddresses",listAddresses);
        model.addAttribute("customer",customer);
        model.addAttribute("usePrimaryAddressAsDefault",usePrimaryAddressAsDefault);
        return "address_book/addresses";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/address_book/new")
    public String newAddress(Model model, HttpServletRequest request){
        List<Province> listProvinces = provinceService.listAll();

        model.addAttribute("listProvinces",listProvinces);
        model.addAttribute("address", new Address());
        model.addAttribute("pageTitle","Add New Address");

        return "address_book/address_form";
    }

    @PostMapping("/address_book/save")
    public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes rs){
        Customer customer = getAuthenticatsCustomer(request);

        address.setCustomer(customer);

        System.out.println(address);

        addressService.save(address);

        rs.addFlashAttribute("message","The address has been saved successfully.");

        return "redirect:/address_book";
    }

    @GetMapping("/address_book/edit/{id}")
    public String editAddress(@PathVariable("id") Integer addressId, Model model, HttpServletRequest request){
        Customer customer = getAuthenticatsCustomer(request);

        Address address = addressService.get(addressId,customer.getId());
        List<Province> listProvinces = provinceService.listAll();

        model.addAttribute("listProvinces",listProvinces);

        model.addAttribute("address",address);
        model.addAttribute("pageTitle","Edit Address (ID: " + addressId + ")");

        return "address_book/address_form";
    }

    @GetMapping("/address_book/delete/{id}")
    public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes rs, HttpServletRequest request){
        Customer customer = getAuthenticatsCustomer(request);
        addressService.delete(addressId,customer.getId());
        rs.addFlashAttribute("message","The address ID " + addressId + " has been deleted.");

        return "redirect:/address_book";
    }

    @GetMapping("/address_book/default/{id}")
    public String setDefaultAddress(@PathVariable("id") Integer addressId, RedirectAttributes rs, HttpServletRequest request){
        Customer customer = getAuthenticatsCustomer(request);
        addressService.setDefaultAddress(addressId,customer.getId());

        String redirectOption = request.getParameter("redirect");
        String redirectURL = "redirect:/address_book";

        if("cart".equals(redirectOption)){
            redirectURL = "redirect:/cart";
        }
        return redirectURL;
    }
}
