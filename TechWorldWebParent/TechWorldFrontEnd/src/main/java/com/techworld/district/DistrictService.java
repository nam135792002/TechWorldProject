package com.techworld.district;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired private DistrictRepository districtRepository;

    public List<District> listDistrictByProvince(Province province){
        return districtRepository.findByProvince(province);
    }

    public District get(Integer id){
        return districtRepository.findById(id).get();
    }
}
