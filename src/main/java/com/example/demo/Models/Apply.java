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
@AllArgsConstructor
public class Apply {
    @SequenceGenerator(
            name = "apply_sequence",
            sequenceName = "apply_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "apply_sequence"
    )
    private Long id;

    private Long contractorId;
    private Long adOwnerId;
    private Long adId;

    public Apply(Long contractorId, Long adOwnerId, Long adId) {
        this.contractorId = contractorId;
        this.adOwnerId = adOwnerId;
        this.adId = adId;
    }
}
