package com.example.bookstore.controller;

import com.example.bookstore.model.Address;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping
    public ResponseEntity<?> getUserAddress(){
        return addressService.getUserAddress();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }

    @PutMapping("/update-address")
    public ResponseEntity<MessageResponse> updateAddress(@RequestBody Address address){
        return addressService.updateAddress(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAddress(@PathVariable String id){
        return addressService.deleteAddress(id);
    }

}
