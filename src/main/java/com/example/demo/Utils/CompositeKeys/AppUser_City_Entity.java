package com.example.demo.Utils.CompositeKeys;

import com.example.demo.Models.City;
import com.example.demo.Models.AppUser;
import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class AppUser_City_Entity {
    @EmbeddedId
    AppUser_City id;

    @ManyToOne
    @MapsId("appUserId")
    @JoinColumn(name = "app_user_id")
    AppUser appUser;

    @ManyToOne
    @MapsId("cityId")
    @JoinColumn(name = "city_id")
    City city;

}
