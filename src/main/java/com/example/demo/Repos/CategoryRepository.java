package com.example.demo.Repos;

import com.example.demo.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional <Category> findByCategoryName(String categoryName);
    Optional<Category> findById(Long id);
    @Query("select c.categoryName from Category c")
    List<String> getAllCategoryName();


}
