package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Address;
import com.example.sliceeehouse.repository.AddressRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/addresses")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository,
                             UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // ================= VIEW ALL ADDRESSES =================
    @GetMapping
    public String viewAddresses(Model model) {

        model.addAttribute("addresses", addressRepository.findAll());

        return "admin/address/view_address";
    }

    // ================= ADD ADDRESS FORM =================
    @GetMapping("/add")
    public String addAddressForm(Model model) {

        model.addAttribute("address", new Address());
        model.addAttribute("users", userRepository.findAll());

        return "admin/address/add_address";
    }

    // ================= SAVE ADDRESS =================
    @PostMapping("/add")
    public String saveAddress(@ModelAttribute Address address) {

        addressRepository.save(address);

        return "redirect:/admin/addresses";
    }

    // ================= UPDATE ADDRESS FORM =================
    @GetMapping("/update/{id}")
    public String updateAddressForm(@PathVariable Long id, Model model) {

        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        model.addAttribute("address", address);
        model.addAttribute("users", userRepository.findAll());

        return "admin/address/update_address";
    }

    // ================= UPDATE ADDRESS =================
    @PostMapping("/update")
    public String updateAddress(@ModelAttribute Address address) {

        addressRepository.save(address);

        return "redirect:/admin/addresses";
    }

    // ================= DELETE ADDRESS =================
    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {

        addressRepository.deleteById(id);

        return "redirect:/admin/addresses";
    }

    
}
