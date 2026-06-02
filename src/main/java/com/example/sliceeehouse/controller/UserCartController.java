// package com.example.sliceeehouse.controller;

// import com.example.sliceeehouse.model.Cart;
// import com.example.sliceeehouse.model.Pizza;
// import com.example.sliceeehouse.model.User;
// import com.example.sliceeehouse.repository.CartRepository;
// import com.example.sliceeehouse.repository.PizzaRepository;
// import com.example.sliceeehouse.repository.UserRepository;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.security.Principal;

// @Controller
// @RequestMapping("/cart")
// public class UserCartController {

//     private final CartRepository cartRepository;
//     private final UserRepository userRepository;
//     private final PizzaRepository pizzaRepository;

//     public UserCartController(CartRepository cartRepository,
//                               UserRepository userRepository,
//                               PizzaRepository pizzaRepository) {

//         this.cartRepository = cartRepository;
//         this.userRepository = userRepository;
//         this.pizzaRepository = pizzaRepository;
//     }

//     // ADD TO CART
//     @PostMapping("/add")
//     public String addToCart(@RequestParam Long pizzaId,
//                             @RequestParam String size,
//                             @RequestParam int quantity,
//                             @RequestParam(required = false) String coupon,
//                             Principal principal) {

//         if (principal == null) {
//             return "redirect:/login";
//         }

//         User user = userRepository.findByUsername(principal.getName())
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         Pizza pizza = pizzaRepository.findById(pizzaId)
//                 .orElseThrow(() -> new RuntimeException("Pizza not found"));

//         Cart cartItem = cartRepository
//                 .findByUserAndPizzaAndSize(user, pizza, size)
//                 .orElse(null);

//         if (cartItem == null) {

//             cartItem = new Cart();
//             cartItem.setUser(user);
//             cartItem.setPizza(pizza);
//             cartItem.setSize(size);
//             cartItem.setQuantity(quantity);
//             cartItem.setCoupon(coupon);

//         } else {

//             cartItem.setQuantity(cartItem.getQuantity() + quantity);

//         }

//         cartRepository.save(cartItem);

//         return "redirect:/cart";
//     }

//     // VIEW CART
//     @GetMapping
//     public String viewCart(Model model, Principal principal) {

//         if (principal == null) {
//             return "redirect:/login";
//         }

//         User user = userRepository.findByUsername(principal.getName())
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         model.addAttribute("cartItems", cartRepository.findByUser(user));

//         return "admin/user/cart";
//     }

//     // DELETE CART ITEM
//     @GetMapping("/delete/{id}")
//     public String deleteCartItem(@PathVariable Long id) {

//         cartRepository.deleteById(id);

//         return "redirect:/cart";
//     }

//     // INCREASE QUANTITY
//     @GetMapping("/addQuantity/{id}")
//     public String addQuantity(@PathVariable Long id) {

//         Cart cartItem = cartRepository.findById(id).orElseThrow();

//         cartItem.setQuantity(cartItem.getQuantity() + 1);

//         cartRepository.save(cartItem);

//         return "redirect:/cart";
//     }

//     // DECREASE QUANTITY
//     @GetMapping("/deleteQuantity/{id}")
//     public String deleteQuantity(@PathVariable Long id) {

//         Cart cartItem = cartRepository.findById(id).orElseThrow();

//         int qty = cartItem.getQuantity() - 1;

//         if (qty < 1) {
//             cartRepository.delete(cartItem);
//         } else {
//             cartItem.setQuantity(qty);
//             cartRepository.save(cartItem);
//         }

//         return "redirect:/cart";
//     }
// }

package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Cart;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.repository.CartRepository;
import com.example.sliceeehouse.repository.PizzaRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class UserCartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;

    public UserCartController(CartRepository cartRepository,
                              UserRepository userRepository,
                              PizzaRepository pizzaRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.pizzaRepository = pizzaRepository;
    }

    // ================= COMMON PRICE METHOD =================
    private double getPrice(Pizza pizza) {
        if (pizza.getDiscountedPrice() != null && pizza.getDiscountedPrice() > 0) {
            return pizza.getDiscountedPrice();
        }
        return pizza.getSellingPrice(); // ⚠️ MUST NOT throw exception
    }

    // ================= ADD TO CART =================
    @PostMapping("/add")
    public String addToCart(@RequestParam Long pizzaId,
                           @RequestParam String size,
                           @RequestParam int quantity,
                           @RequestParam(required = false) String coupon,
                           Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        // 🔥 FIX: repository should return Cart (NOT Optional)
  Cart cartItem = cartRepository
        .findByUserAndPizzaAndSize(user, pizza, size)
        .orElse(null);

        if (cartItem == null) {
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setPizza(pizza);
            cartItem.setSize(size);
            cartItem.setQuantity(quantity);
            cartItem.setCoupon(coupon);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        double price = getPrice(pizza);
        cartItem.setTotalPrice(price * cartItem.getQuantity());

        cartRepository.save(cartItem);

        return "redirect:/cart";
    }

    // ================= VIEW CART =================
    @GetMapping
    public String viewCart(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser(user);

        double total = 0;
        for (Cart c : cartItems) {
            total += (c.getTotalPrice() != null ? c.getTotalPrice() : 0);
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", total);

        return "cart/view_cart";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }

    // ================= INCREASE =================
    @GetMapping("/addQuantity/{id}")
    public String addQuantity(@PathVariable Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.setQuantity(cart.getQuantity() + 1);

        double price = getPrice(cart.getPizza());
        cart.setTotalPrice(price * cart.getQuantity());

        cartRepository.save(cart);

        return "redirect:/cart";
    }

    // ================= DECREASE =================
    @GetMapping("/deleteQuantity/{id}")
    public String deleteQuantity(@PathVariable Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        int qty = cart.getQuantity() - 1;

        if (qty < 1) {
            cartRepository.delete(cart);
        } else {
            cart.setQuantity(qty);

            double price = getPrice(cart.getPizza());
            cart.setTotalPrice(price * qty);

            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }
}