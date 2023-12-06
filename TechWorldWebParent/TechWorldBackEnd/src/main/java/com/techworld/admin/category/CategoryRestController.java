package com.techworld.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name){
        return categoryService.checkUnique(id,name);
    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public ResponseEntity<String> updateCategoryEnabledStatus(@PathVariable("id") Integer id,
                                                              @PathVariable("status") boolean enabled){
        categoryService.updateCategoryEnabledStatus(id,enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The category ID " + id + " has been " + status;
        return ResponseEntity.ok(message);
    }
}
