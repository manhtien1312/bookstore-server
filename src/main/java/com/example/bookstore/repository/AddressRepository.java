package com.example.bookstore.repository;

import com.example.bookstore.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Transactional
    @Query(value = "SELECT a FROM Address a WHERE a.user.id=:userId")
    Address findByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Address a SET a.city=:city, a.district=:district, a.town=:town, a.detailAddr=:detailAddr WHERE a.user.id=:userId")
    void updateAddressByUserid(String city, String district, String town, String detailAddr, int userId);

}
