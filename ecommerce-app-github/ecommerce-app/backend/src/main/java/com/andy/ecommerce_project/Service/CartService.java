package com.andy.ecommerce_project.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartService {

     private final JdbcTemplate jdbc;

     public CartService(JdbcTemplate jc){
        this.jdbc = jc ;
     }

     public record CartItem(Integer productId,Integer quantity){}

     @Transactional   //groups all the stock updates together.
     public void checkout(List<CartItem> items){

        if(items == null || items.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart is Empty");
        }

        Map<Integer, Long> quantities = new TreeMap<>();
        for (CartItem item : items) {
            if (item == null || item.productId == null || item.productId() <= 0
                    || item.quantity() == null || item.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product ID or quantity");
            }

            quantities.merge(item.productId(), item.quantity().longValue(), Long::sum);
        }
        //updating db 
        for (var entry : quantities.entrySet()) {
            int productId = entry.getKey();
            long quantity = entry.getValue();

            int updated = jdbc.update("""
                    UPDATE public.product
                    SET stock_quantity = stock_quantity - ?
                    WHERE id = ? AND stock_quantity >= ?
                    """, quantity, productId, quantity);

            if (updated == 0) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Product " + productId + " is missing or has insufficient stock");
            }
        }

        
     }


}
