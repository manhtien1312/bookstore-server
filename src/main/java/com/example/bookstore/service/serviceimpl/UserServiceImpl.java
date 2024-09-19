package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.dto.mapper.UserDTOMapper;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.serviceinterface.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper userDTOMapper;

    private UserDTO getInfoFromRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByAccountEmail(userDetails.getUsername());
    }

    @Override
    public UserDTO findByAccountEmail(String accountEmail) {
        return userRepository.findByAccountEmail(accountEmail)
                .map(userDTOMapper)
                .orElseThrow();
    }

    @Override
    public UserDTO getUserInfor() {
        return getInfoFromRequest();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<MessageResponse> updateUser(UserDTO userDTO) {
        try {
            User user = userRepository.findById(getInfoFromRequest().id()).orElseThrow();
            user.setName(userDTO.name());
            user.setPhoneNumber(userDTO.phoneNumber());
            user.setGender(userDTO.gender());
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Cập Nhật Thông Tin Thành Công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi Server. Vui Lòng Thử Lại Sau"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateAvatarImage(MultipartFile avatarImage) {
        try {
            User user = userRepository.findById(getInfoFromRequest().id()).orElseThrow();
            user.setAvatarImage(avatarImage.getBytes());
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Cập Nhật Ảnh Đại Diện Thành Công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi Server. Vui Lòng Thử Lại Sau"));
        }
    }


}
