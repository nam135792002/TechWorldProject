package com.techworld.admin.brand;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.admin.Message;
import com.techworld.admin.category.CategoryService;
import com.techworld.common.entity.Brand;
import com.techworld.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/brands")
    public String listFirstPage(Model model){
        List<Brand> listBrands = brandService.listAll();
        model.addAttribute("listBrands",listBrands);

        return "brands/brands";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model){
        List<Category> listCategories = categoryService.listAll();

        model.addAttribute("listCategories",listCategories);
        model.addAttribute("brand",new Brand());
        model.addAttribute("pageTitle","Create New Brand");

        return "brands/brand_form";
    }

    @PostMapping("/brands/save")
    public String saveBrand(Brand brand, @RequestParam("fileImage")MultipartFile multipartFile,
                            RedirectAttributes redirectAttributes) throws IOException, BrandNotFoundException {
        if(!multipartFile.isEmpty()){
            if(brand.getId() != null && brandService.get(brand.getId()).getLogo() != null){
                brandService.deleteImageInCloudinary(brand);
            }
            try{
                Map r = this.cloudinary.uploader().upload(multipartFile.getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                String img = (String) r.get("secure_url");
                brand.setLogo(img);
                Brand savedBrand = brandService.save(brand);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            if(brand.getId() == null || brandService.get(brand.getId()).getLogo() == null){
                brand.setLogo(null);
            }
            Brand savedBrand = brandService.save(brand);
        }
        redirectAttributes.addFlashAttribute("message", new Message("success","The brand has been saved successfully."));
        return "redirect:/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            Brand brand = brandService.get(id);
            List<Category> listCategories = categoryService.listAll();
            System.out.println(brand.getLogo());
            model.addAttribute("brand",brand);
            model.addAttribute("listCategories",listCategories);
            model.addAttribute("pageTitle","Edit Brand (ID: " + id + ")");

            return "brands/brand_form";
        } catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",new Message("error", e.getMessage()));
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            Brand brand = brandService.get(id);
            if(brand.getLogo() != null){
                brandService.deleteImageInCloudinary(brand);
            }
            brandService.delete(id);
            redirectAttributes.addFlashAttribute("message", new Message("success", "The brand ID " + id + " has been deleted successfully"));
        } catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",new Message("error", e.getMessage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/brands";
    }

    @GetMapping("/brands/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Brand> listBrands = brandService.listAll();
        BrandCsvExporter exporter = new BrandCsvExporter();
        exporter.export(listBrands,response);
    }
}
