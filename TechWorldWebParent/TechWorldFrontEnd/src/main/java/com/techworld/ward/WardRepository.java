package com.techworld.ward;

import com.techworld.common.entity.District;
import com.techworld.common.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {

    public List<Ward> findByDistrict(District district);
}
