package com.andy.ecommerce_project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.ecommerce_project.Service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
        
        //@Autowired
        private final CartService cartService;

        public CartController(CartService cartService){
            this.cartService = cartService;
        }

        @PostMapping("/checkout")
        public ResponseEntity<Void> checkout(@RequestBody List<CartService.CartItem> items){
            cartService.checkout(items);
            return ResponseEntity.noContent().build();
        }

      

}
