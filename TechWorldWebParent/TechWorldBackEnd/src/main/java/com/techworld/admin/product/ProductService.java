package com.techworld.admin.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Cloudinary cloudinary;
    public List<Product> listAll(){
        return productRepository.findAll();
    }

    public Product save(Product product){
        if(product.getId() == null){
            product.setCreatedTime(new Date());
        }else{
            Product product1 = productRepository.findById(product.getId()).get();
            product.setCreatedTime(product1.getCreatedTime());
        }

        String defaultAlias = product.getName().replaceAll(" ","-");
        product.setAlias(defaultAlias);

        product.setUpdatedTime(new Date());
        Product updatedProduct = productRepository.save(product);
        productRepository.updateReviewCountAndAverageRating(updatedProduct.getId());

        return updatedProduct;
    }

    public void saveProductPrice(Product product){
        Product productInDB = productRepository.findById(product.getId()).get();
        productInDB.setNewPrice(product.getNewPrice());
        productInDB.setOldPrice(product.getOldPrice());
        productInDB.setDiscountPercent(product.getDiscountPercent());
        productRepository.save(productInDB);
    }

    public String checkUnique(Integer id, String name){
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = productRepository.findByName(name);
        if(isCreatingNew){
            if(productByName != null) return "Duplicate";
        }else{
            if(productByName != null && !Objects.equals(productByName.getId(), id)){
                return "Duplicate";
            }
        }
        return "OK";
    }

    public void updateProductEnabledStatus(Integer id, boolean enabled){
        productRepository.updateEnabledStatus(id,enabled);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
        productRepository.deleteById(id);
    }

    public Product get(Integer id) throws ProductNotFoundException {
        try{
            return productRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
    }

    public void deleteImageInCloudinary(Product product) throws IOException {
        String urlTemp = product.getMainImage();
        int lastSlashIndex = urlTemp.lastIndexOf('/');
        int lastDotIndex = urlTemp.lastIndexOf('.');
        String fileName = urlTemp.substring(lastSlashIndex + 1, lastDotIndex);
        cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
    }

    public void deleteImageExtraInCloudinary(Integer id) throws IOException {
        List<ProductImage> listProductImages = productRepository.findExtraImageByProduct(id);
        for(ProductImage productImage : listProductImages){
            String urlTemp = productImage.getUrl();
            int lastSlashIndex = urlTemp.lastIndexOf('/');
            int lastDotIndex = urlTemp.lastIndexOf('.');
            String fileName = urlTemp.substring(lastSlashIndex + 1, lastDotIndex);
            cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
        }
    }

    public List<ProductImage> findAllExtraImage(Integer id){
        return productRepository.findExtraImageByProduct(id);
    }

    public long countByImageExtra(Integer id){
        return productRepository.countImageExtraById(id);
    }

    public long countProduct(){
        return productRepository.count();
    }
}
