package com.techworld.ward;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Ward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService {
    @Autowired private WardRepository wardRepository;

    public List<Ward> listWardByDistrict(District district){
        return wardRepository.findByDistrict(district);
    }
}
