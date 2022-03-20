package com.example.Be_The_Donor.controller;

import com.example.Be_The_Donor.config.PasswordEncoder;
import com.example.Be_The_Donor.controller.requestbody.RegistrationRequest;
import com.example.Be_The_Donor.entity.ApplicationUser;
import com.example.Be_The_Donor.entity.OrderItem;
import com.example.Be_The_Donor.entity.Orders;
import com.example.Be_The_Donor.entity.Product;
import com.example.Be_The_Donor.repository.OrderItemsRepository;
import com.example.Be_The_Donor.repository.OrderRepository;
import com.example.Be_The_Donor.repository.ProductRepository;
import com.example.Be_The_Donor.repository.UserRepository;
import com.example.Be_The_Donor.service.ApplicationUserService;
import com.example.Be_The_Donor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class DonorController {
    @Autowired
    ApplicationUserService applicationUserService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    private DaoAuthenticationProvider authenticationManager;
    @Autowired
    private ApplicationUserService userDetailsService;

    @GetMapping("/api/v1/donorview")
    public String donorLogin(Model model) {
        return "donorView";
    }

}