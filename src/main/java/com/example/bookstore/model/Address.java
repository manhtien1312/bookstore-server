package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;
    private String district;
    private String town;
    private String detailAddr;

    @OneToOne(
            mappedBy = "address"
    )
    @JsonBackReference
    private User user;

    @Override
    public String toString(){
        return detailAddr + " " + town + " " + district + " " + city;
    }

}
