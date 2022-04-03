package com.beTheDonor.controller;

import com.beTheDonor.entity.Donors;
import com.beTheDonor.entity.Response;
import com.beTheDonor.exception.ErrorResponse;
import com.beTheDonor.repository.*;
import com.beTheDonor.config.PasswordEncoder;
import com.beTheDonor.service.ApplicationUserService;
import com.beTheDonor.service.ProductService;
import com.beTheDonor.service.DonorService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller

public class
DonorController {
    @Autowired
    ApplicationUserService applicationUserService;
    @Autowired
    DonorService donorService;
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
    DonorRepository donorRepository;
    @Autowired
    private DaoAuthenticationProvider authenticationManager;
    @Autowired
    private ApplicationUserService userDetailsService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @GetMapping("/donorview")
    public String donorLogin(Model model) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "donorView";
    }


    @PostMapping(value = "/finalOrder")
    public @ResponseBody Response changeStatusAfterOrder(@RequestBody JSONObject payload) throws Exception {
        Boolean response = donorService.changeStatusOfOrder(payload);
        return new Response(true, "");
    }

    @PostMapping(value = "/donationInfo")
    public ErrorResponse storeTotalAmount(@RequestBody JSONObject payload, HttpServletRequest request) throws Exception {
        Principal principal = request.getUserPrincipal();
        Donors donor;
        String userId = principal.getName();
        Long id = userRepository.getByEmail(userId).getId();
        Boolean response = donorService.storeTotalAmount(payload, id);
        return null;
    }


}
