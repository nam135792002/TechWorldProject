package com.techworld.product;

import com.techworld.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {
    @Autowired ProductService productService;

    @GetMapping("/api/search/{keyword}")
    public List<ProductDTO> listProduct(@PathVariable(name = "keyword") String keyword){
        List<ProductDTO> listProductDTOs = new ArrayList<>();
        try {
            List<Product> listProducts = productService.listProductBySearchKeyWord(keyword);
            for (Product p : listProducts){
                listProductDTOs.add(new ProductDTO(p.getId(), p.getName(), p.getAlias(), p.getMainImage(), p.getOldPrice(), p.getNewPrice(), p.getAverageRating(), p.getReviewCount()));
            }
//            return listProductDTOs;
        }catch (Exception e){
            e.printStackTrace();
        }
        return listProductDTOs;
    }

    @GetMapping("/products/sort/{categoryName}/{brandId}/{sortId}")
    public List<ProductDTO> listProductDTOSort(@PathVariable(name = "categoryName") String categoryName,
                                               @PathVariable(name = "brandId") Integer brandId,
                                               @PathVariable(name = "sortId") Integer sortId){
        List<ProductDTO> lisProductDTO = new ArrayList<>();
        List<Product> listProducts = productService.listPageProduct(categoryName, brandId, sortId);
        for (Product p : listProducts){
            lisProductDTO.add(new ProductDTO(p.getId(), p.getName(), p.getAlias(), p.getMainImage(), p.getOldPrice(), p.getNewPrice(), p.getDiscountPercent(), p.getAverageRating(), p.getReviewCount()));
        }

        return lisProductDTO;
    }
}
