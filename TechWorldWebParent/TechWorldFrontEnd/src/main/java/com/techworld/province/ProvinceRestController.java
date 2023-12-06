package com.techworld.province;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Province;
import com.techworld.district.DistrictDTO;
import com.techworld.district.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProvinceRestController {
    @Autowired private DistrictService districtService;
    @Autowired private ProvinceService provinceService;

    @GetMapping("/province/{provinceId}/districts")
    public List<DistrictDTO> listDistrict(@PathVariable(name = "provinceId") Integer provinceId){
        List<DistrictDTO> lisDistrict = new ArrayList<>();
        Province province = provinceService.get(provinceId);
        List<District> districts = districtService.listDistrictByProvince(province);
        for (District district : districts){
            lisDistrict.add(new DistrictDTO(district.getId(),district.getName()));
        }
        return lisDistrict;
    }
}
