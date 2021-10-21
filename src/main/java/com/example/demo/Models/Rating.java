package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rating {
    //############## ID ###################//
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_sequence"
    )
    private Long id;

    //############## ATTRIBUTES ###################//
    private int rating;
    private boolean ratingEnabled = false;

    //############## CONSTRUCTORS ###################//
    public Rating(int rating){
        this.rating = rating;
    }
    //############## METHODS ###################//

    public boolean isEnabled(){
        return ratingEnabled;
    }
}
