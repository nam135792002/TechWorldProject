package com.techworld.admin.customer;

import com.techworld.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer  c where c.email = ?1")
    public Customer findByEmail(String email);

    public Long countById(Integer id);

    @Query("update Customer  c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
