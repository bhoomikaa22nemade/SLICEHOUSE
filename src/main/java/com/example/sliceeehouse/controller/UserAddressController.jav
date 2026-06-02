package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Address;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.model.enums.State;
import com.example.sliceeehouse.repository.AddressRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class UserAddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public UserAddressController(AddressRepository addressRepository,
                                 UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // ================= VIEW USER ADDRESSES =================
    @GetMapping
    public String viewAddresses(Model model) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Address> addresses = addressRepository.findAllByUser(user);

        model.addAttribute("addresses", addresses);

        return "user/address";
    }

    // ================= ADD ADDRESS FORM =================
    @GetMapping("/add")
    public String addAddressForm(Model model) {

        model.addAttribute("address", new Address());
        model.addAttribute("states", State.values());

        return "user/add_address";
    }

    // ================= SAVE ADDRESS =================
    @PostMapping("/add")
    public String saveAddress(@ModelAttribute Address address) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);

        addressRepository.save(address);

        return "redirect:/address";
    }

    // ================= DELETE ADDRESS =================
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {

        addressRepository.deleteById(id);

        return "redirect:/address";
    }

}