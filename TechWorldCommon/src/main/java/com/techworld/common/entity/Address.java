package com.techworld.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends AddressBaseEntity{

    @Column(name = "full_name", nullable = false, length = 45)
    private String fullName;


    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(name = "default_address")
    private boolean defaultForShipping;

    public Address() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public boolean isDefaultForShipping() {
        return defaultForShipping;
    }

    public void setDefaultForShipping(boolean defaultForShipping) {
        this.defaultForShipping = defaultForShipping;
    }

    @Override
    public String toString() {
        return fullName + ", " + email + ", " + phoneNumber + ", " + addressLine + ", "
                + ward.getName() + ", " + district.getName() + ", " + province.getName();
    }

    @Transient
    public String getAddreessLine(){
        return addressLine + ", " + ward.getName();
    }

    @Transient
    public String getProvinceWard(){
        return district.getName() + ", " + province.getName() + ", Việt Nam";
    }
}
