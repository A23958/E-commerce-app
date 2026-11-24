package com.andy.ecommerce_project.Controller;

import java.net.ResponseCache;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.andy.ecommerce_project.Service.ProductService;
import com.andy.ecommerce_project.Model.Product;


@RestController 
@CrossOrigin //(origins = "http://localhost:5173")    //allows front and back end to communicate when running on different ports
@RequestMapping("/api")

public class ProductController {

   @Autowired 
   private ProductService service ;

   
   @GetMapping("")
   public String hello(){
       return "hello";
   }


   @GetMapping("/products") 
   // want to take control of response status use Response Entity
   public ResponseEntity<List<Product>> GetAllProducts(){
                 return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
   }


   @GetMapping("/product/{id}")
   public ResponseEntity<Product> GetProduct(@PathVariable int id){
      
           Product product = service.getProduct(id);
           if(product != null)
             return  new ResponseEntity<>(product,HttpStatus.OK);
           else
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
    
   @PostMapping("/product") 
   public ResponseEntity<?> addProduct(
           @RequestPart Product product,
           @RequestPart MultipartFile imageFile){
      
      try {

            Product prod1 = service.addProduct(product , imageFile);
            return new ResponseEntity<>(prod1,HttpStatus.CREATED);

      } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR); //shows message to the user 
           
      }
   }

   @GetMapping("/product/{productId}/image")
   public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

      Product product = service.getProduct(productId);///look for the product in the database
      byte[] imageFile = product.getImageDate();

      return ResponseEntity.ok()
      .contentType(org.springframework.http.MediaType.valueOf(product.getImageType())) //returns a String saved earlier and converts that String into a Spring MediaType object,
      .body(imageFile);
      // Respond with status 200 OK, label the content with the stored image type,
      //  and put the image bytes in the body.

   }

   @PutMapping("/product/{id}")
   public ResponseEntity<String> updateProduct(@PathVariable int id,
           @RequestPart Product product,
           @RequestPart MultipartFile imageFile){

         try {
            service.updateProduct(id, product, imageFile);
            return new ResponseEntity<>("Updated" , HttpStatus.OK);
            
         } catch (Exception e) {
            return new ResponseEntity<>("Failed to update the product" , HttpStatus.BAD_REQUEST);
         }
         
   }
   
   @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

      Product  product2 = service.getProduct(id);
      if(product2 != null){
         service.deleteProduct(id);
         return new ResponseEntity<>("Deleted" , HttpStatus.OK);
      }else{
         return new ResponseEntity<>("Product not found" , HttpStatus.NOT_FOUND);
      }
       
      
    }

    @GetMapping("/products/search")
    public List<Product> searchProduct(@RequestParam("keyword") String keyword){
        return service.searchProduct(keyword);
    }



         
   
}
