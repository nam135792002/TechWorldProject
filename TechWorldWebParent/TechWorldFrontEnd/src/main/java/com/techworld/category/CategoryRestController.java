package com.techworld.category;

import com.techworld.brand.BrandDTO;
import com.techworld.common.entity.Brand;
import com.techworld.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class CategoryRestController {
    @Autowired CategoryService categoryService;

    @GetMapping("/category/{name}/brands")
    public List<BrandDTO> listBrandsByCategories(@PathVariable(name = "name") String name) throws CategoryNotFoundException {
        List<BrandDTO> listBrandDto = new ArrayList<>();
        Category category = categoryService.getCategory(name);
        Set<Brand> brands = category.getBrands();
        for(Brand brand : brands){
            listBrandDto.add(new BrandDTO(brand.getId(), brand.getName()));
        }
        return listBrandDto;
    }
}
