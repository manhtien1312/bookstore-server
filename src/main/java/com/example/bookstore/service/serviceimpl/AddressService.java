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
import java.util.Optional;
import java.util.UUID;

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
            if(address.getIsDefault() == 1){
                Address defaultAddress = addressRepository.findDefaultByUserId(getUserInfoFromRequest().getId()).orElseThrow();
                defaultAddress.setIsDefault(0);
                addressRepository.save(defaultAddress);
            }
            address.setUser(getUserInfoFromRequest());
            addressRepository.save(address);
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

            if(address.getIsDefault() == 0){
                addressRepository.save(address);
                return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Thêm Địa Chỉ Thành Công!"));
            }

            Optional<Address> defaultAddress = addressRepository.findDefaultByUserId(getUserInfoFromRequest().getId());
            if(defaultAddress.isPresent()){
                defaultAddress.get().setIsDefault(0);
                addressRepository.save(defaultAddress.get());
            }
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Thêm Địa Chỉ Thành Công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteAddress(String id) {
        try {
            addressRepository.deleteById(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Bạn Đã Xóa Địa Chỉ!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau!"));
        }
    }

}
