package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.Address;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAddressService {

    ResponseEntity<?> getUserAddress();
    ResponseEntity<MessageResponse> updateAddress(Address address);
    ResponseEntity<MessageResponse> addAddress(Address address);
    ResponseEntity<MessageResponse> deleteAddress(String id);

}
