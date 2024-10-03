package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.model.Cart;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.service.serviceinterface.ICartService;
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
public class CartService implements ICartService {

    @Autowired
    private IUserService userService;
    @Autowired
    private CartRepository cartRepository;

    private User getUserInfoFromRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(userDetails.getUsername()).orElseThrow();
    }

    @Override
    public ResponseEntity<?> getAllItems() {
        try {
            List<Cart> res = cartRepository.findByUserId(getUserInfoFromRequest().getId());
            if(res.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res);
            }
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau."));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> addItemCart(Cart cart) {
        try {
            cart.setUser(getUserInfoFromRequest());
            cartRepository.save(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Thêm giỏ hàng thành công!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Lỗi Server. Vui lòng thử lại sau."));
        }
    }
}
