package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.Address;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<MessageResponse> updateAddress(Address address);

    Address getUserAddress();
}
