package com.example.demo.Requests;

import com.example.demo.Models.AppUser;
import com.example.demo.Repos.AppUserRepository;
import com.example.demo.Services.AppUserService;
import lombok.*;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AdCategoryRequest {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    //####### CATEGORY ##########
    private String categoryName;
    //######## USER ############
    private Long userId;
    //######## AD ###########
    private String title;
    private String description;

    public Optional<AppUser> getUser(){
        return appUserService.getUserById(userId);
    }
}
