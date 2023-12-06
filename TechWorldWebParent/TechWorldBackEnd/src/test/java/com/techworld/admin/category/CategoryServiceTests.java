package com.techworld.admin.category;

import com.techworld.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @MockBean
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCheckUniqueInNewModelReturnDuplicateName(){
        Integer id = null;
        String name = "computer";

        Category category = new Category(id, name);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(category);

        String result = categoryService.checkUnique(id,name);

        assertThat(result).isEqualTo("DuplicateName");
    }
//
    @Test
    public void testCheckUniqueInNewModelReturnOk(){
        Integer id = null;
        String name = "abc";

        Mockito.when(categoryRepository.findByName(name)).thenReturn(null);

        String result = categoryService.checkUnique(id,name);

        assertThat(result).isEqualTo("OK");
    }
//
    @Test
    public void testCheckUniqueInEditModelReturnDuplicateName(){
        Integer id = 1;
        String name = "Laptop";

        Category category = new Category(2, name);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(category);

        String result = categoryService.checkUnique(id,name);

        assertThat(result).isEqualTo("DuplicateName");
    }
//
    @Test
    public void testCheckUniqueInEditModelReturnOk(){
        Integer id = 1;
        String name = "abc";

        Category category = new Category(id, name);

        Mockito.when(categoryRepository.findByName(name)).thenReturn(null);

        String result = categoryService.checkUnique(id,name);

        assertThat(result).isEqualTo("OK");
    }

}
