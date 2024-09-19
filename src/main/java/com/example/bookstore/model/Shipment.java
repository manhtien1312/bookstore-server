package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private String shipStatus;

    @ManyToOne
    @JoinColumn(name = "shipment_method_id")
    private ShipmentMethod shipmentMethod;

}