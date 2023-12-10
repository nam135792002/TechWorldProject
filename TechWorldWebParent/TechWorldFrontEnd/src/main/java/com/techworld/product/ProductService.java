package com.techworld.product;

import com.techworld.category.CategoryService;
import com.techworld.common.entity.Category;
import com.techworld.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired ProductRepository productRepository;
    @Autowired CategoryService categoryService;

    public List<Product> findOneProductForCategory(List<Category> listCategories){
        List<Product> products = new ArrayList<>();
        listCategories.forEach(category -> {
            List<Product> listProducts = new ArrayList<>();
            listProducts = productRepository.findProductByCategory(category.getId());
            if(!listProducts.isEmpty()){
                products.add(listProducts.get(0));
            }
        });

        return products;
    }

    public List<Product> findProductByCategory(Integer idCategory){
        return productRepository.findProductByCategory(idCategory);
    }

    public List<Product> findProductByDate(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(2023, Calendar.NOVEMBER,13);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar.getTime();
        System.out.println(startDate);

        calendar.set(2023, Calendar.NOVEMBER,14);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);

        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return productRepository.findProductByDate(startDate, endDate);
    }

    public List<Product> findProductByDiscountPercent(){
        return productRepository.findProductByDiscountPercent(0.4f,0.5f);
    }

    public Product findProductById(Integer id){
        return productRepository.findById(id).get();
    }

    public Product getProduct(String alias) throws ProductNotFoundException {
        Product product = productRepository.findByAlias(alias);
        if(product == null){
            throw new ProductNotFoundException("Could not find any product with alias " + alias);
        }
        return product;
    }

    public List<Product> listProductBySearchKeyWord(String keyword){
        return productRepository.search(keyword);
    }

    public List<Product> listProductByCategoryName(String name){
        return productRepository.findProductByCategoryName(name);
    }

    public List<Product> listPageProduct(String categoryName, Integer brandId, Integer sortId) {
        if(brandId != 0){
            if (sortId >= 1 && sortId <= 4) {
                Sort sort = styleSortProduct(sortId);
                return productRepository.sortProductByCategoryAndBrand(categoryName, brandId, sort);
            } else {
                return productRepository.findProductByCategoryName(categoryName);
            }
        }else{
            if (sortId >= 1 && sortId <= 4) {
                Sort sort = styleSortProduct(sortId);
                return productRepository.findProductByCategoryName(categoryName, sort);
            } else {
                return productRepository.sortProductByCategoryAndBrand(categoryName, brandId);
            }
        }
    }

    private Sort styleSortProduct(Integer sortId){
        Sort sort;
        if (sortId == 1 || sortId == 2) {
            sort = Sort.by("name");
            sort = (sortId == 1 ? sort.ascending() : sort.descending());
        } else if (sortId == 3 || sortId == 4) {
            sort = Sort.by("newPrice");
            sort = (sortId == 3 ? sort.ascending() : sort.descending());
        }else {
            // Handle the case where sortId is not 1, 2, 3, or 4
            // You might want to provide a default sort in this case.
            sort = Sort.unsorted();
        }

        return sort;
    }

    public Product getProduct(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id).get();
    }

}
