package com.techworld.common.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wards")
public class Ward {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> listAddresses = new ArrayList<>();

    @Column(nullable = false, length = 64)
    private String name;

    public Ward() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ward{" +
                "id=" + id +
                ", district=" + district +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Address> getListAddresses() {
        return listAddresses;
    }

    public void setListAddresses(List<Address> listAddresses) {
        this.listAddresses = listAddresses;
    }
}
