package com.example.demo.Controller;

import com.example.demo.Models.Category;
import com.example.demo.Repos.CategoryRepository;
import com.example.demo.Requests.AdCategoryRequest;
import com.example.demo.Requests.CategoryRequest;
import com.example.demo.Services.CategoryService;
import com.example.demo.Models.Ad;
import com.example.demo.Models.AppUser;
import com.example.demo.Repos.AppUserRepository;
import com.example.demo.Services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    @PostMapping(path = "save-category")
    public String saveCat(@RequestBody AdCategoryRequest catReq, Model model){
        //Optional<Category> cat = categoryRepository.findByCategoryName(catReq.getCategory());
        //Category cat = categoryService.getCategoryByCategoryName(catReq.getCategory());
        //Category category = new Category(catReq.getCategoryName(),catReq.getUser(),catReq.getAd());
        //categoryService.saveCategory(category);
        System.out.println(catReq.getCategoryName() + " " +
                            catReq.getDescription() + " " +
                            catReq.getTitle() + " " +
                            catReq.getUserId());
        Optional<AppUser> OptUser = appUserRepository.findById(catReq.getUserId());
        AppUser usr =  OptUser.get();
        Ad ad = new Ad(catReq.getTitle(),catReq.getDescription());
        Category cat = new Category(catReq.getCategoryName(), usr);

        return "welcome";
    }
    @PostMapping(path = "save-cat")
    public String saveCategory(@RequestBody CategoryRequest catReq, Model model){
        Category cat = new Category(catReq.getCategoryName());
        categoryService.saveCategory(cat);
        categoryRepository.findAll();

        return "home";
    }
    @GetMapping(path = "getAllCategories")
    public String displayAllCategories(Model model){
        List<String> categories = categoryRepository.getAllCategoryName();
        for (String catName:categories)
        {
            System.out.println(catName);
        }
        model.addAttribute("userList", categories);
        return "home";
    }
    @GetMapping(path = "pick")
    public String displayCategory(Model model){
        List<String> categories = categoryRepository.getAllCategoryName();
        for (String catName:categories)
        {
            System.out.println(catName);
        }
        model.addAttribute("userList", categories);
        return "pick";
    }
}
