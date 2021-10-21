package com.example.demo.Services;

import com.example.demo.Models.Category;
import com.example.demo.Models.Icon;
import com.example.demo.Repos.IconRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class IconService {
    private final IconRepository iconRepository;

    public Optional<Icon> getIconById(Long id){
        return iconRepository.findById(id);
    }
    public Optional<Icon> getIconById(String value){
        return iconRepository.findByValue(value);
    }

    public Icon getIconByCategory(Category category) {
        return iconRepository.findByCategory(category);
    }
}
