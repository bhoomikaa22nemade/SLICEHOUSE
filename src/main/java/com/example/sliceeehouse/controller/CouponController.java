package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Coupon;
import com.example.sliceeehouse.repository.CouponRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/coupon")
public class CouponController {

    private final CouponRepository couponRepository;

    public CouponController(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // 🔹 VIEW ALL
    @GetMapping
    public String viewCoupons(Model model) {
        model.addAttribute("coupons", couponRepository.findAll());
        return "admin/coupon/view_coupon";
    }

    // 🔹 ADD FORM
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "admin/coupon/add_coupon";
    }

    // 🔹 SAVE
    @PostMapping("/save")
    public String saveCoupon(@ModelAttribute Coupon coupon) {
        coupon.setActive(true); // default active
        couponRepository.save(coupon);
        return "redirect:/admin/coupon";
    }

    // 🔹 DELETE
    @GetMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        couponRepository.deleteById(id);
        return "redirect:/admin/coupon";
    }
}
