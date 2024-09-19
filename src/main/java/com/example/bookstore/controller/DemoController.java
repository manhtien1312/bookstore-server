package com.example.bookstore.controller;

import com.example.bookstore.model.Role;
import com.example.bookstore.model.TestModel;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public MessageResponse hello(){
        return new MessageResponse("Hello World!");
    }

    @PostMapping("/test")
    public MessageResponse testPost(@RequestBody Role role){
        try {
            roleRepository.save(role);
            return new MessageResponse("Success!");
        } catch (Exception e){
            e.printStackTrace();
            return new MessageResponse("Failed!");
        }
    }

    @GetMapping("/test")
    public List<TestModel> getAll(){
        return testRepository.findAll();
    }

}
