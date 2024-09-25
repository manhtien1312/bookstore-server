package com.example.bookstore.repository;

import com.example.bookstore.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Transactional
    @Query(value = "SELECT a FROM Address a WHERE a.user.id=:userId")
    List<Address> findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Address a " +
            "SET a.name=:name, a.phoneNumber=:phoneNumber, " +
            "a.city=:city, a.district=:district, " +
            "a.ward=:ward, a.detailAddress=:detailAddress, " +
            "a.isDefault=:isDefault " +
            "WHERE a.user.id=:userId")
    void updateAddressByUserid(
            String name,
            String phoneNumber,
            String city,
            String district,
            String ward,
            String detailAddress,
            int isDefault,
            UUID userId
    );

}
