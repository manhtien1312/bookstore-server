package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.model.*;
import com.example.bookstore.payload.request.SignInRequest;
import com.example.bookstore.payload.request.SignUpRequest;
import com.example.bookstore.payload.response.JwtResponse;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.AccountRepository;
import com.example.bookstore.security.JwtService;
import com.example.bookstore.service.serviceinterface.AccountService;
import com.example.bookstore.service.serviceinterface.RoleService;
import com.example.bookstore.service.serviceinterface.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public MessageResponse checkAccount(String email){
        if(!accountRepository.existsByEmail(email)){
            return new MessageResponse("Tài Khoản Không Tồn Tại!");
        }
        return new MessageResponse("Email Đã Được Sử Dụng");
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest request) throws Exception {
        if(accountRepository.existsByEmail(request.email())){
            return ResponseEntity.badRequest().body(new MessageResponse("Email Đã Được Sử Dụng!"));
        }

        Set<String> roles = request.roles();
        Set<Role> listRole = new HashSet<>();
        if(roles == null){
            Role role = roleService.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRole.add(role);
        } else {
            roles.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRole.add(adminRole);

                    case "user":
                        Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRole.add(userRole);
                }
            });
        }

        var account = Account.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(listRole)
                .build();

        String imgUrl = "";
        if(request.gender().equals("Nam")) imgUrl = "https://i.postimg.cc/tTnNGWhw/male-avt.jpg";
        else imgUrl = "https://i.postimg.cc/CKFG59bG/female-avt.jpg";

        var user = User.builder()
                .name(request.name())
                .gender(request.gender())
                .phoneNumber(request.phoneNumber())
                .account(account)
                .address(new Address())

                .avatarImage(readImage(imgUrl))
                .build();

        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse(("Đăng Ký Thành Công!")));
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = accountRepository.findByEmail(request.email()).orElseThrow();
            String jwtToken = jwtService.generateToken(account);
            List<String> listRoles = account.getRoles().stream()
                    .map(item -> String.valueOf(item.getRoleName())).toList();
            return ResponseEntity.ok(new JwtResponse(jwtToken, listRoles));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Thông Tin Đăng Nhập Không Chính Xác!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> changePassword(String newPassword) {
        try {
            accountRepository.updatePasswordByUserId(passwordEncoder.encode(newPassword), userService.getUserInfor().id());
            return ResponseEntity.ok(new MessageResponse(("Thay Đổi Mật Khẩu Thành Công!")));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi Server. Vui Lòng Thử Lại Sau"));
        }
    }

    private byte[] readImage(String url) throws Exception{
        URL imgUrl = new URL(url);
        BufferedImage bImg = ImageIO.read(imgUrl);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "jpg", bos);
        return bos.toByteArray();
    }

}
