package com.example.demo.Requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryRequest {
    CategoryRequest(){}
    private String categoryName;
    //private Ad ad;
    //private AppUser user;

    //public String getCategory() {
        //return category.getCategoryName();
    //}
}
