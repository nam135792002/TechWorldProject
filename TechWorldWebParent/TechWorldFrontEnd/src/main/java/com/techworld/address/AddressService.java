package com.techworld.address;

import com.techworld.common.entity.Address;
import com.techworld.common.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressService {
    @Autowired private AddressRepository repository;

    public List<Address> listAddressBook(Customer customer){
        return repository.findByCustomer(customer);
    }

    public void save(Address address){
        repository.save(address);
    }

    public Address get(Integer addressId, Integer customerId){
        return repository.findByIdAndCustomer(addressId,customerId);
    }

    public void delete(Integer addressId, Integer customerId){
        repository.deleteByIdAndCustomer(addressId,customerId);
    }

    public void setDefaultAddress(Integer defaultAddressId, Integer customerId){
        if(defaultAddressId > 0){
            repository.setDefaultAddress(defaultAddressId);
        }
        repository.setNonDefaultForOthers(defaultAddressId,customerId);

    }

    public Address getDefaultAddress(Customer customer){
        return repository.findDefaultCustomer(customer.getId());
    }
}
