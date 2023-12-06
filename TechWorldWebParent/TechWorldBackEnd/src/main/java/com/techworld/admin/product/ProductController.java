package com.techworld.admin.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.admin.Message;
import com.techworld.admin.brand.BrandService;
import com.techworld.admin.category.CategoryService;
import com.techworld.admin.security.TechWorldUserDetails;
import com.techworld.common.entity.Brand;
import com.techworld.common.entity.Category;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String listFirstPage(Model model){
        List<Product> listProduct = productService.listAll();
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listProduct",listProduct);
        model.addAttribute("listCategories",listCategories);

        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model){
        List<Brand> listBrands = brandService.listAllTemp();
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);
        int numberOfExistingExtraImages = 0;

        model.addAttribute("numberOfExistingExtraImages",numberOfExistingExtraImages);
        model.addAttribute("product",product);
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("pageTitle","Create New Product");
        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes,
                              @RequestParam(value = "fileImage",required = false) MultipartFile multipartFile,
                              @RequestParam(value = "extraImage",required = false) MultipartFile[] extraMultipartFile,
                              @RequestParam(name = "detailIDs",required = false) String[] detailIDs,
                              @RequestParam(name = "detailNames",required = false) String[] detailNames,
                              @RequestParam(name = "detailValues",required = false) String[] detailValues,
                              @RequestParam(name = "imageIDs",required = false) String[] imageIDs,
                              @RequestParam(name = "imageName",required = false) String[] imageName,
                              @RequestParam(name = "imageUrl",required = false) String[] imageUrl,
                              @RequestParam(name = "deletedImageIds",required = false) String[] deletedImageIds,
                              @AuthenticationPrincipal TechWorldUserDetails loggedUser
                              ) throws ProductNotFoundException, IOException {
        if(loggedUser.hasRole("Salesperson")){
            productService.saveProductPrice(product);
            redirectAttributes.addFlashAttribute("message", new Message("success","The product has been saved successfully."));
            return "redirect:/products";
        }
        if(0 < deletedImageIds.length){
            for(String url : deletedImageIds){
                int lastSlashIndex = url.lastIndexOf('/');
                int lastDotIndex = url.lastIndexOf('.');
                String fileUrlOld = url.substring(lastSlashIndex + 1, lastDotIndex);
                cloudinary.uploader().destroy(fileUrlOld, ObjectUtils.asMap("resource_type","image"));
            }
        }
        if(!multipartFile.isEmpty()){
            if(product.getId() != null && productService.get(product.getId()).getMainImage() != null){
                productService.deleteImageInCloudinary(product);
            }
            try{
                Map r = this.cloudinary.uploader().upload(multipartFile.getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                String img = (String) r.get("secure_url");
                product.setMainImage(img);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            if(product.getId() == null || productService.get(product.getId()).getMainImage() == null){
                product.setMainImage(null);
            }
        }

        if(imageIDs != null && imageName.length != 0){
            Set<ProductImage> images = new HashSet<>();
            for(int i = 0; i < imageName.length; i++){
                Integer id = Integer.parseInt(imageIDs[i]);
                String name = imageName[i];
                String url = imageUrl[i];
                images.add(new ProductImage(id,name,url,product));
            }
            product.setImages(images);
            if(0 < extraMultipartFile.length){
                for(MultipartFile file : extraMultipartFile){
                    if(!file.isEmpty()){
                        try{
                            String fileName = file.getOriginalFilename();
                            if(product.containsImageName(fileName)){
                                Map r = this.cloudinary.uploader().upload(file.getBytes(),
                                        ObjectUtils.asMap("resource_type","auto"));
                                String fileUrl = (String) r.get("secure_url");
                                String oldUrl = product.changeExtraImage(fileUrl,fileName);
                                if(oldUrl != null){
                                    int lastSlashIndex = oldUrl.lastIndexOf('/');
                                    int lastDotIndex = oldUrl.lastIndexOf('.');
                                    String fileUrlOld = oldUrl.substring(lastSlashIndex + 1, lastDotIndex);
                                    cloudinary.uploader().destroy(fileUrlOld, ObjectUtils.asMap("resource_type","image"));
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if(0 < extraMultipartFile.length){
            for(MultipartFile file : extraMultipartFile){
                if(!file.isEmpty()){
                    try{
                        String fileName = file.getOriginalFilename();
                        if(!product.containsImageName(fileName)){
                            Map r = this.cloudinary.uploader().upload(file.getBytes(),
                                    ObjectUtils.asMap("resource_type","auto"));
                            String fileUrl = (String) r.get("secure_url");
                            product.addExtraImage(fileName,fileUrl);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

        if(detailNames != null && detailNames.length > 0){
            for(int i = 0; i < detailNames.length; i++){
                String name = detailNames[i];
                String value = detailValues[i];
                Integer id = Integer.parseInt(detailIDs[i]);
                if(id != 0){
                    product.addDetail(id,name,value);
                } else if(!name.isEmpty() && !value.isEmpty()){
                    product.addDetail(name,value);
                }
            }
        }

        Product savedProduct = productService.save(product);
        redirectAttributes.addFlashAttribute("message", new Message("success","The product has been saved successfully."));
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            Product product = productService.get(id);
            if(product.getMainImage() != null){
                productService.deleteImageInCloudinary(product);
            }
            long countByImageExtra = productService.countByImageExtra(id);
            if(countByImageExtra > 0){
                productService.deleteImageExtraInCloudinary(id);
            }
            productService.delete(id);
            redirectAttributes.addFlashAttribute("message", new Message("success","The product ID " + id + " has been deleted successfully"));
        }catch (ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",new Message("error", ex.getMessage()));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws ProductNotFoundException {
        try {
            Product product = productService.get(id);
            List<Brand> listBrands = brandService.listAllTemp();
            model.addAttribute("product",product);
            model.addAttribute("pageTitle","Edit Product (ID: " + id + ")");
            model.addAttribute("listBrands",listBrands);
            Integer numberOfExistingExtraImages = product.getImages().size();
            model.addAttribute("numberOfExistingExtraImages",numberOfExistingExtraImages);

            return "products/product_form";
        } catch (ProductNotFoundException e){
            redirectAttributes.addFlashAttribute("message",new Message("error", e.getMessage()));

            return "redirect:/products";
        }

    }

    @GetMapping("/products/detail/{id}")
    public String viewProductDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws ProductNotFoundException {
        try {
            Product product = productService.get(id);
            model.addAttribute("product",product);
            return "products/product_detail_modal";
        } catch (ProductNotFoundException e){
            redirectAttributes.addFlashAttribute("message",new Message("error", e.getMessage()));

            return "redirect:/products";
        }

    }
}
