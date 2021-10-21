package com.example.demo.Models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Category {

    //############## ID ###################//
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;

    //############## TABLE FOR USER - CATEGORY ###################//
    @ManyToMany(mappedBy = "categories")
    private Set<AppUser> users = new HashSet<>();

    //############## TABLE FOR AD - CATEGORY ###################//
    @OneToMany(mappedBy = "category")
    private Set<Ad> ads = new HashSet<>();

    //############## ATTRIBUTES ###################//
    @Column(nullable = false)
    private String categoryName;

    @OneToOne(mappedBy = "category")
    private Icon icon;

    //############## CONSTRUCTORS ###################//
    public Category(String categoryName){
        super();
        this.categoryName = categoryName;
    }
    public Category(String categoryName,AppUser user){
        super();
        this.categoryName = categoryName;
        this.users.add(user);
    }

}
