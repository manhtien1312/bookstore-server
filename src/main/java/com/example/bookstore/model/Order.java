package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createTime;

    private String status;
    private String address;
    private String phoneNumber;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<ItemOrder> itemOrders;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "ship_id")
    private Shipment shipment;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "pay_id")
    private Payment payment;

}