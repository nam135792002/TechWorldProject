package com.techworld.category;

import com.techworld.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired private CategoryRepository categoryRepository;

    public List<Category> listCategories(){
        return categoryRepository.findAllEnabled();
    }

    public Category getCategory(String name) throws CategoryNotFoundException {
        Category category = categoryRepository.findByNameEnabled(name);
        if(category == null){
            throw new CategoryNotFoundException("Could not find any categories with alias " + name);
        }
        return category;
    }
}
