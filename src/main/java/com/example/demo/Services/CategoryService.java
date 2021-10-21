package com.example.demo.Services;

import com.example.demo.Models.Category;
import com.example.demo.Repos.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public Optional<Category> getCategoryId(Long id){
        return categoryRepository.findById(id);
    }
    public Optional<Category> getCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }
    public List<String> getAllCategories(){
        return categoryRepository.getAllCategoryName();
    }
}
