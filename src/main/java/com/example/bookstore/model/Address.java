package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Tên người nhận không được để trống!")
    private String name;

    @NotBlank(message = "Số điện thoại người nhận không được để trống!")
    private String phoneNumber;

    @NotBlank(message = "Tỉnh/Thành Phố không được để trống!")
    private String city;

    @NotBlank(message = "Quận/Huyện không được để trống!")
    private String district;

    @NotBlank(message = "Phường/Xã không được để trống!")
    private String ward;

    private String detailAddress;
    private int isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Override
    public String toString(){
        return detailAddress + " " + ward + " " + district + " " + city;
    }

}
