package com.techworld.customer;

import com.techworld.common.entity.AuthenticationType;
import com.techworld.common.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CustomerRepositoryTests {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1(){
        Customer customer = new Customer();

        customer.setFullName("Nguyễn Phương Nam");
        customer.setPassword("123456789");
        customer.setEmail("21h1120046@ut.edu.vn");
        customer.setPhoneNumber("0334069054");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = customerRepository.save(customer);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCustomer2(){
        Customer customer = new Customer();

        customer.setFullName("Lê Tư Thành");
        customer.setPassword("123456789");
        customer.setEmail("thanhking2k2@gmail.com");
        customer.setPhoneNumber("0934958717");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = customerRepository.save(customer);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
    }
//
    @Test
    public void testListCustomers(){
        Iterable<Customer> customers = customerRepository.findAll();
        customers.forEach(System.out::println);
        Assertions.assertThat(customers).hasSizeGreaterThan(1);
    }
//
    @Test
    public void testUpdateCustomer(){
        Integer customerId = 1;
        String fullName = "Nguyen Phuong Thien";

        Customer customer = customerRepository.findById(customerId).get();
        customer.setFullName(fullName);
        customer.setEnabled(true);

        Customer updatedCustomer = customerRepository.save(customer);
        Assertions.assertThat(updatedCustomer.getFullName()).isEqualTo(fullName);
    }
//
    @Test
    public void testGetCustomer(){
        Integer customerId = 2;
        Optional<Customer> findById = customerRepository.findById(customerId);

        Assertions.assertThat(findById).isPresent();

        Customer customer = findById.get();
        System.out.println(customer);
    }
//
    @Test
    public void testDeleteCustomer(){
        Integer customerId = 2;
        customerRepository.deleteById(customerId);
        Optional<Customer> findById = customerRepository.findById(customerId);
        Assertions.assertThat(findById).isNotPresent();
    }
//
    @Test
    public void testFindByEmail(){
        String email = "21h1120046@ut.edu.vn";
        Customer customer = customerRepository.findByEmail(email);

        Assertions.assertThat(customer).isNotNull();
        System.out.println(customer);
    }
//
    @Test
    public void testFindByVerificationCode(){
        String code = "code_123";
        Customer customer = customerRepository.findByVerificationCode(code);

        Assertions.assertThat(customer).isNotNull();
        System.out.println(customer);
    }
//
    @Test
    public void testEnableCustomer(){
        Integer customerId = 1;
        customerRepository.enable(customerId);

        Customer customer = customerRepository.findById(customerId).get();
        Assertions.assertThat(customer.isEnabled()).isTrue();
    }
//
    @Test
    public void testUpdateAuthenticationType(){
        Integer id = 1;
        customerRepository.updateAuthenticationType(id, AuthenticationType.GOOGLE);

        Customer customer = customerRepository.findById(id).get();
        Assertions.assertThat(customer.getAuthenticationType()).isEqualTo(AuthenticationType.GOOGLE);
    }

}
