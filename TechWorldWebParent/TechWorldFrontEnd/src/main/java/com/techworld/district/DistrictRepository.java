package com.techworld.district;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    public List<District> findByProvince(Province province);
}
