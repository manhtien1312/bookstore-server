package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Address;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.AddressRepository;
import com.example.bookstore.service.serviceinterface.IAddressService;
import com.example.bookstore.service.serviceinterface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private IUserService userService;

    private User getUserInfoFromRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(userDetails.getUsername()).orElseThrow();
    }

    @Override
    public ResponseEntity<?> getUserAddress() {
        try {
            List<Address> addresses = addressRepository.findByUserId(getUserInfoFromRequest().getId());
            if(addresses.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(addresses);
            }
            return ResponseEntity.status(HttpStatus.OK).body(addresses);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateAddress(Address address) {
        try {
            String name = address.getName();
            String phoneNumber = address.getPhoneNumber();
            String city = address.getCity();
            String district = address.getDistrict();
            String town = address.getWard();
            String detailAddress = address.getDetailAddress();
            int isDefault = address.getIsDefault();
            addressRepository.updateAddressByUserid(name, phoneNumber, city, district, town, detailAddress, isDefault, getUserInfoFromRequest().getId());
            return ResponseEntity.ok(new MessageResponse("Cập Nhật Địa Chỉ Thành Công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi Server. Vui Lòng Thử Lại Sau"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> addAddress(Address address) {
        try {
            address.setUser(getUserInfoFromRequest());
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

}
