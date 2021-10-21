package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Ad {
    @SequenceGenerator(
            name = "ad_sequence",
            sequenceName = "ad_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ad_sequence"
    )
    private Long id; //TODO napravit formu u koju cemo spremit
    private String title; // TODO vrijednosti i od nje napravit objekt Ad
    private String description;
    private Integer price;
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private boolean isPicked;
    @Column(nullable = true)
    private boolean isFinished;
    @Column(nullable = true)
    private boolean isContractorFinished;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="icon_id")
    private Icon icon;

    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="app_user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    public Ad(String title, String description){
        super();
        this.title = title;
        this.description = description;

    }
    public String getIcon(){
        return icon.getValue();
    }
    public Ad(Category category, City city,
              LocalDateTime now, String description,
              String price, String title, AppUser appUser, Icon icon) {
        this.category = category;
        this.city = city;
        this.createdAt = now;
        this.description = description;
        this.price = Integer.valueOf(price);
        this.title = title;
        this.user = appUser;
        this.icon = icon;
        this.isFinished = false;
        this.isPicked = false;
        this.isContractorFinished = false;
    }
}
