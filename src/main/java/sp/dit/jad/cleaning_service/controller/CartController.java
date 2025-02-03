// Path: src/main/java/sp/dit/jad/cleaning_service/controller/CartController.java
package sp.dit.jad.cleaning_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.model.CartItem;
import sp.dit.jad.cleaning_service.dto.CartItemDTO;
import sp.dit.jad.cleaning_service.service.CartService;
import sp.dit.jad.cleaning_service.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(@RequestParam("serviceId") Long serviceId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            CartItem cartItem = cartService.addToCart(user, serviceId);
            
            CartItemDTO dto = convertToDTO(cartItem);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<?> removeFromCart(@RequestParam("serviceId") Long serviceId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            cartService.removeFromCart(user, serviceId);
            return ResponseEntity.ok(Map.of("message", "Service removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<?> getCartItems() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            List<CartItem> cartItems = cartService.getCartItems(user);
            
            List<CartItemDTO> dtos = cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<?> clearCart() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            cartService.clearCart(user);
            return ResponseEntity.ok(Map.of("message", "Cart cleared"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setServiceId(cartItem.getService().getServiceId());
        dto.setServiceName(cartItem.getService().getName());
        dto.setPrice(cartItem.getService().getBasePrice());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}