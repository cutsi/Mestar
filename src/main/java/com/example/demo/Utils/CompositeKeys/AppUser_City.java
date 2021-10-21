package com.example.demo.Utils.CompositeKeys;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppUser_City implements Serializable {

    @Column(name = "app_user_id")
    Long appUserId;

    @Column(name = "city_id")
    Long cityId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
