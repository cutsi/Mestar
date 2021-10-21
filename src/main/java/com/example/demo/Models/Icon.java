package com.example.demo.Models;

import com.example.demo.Models.Category;
import com.example.demo.Models.Ad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Icon {
    @SequenceGenerator(
            name = "icon_sequence",
            sequenceName = "icon_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "icon_sequence"
    )
    private Long id;
    private String value;
    @OneToMany(mappedBy = "icon")
    private Set<Ad> ads = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
