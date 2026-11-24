package com.andy.ecommerce_project.Model;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // you want to create a table of this type
@Data 
@AllArgsConstructor 
@NoArgsConstructor 


public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto generate ids
    private Integer id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")  //When sending or receiving the releaseDate
    private Date releaseDate;

    private Boolean productAvailable;
    private Integer stockQuantity;

    private String imageName;
    private String imageType;
    
    @Lob 
    private byte[] imageDate;  //since an image is large use byte datatype and Lob annotation

}
