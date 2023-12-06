package com.techworld.district;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Ward;
import com.techworld.ward.WardDTO;
import com.techworld.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DistrictRestController {
    @Autowired WardService wardService;
    @Autowired DistrictService districtService;

    @GetMapping("/district/{districtId}/wards")
    public List<WardDTO> listWardByDistrict(@PathVariable(name = "districtId") Integer districtId){
        List<WardDTO> listWard = new ArrayList<>();
        District district = districtService.get(districtId);
        List<Ward> wards = wardService.listWardByDistrict(district);
        for(Ward ward : wards){
            listWard.add(new WardDTO(ward.getId(), ward.getName()));
        }

        return listWard;
    }
}
