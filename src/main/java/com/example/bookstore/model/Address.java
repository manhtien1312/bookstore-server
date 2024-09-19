package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    private String city;
    private String district;
    private String town;
    private String detailAddress;

    @OneToOne(
            mappedBy = "address"
    )
    @JsonBackReference
    private User user;

    @Override
    public String toString(){
        return detailAddress + " " + town + " " + district + " " + city;
    }

}
