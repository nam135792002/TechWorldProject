package com.techworld.province;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Province;
import com.techworld.common.entity.Ward;
import com.techworld.district.DistrictRepository;
import com.techworld.ward.WardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProvinceRepositoryTest {
    @Autowired private ProvinceRepository provinceRepository;
    @Autowired private DistrictRepository districtRepository;
    @Autowired private WardRepository wardRepository;

    @Test
    public void testListAllProvince(){
        District  district = new District(402);

        List<Ward> wards = wardRepository.findByDistrict(district);
        for (Ward ward : wards){
            System.out.println("Name: " + ward.getName());
        }
    }
}
