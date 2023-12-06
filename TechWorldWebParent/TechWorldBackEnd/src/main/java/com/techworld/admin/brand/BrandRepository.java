package com.techworld.admin.brand;

import com.techworld.common.entity.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Long countById(Integer id);

    public Brand findByName(String name);

    @Query("select new Brand(b.id, b.name) from Brand b order by b.name ASC")
    public List<Brand> findAllTemp();
}
