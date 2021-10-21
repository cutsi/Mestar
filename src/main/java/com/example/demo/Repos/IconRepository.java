package com.example.demo.Repos;

import com.example.demo.Models.Category;
import com.example.demo.Models.Icon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IconRepository extends JpaRepository<Icon, Long> {
    Optional<Icon> findById(Long id);
    Optional<Icon> findByValue(String value);
    Icon findByCategory(Category category);
}
