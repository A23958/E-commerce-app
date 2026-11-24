package com.andy.ecommerce_project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andy.ecommerce_project.Model.Users;

@Repository
public interface UserRepo  extends JpaRepository<Users,Integer>{
     
    Users findByUsername(String username);
}
