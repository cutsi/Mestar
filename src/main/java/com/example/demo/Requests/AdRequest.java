package com.example.demo.Requests;

import com.example.demo.Models.Ad;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AdRequest {
    private String title;
    private String description;
    private String price;
    private String city;
    private String category;
    public Ad getAd(){
        return new Ad(title,description);
    }

}
