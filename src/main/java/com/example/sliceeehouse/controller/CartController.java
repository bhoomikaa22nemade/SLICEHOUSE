// package com.example.sliceeehouse.controller;

// import com.example.sliceeehouse.model.Cart;
// import com.example.sliceeehouse.repository.CartRepository;
// import com.example.sliceeehouse.repository.PizzaRepository;
// import com.example.sliceeehouse.repository.UserRepository;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// @Controller
// @RequestMapping("/admin/cart")
// public class CartController {

//     private final CartRepository cartRepository;
//     private final UserRepository userRepository;
//     private final PizzaRepository pizzaRepository;

//     public CartController(CartRepository cartRepository,
//                           UserRepository userRepository,
//                           PizzaRepository pizzaRepository) {
//         this.cartRepository = cartRepository;
//         this.userRepository = userRepository;
//         this.pizzaRepository = pizzaRepository;
//     }

//     // ================= VIEW CART ITEMS =================
//     @GetMapping
//     public String viewCartItems(Model model) {

//         model.addAttribute("cartItems", cartRepository.findAll());

//         return "admin/cart/view_cart";
//     }

//     // ================= ADD CART FORM =================
//     @GetMapping("/add")
//     public String addCartForm(Model model) {

//         model.addAttribute("cart", new Cart());
//         model.addAttribute("users", userRepository.findAll());
//         model.addAttribute("pizzas", pizzaRepository.findAll());

//         return "admin/cart/add_cart";
//     }

//     // ================= SAVE CART ITEM =================
//     @PostMapping("/add")
//     public String saveCartItem(@ModelAttribute Cart cart) {

//         cartRepository.save(cart);

//         return "redirect:/admin/cart";
//     }

//     // ================= DELETE CART ITEM =================
//     @GetMapping("/delete/{id}")
//     public String deleteCartItem(@PathVariable Long id) {

//         cartRepository.deleteById(id);

//         return "redirect:/admin/cart";
//     }

// }


package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Cart;
import com.example.sliceeehouse.model.Coupon;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.repository.CartRepository;
import com.example.sliceeehouse.repository.CouponRepository;
import com.example.sliceeehouse.repository.PizzaRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    private final CouponRepository couponRepository;

 public CartController(CartRepository cartRepository,
                      UserRepository userRepository,
                      PizzaRepository pizzaRepository,
                      CouponRepository couponRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.pizzaRepository = pizzaRepository;
    this.couponRepository = couponRepository;
}

    // VIEW
    @GetMapping
    public String viewCartItems(Model model) {

        List<Cart> cartItems = cartRepository.findAll();

        double total = 0;
        for (Cart c : cartItems) {
            total += (c.getTotalPrice() != null ? c.getTotalPrice() : 0);
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", total);

        return "admin/cart/view_cart";
    }

    // ADD FORM
    @GetMapping("/add")
    public String addCartForm(Model model) {

        model.addAttribute("cart", new Cart());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("pizzas", pizzaRepository.findAll());

        return "admin/cart/add_cart";
    }

    // SAVE
    @PostMapping("/add")
public String saveCartItem(@ModelAttribute Cart cart,
                          @RequestParam(required = false) String coupon) {

    Pizza pizza = pizzaRepository.findById(cart.getPizza().getId())
            .orElseThrow(() -> new RuntimeException("Pizza not found"));

    cart.setPizza(pizza);
    cart.setUser(userRepository.findById(cart.getUser().getId()).orElseThrow());

    double price = pizza.getDiscountedPrice() != null
            ? pizza.getDiscountedPrice()
            : pizza.getSellingPrice();

    double total = price * cart.getQuantity();

    // 🔥 APPLY COUPON HERE
    if (coupon != null && !coupon.isEmpty()) {

        Coupon c = couponRepository.findByCode(coupon).orElse(null);

        if (c != null && c.isActive()) {

            if (total >= c.getMinAmount()) {

                switch (c.getType()) {

                    case "PERCENT":
                        total = total - (total * c.getValue() / 100);
                        break;

                    case "FLAT":
                        total = total - c.getValue();
                        break;

                    case "DELIVERY":
                        // optional logic
                        break;
                }
            }
        }
    }

    cart.setTotalPrice(total);

    cartRepository.save(cart);

    return "redirect:/admin/cart";
}

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/admin/cart";
    }

    // INCREASE
    @GetMapping("/addQuantity/{id}")
    public String addQuantity(@PathVariable Long id) {

        Cart cart = cartRepository.findById(id).orElseThrow();

        cart.setQuantity(cart.getQuantity() + 1);

        double price = cart.getPizza().getDiscountedPrice() != null
                ? cart.getPizza().getDiscountedPrice()
                : cart.getPizza().getSellingPrice();

        cart.setTotalPrice(price * cart.getQuantity());

        cartRepository.save(cart);

        return "redirect:/admin/cart";
    }

    // DECREASE
    @GetMapping("/deleteQuantity/{id}")
    public String deleteQuantity(@PathVariable Long id) {

        Cart cart = cartRepository.findById(id).orElseThrow();

        int qty = cart.getQuantity() - 1;

        if (qty < 1) {
            cartRepository.delete(cart);
        } else {
            cart.setQuantity(qty);

            double price = cart.getPizza().getDiscountedPrice() != null
                    ? cart.getPizza().getDiscountedPrice()
                    : cart.getPizza().getSellingPrice();

            cart.setTotalPrice(price * cart.getQuantity());

            cartRepository.save(cart);
        }

        return "redirect:/admin/cart";
    }
}