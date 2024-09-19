package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.dto.mapper.UserDtoMapper;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.serviceinterface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper userDTOMapper;

    private UserDto getInfoFromRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByAccountEmail(userDetails.getUsername());
    }

    @Override
    public UserDto findByAccountEmail(String accountEmail) {
        return userRepository.findByAccountEmail(accountEmail)
                .map(userDTOMapper)
                .orElseThrow();
    }

    @Override
    public UserDto getUserInfor() {
        return getInfoFromRequest();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<MessageResponse> updateUser(UserDto userDTO) {
        try {
            User user = userRepository.findById(getInfoFromRequest().id()).orElseThrow();
            if (userDTO.name() != null && !userDTO.name().isEmpty()){
                user.setName(userDTO.name());
            }
            if (userDTO.phoneNumber() != null && !userDTO.phoneNumber().isEmpty()){
                user.setPhoneNumber(userDTO.phoneNumber());
            }
            if (userDTO.gender() != null && !userDTO.gender().isEmpty()){
                user.setGender(userDTO.gender());
            }
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
