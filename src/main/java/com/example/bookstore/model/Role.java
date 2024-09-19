package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private ERole roleName;

}
