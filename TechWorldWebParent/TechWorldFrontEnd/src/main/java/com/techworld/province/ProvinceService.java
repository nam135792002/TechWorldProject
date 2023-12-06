package com.techworld.province;

import com.techworld.common.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired ProvinceRepository provinceRepository;

    public List<Province> listAll(){
        return provinceRepository.findAll();
    }

    public Province get(Integer id){
        return provinceRepository.findById(id).get();
    }
}
