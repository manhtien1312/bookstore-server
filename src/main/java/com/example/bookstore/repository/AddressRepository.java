package com.example.bookstore.repository;

import com.example.bookstore.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Transactional
    @Query(value = "SELECT a FROM Address a " +
            "WHERE a.user.id=:userId")
    List<Address> findByUserId(UUID userId);

    @Transactional
    @Query(value = "SELECT a FROM Address a " +
            "WHERE a.user.id=:userId AND a.isDefault=1")
    Optional<Address> findDefaultByUserId(UUID userId);

}
