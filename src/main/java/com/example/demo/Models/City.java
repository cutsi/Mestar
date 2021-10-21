package com.example.demo.Models;

import com.example.demo.Models.Ad;
import com.example.demo.Models.AppUser;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City {

    @SequenceGenerator(
            name = "city_sequence",
            sequenceName = "city_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_sequence"
    )
    private Long id;

    @ManyToMany(mappedBy = "cities")
    private Set<AppUser> userCity = new HashSet<>();

    @OneToMany(mappedBy = "city")
    private Set<Ad> ads = new HashSet<>();

    private String cityName;
}
