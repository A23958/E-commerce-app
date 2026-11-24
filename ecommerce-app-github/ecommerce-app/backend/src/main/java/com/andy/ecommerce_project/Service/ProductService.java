package com.andy.ecommerce_project.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andy.ecommerce_project.Repository.ProductRepo;
import com.andy.ecommerce_project.Model.Product;

@Service 
public class ProductService {
     
     @Autowired 
    private ProductRepo repo;

    public List<Product> getAll(){
        return repo.findAll();
    }

    public Product getProduct(int id){
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
          //we have to store/send the image also along with the product ,to do that you need to get the product details and convert into btyes
          //get all the image details 
          //System.out.println("SERVICE CALLED");
          product.setImageName(imageFile.getOriginalFilename());
          product.setImageType(imageFile.getContentType());
          product.setImageDate(imageFile.getBytes());  // this might throw an exception
          //System.out.println("BEFORE SAVE");

          return repo.save(product); // now save the products with updated details
          
                    
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        
        return repo.save(product);
    }

    public void deleteProduct(int id) {
         repo.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return repo.searchProducts(keyword);
    }

   

    
}
