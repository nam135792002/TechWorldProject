package com.techworld.customer;

import com.techworld.common.entity.AuthenticationType;
import com.techworld.common.entity.Customer;
import jakarta.transaction.Transactional;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired PasswordEncoder passwordEncoder;

    public boolean isEmailUnique(String email){
        Customer customer = customerRepository.findByEmail(email);
        return customer == null;
    }

    public void registerCustomer(Customer customer){
        encodePassword(customer);
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(AuthenticationType.DATABASE);

        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode);
        customerRepository.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }

    public boolean verify(String verificationCode){
        Customer customer = customerRepository.findByVerificationCode(verificationCode);

        if(customer == null || customer.isEnabled()){
            return false;
        }else{
            customerRepository.enable(customer.getId());
            return true;
        }
    }

    public void updateAuthenticationType(Customer customer, AuthenticationType type){
        if(!customer.getAuthenticationType().equals(type)){
            customerRepository.updateAuthenticationType(customer.getId(), type);
        }
    }

    public Customer getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public void addNewCustomerUponOAuthLogin(String name, String email, AuthenticationType authenticationType) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFullName(name);
        customer.setEnabled(true);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(authenticationType);
        customer.setPassword("");
        customer.setPhoneNumber("");
        customerRepository.save(customer);
    }

    public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            String token = RandomString.make(30);
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
            return token;
        }else{
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public Customer getByResetPasswordToken(String token){
        return customerRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByResetPasswordToken(token);
        if(customer == null){
            throw new CustomerNotFoundException("No customer found: invalid token");
        }
        customer.setPassword(newPassword);
        customer.setResetPasswordToken(null);
        encodePassword(customer);
        customerRepository.save(customer);
    }

    public void update(Customer customerInform){
        Customer customerInDB = customerRepository.findById(customerInform.getId()).get();
        if(customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)){
            if(!customerInform.getPassword().isEmpty()){
                String encodedPassword = passwordEncoder.encode(customerInform.getPassword());
                customerInform.setPassword(encodedPassword);
            } else {
                customerInform.setPassword(customerInDB.getPassword());
            }
        }else{
            customerInform.setPassword(customerInDB.getPassword());
        }
        customerInform.setEnabled(customerInDB.isEnabled());
        customerInform.setCreatedTime(customerInDB.getCreatedTime());
        customerInform.setVerificationCode(customerInDB.getVerificationCode());
        customerInform.setResetPasswordToken(customerInDB.getResetPasswordToken());
        customerInform.setAuthenticationType(customerInDB.getAuthenticationType());
        customerRepository.save(customerInform);
    }
}
