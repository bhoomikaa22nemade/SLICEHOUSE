package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Cart;
import com.example.sliceeehouse.model.Payment;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.model.Order;
import com.example.sliceeehouse.repository.CartRepository;
import com.example.sliceeehouse.repository.PaymentRepository;
import com.example.sliceeehouse.repository.UserRepository;
import com.example.sliceeehouse.repository.OrderRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")   // optional but clean
public class PaymentController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentController(CartRepository cartRepository,
                             UserRepository userRepository,
                             PaymentRepository paymentRepository,
                             OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // ================= USER PAYMENT PAGE =================
    @GetMapping("/payment")
    public String paymentPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();   // ⚠️ FIX: use username (not email)

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser(user);

        model.addAttribute("cartItems", cartItems);

        return "payment";
    }

    // ================= ADMIN VIEW PAYMENTS =================
    @GetMapping("/admin/payments")
    public String viewPayments(Model model) {

        List<Payment> payments = paymentRepository.findAll();

        System.out.println("Payments found: " + payments.size()); // DEBUG

        model.addAttribute("payments", payments);

        return "admin/payment/view_payment";
    }

    // ================= ADMIN ADD FORM =================
    @GetMapping("/admin/payments/add")
    public String addPaymentForm(Model model) {

        model.addAttribute("orders", orderRepository.findAll());

        return "admin/payment/add_payment";
    }

    // ================= SAVE PAYMENT =================
    @PostMapping("/admin/payments/add")
    public String addPayment(
            @RequestParam("order.id") Long orderId,
            @RequestParam Double amount,
            @RequestParam String paymentMethod,
            @RequestParam String paymentStatus
    ) {

        System.out.println("Saving payment..."); // DEBUG

        // fetch order (IMPORTANT)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(paymentStatus);

        paymentRepository.save(payment);

        System.out.println("Payment saved successfully!"); // DEBUG

        return "redirect:/admin/payments";
    }

    // ================= UPDATE FORM =================
    @GetMapping("/admin/payments/update/{id}")
    public String updatePaymentForm(@PathVariable Long id, Model model) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        model.addAttribute("payment", payment);
        model.addAttribute("orders", orderRepository.findAll());

        return "admin/payment/update_payment";
    }

    // ================= UPDATE PAYMENT =================
    @PostMapping("/admin/payments/update")
    public String updatePayment(
            @RequestParam Long id,
            @RequestParam("order.id") Long orderId,
            @RequestParam Double amount,
            @RequestParam String paymentMethod,
            @RequestParam String paymentStatus
    ) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(paymentStatus);

        paymentRepository.save(payment);

        return "redirect:/admin/payments";
    }
}


