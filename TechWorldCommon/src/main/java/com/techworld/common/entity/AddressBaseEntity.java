package com.techworld.common.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class AddressBaseEntity extends PersonalBaseEntity{
    @Column(name = "address_line", nullable = false, length = 64)
    protected String addressLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    protected Customer customer;

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
