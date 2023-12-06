package com.techworld.admin.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/products/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name){
        return productService.checkUnique(id, name);
    }

    @PostMapping("/products/delete_extra_image")
    public ResponseEntity<String> deleteExtraImageOnCloud(@RequestParam("initialImageUrl") String urlOldImage) throws IOException {
        int lastSlashIndex = urlOldImage.lastIndexOf('/');
        int lastDotIndex = urlOldImage.lastIndexOf('.');
        String fileName = urlOldImage.substring(lastSlashIndex + 1, lastDotIndex);
        cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/products/get/{id}")
    public ProductDTO getProductInfo(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = productService.get(id);
        return new ProductDTO(product.getName(), product.getMainImage(), product.getOldPrice(), product.getNewPrice());
    }

    @GetMapping("products/{id}/enabled/{status}")
    public ResponseEntity<?> updateProductEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status") boolean enabled){
        productService.updateProductEnabledStatus(id,enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The product ID " + id + " has been " + status;

        return ResponseEntity.ok(message);
    }
}
