package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.Address;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.AddressRepository;
import com.example.bookstore.service.serviceinterface.AddressService;
import com.example.bookstore.service.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;

    private UserDTO getUserInfoFromRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(userDetails.getUsername());
    }

    @Override
    public Address getUserAddress() {
        return addressRepository.findByUserId(getUserInfoFromRequest().id());
    }

    @Override
    public ResponseEntity<MessageResponse> updateAddress(Address address) {
        try {
            String city = address.getCity();
            String district = address.getDistrict();
            String town = address.getTown();
            String detailAddr = address.getDetailAddr();
            addressRepository.updateAddressByUserid(city, district, town, detailAddr, getUserInfoFromRequest().id());
            return ResponseEntity.ok(new MessageResponse("Cập Nhật Địa Chỉ Thành Công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi Server. Vui Lòng Thử Lại Sau"));
        }
    }

}
