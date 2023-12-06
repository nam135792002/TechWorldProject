package com.techworld.admin.category;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.common.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Cloudinary cloudinary;

    public List<Category> listAll(){
        return categoryRepository.findAll();
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }


    public Category get(Integer id) throws CategoryNotFoundException {
        try{
            return categoryRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
    }

    public void deleteImageInCloudinary(Category category) throws IOException {
        String urlTemp = category.getImage();
        int lastSlashIndex = urlTemp.lastIndexOf('/');
        int lastDotIndex = urlTemp.lastIndexOf('.');
        String fileName = urlTemp.substring(lastSlashIndex + 1, lastDotIndex);
        System.out.println(fileName);
        cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
    }

    public String checkUnique(Integer id, String name){
        boolean isCreatingNew = (id == null || id == 0);
        Category categoryByName = categoryRepository.findByName(name);
        if(isCreatingNew && categoryByName != null){
            return "DuplicateName";
        }else if(categoryByName != null && categoryByName.getId() != id){
            return "DuplicateName";
        }
        return "OK";
    }

    public void updateCategoryEnabledStatus(Integer id, boolean enabled){
        categoryRepository.updateEnabledStatus(id,enabled);
    }

    public void delete(Integer id) throws CategoryNotFoundException{
        Long countById = categoryRepository.countById(id);
        if(countById == null || countById == 0){
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
        categoryRepository.deleteById(id);
    }

    public long countCategory(){
        return categoryRepository.count();
    }
}
