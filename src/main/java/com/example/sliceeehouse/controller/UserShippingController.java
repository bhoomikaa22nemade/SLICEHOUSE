package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Shipping;
import com.example.sliceeehouse.repository.ShippingRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shipping")
public class UserShippingController {

    private final ShippingRepository shippingRepository;

    public UserShippingController(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @GetMapping("/track")
    public String trackPage(){
        return "shipping/track";
    }

    @PostMapping("/track")
    public String trackResult(@RequestParam String trackingNumber, Model model){

        Shipping shipping = shippingRepository.findByTrackingNumber(trackingNumber);

        model.addAttribute("shipping", shipping);

        return "shipping/track_result";
    }
}
