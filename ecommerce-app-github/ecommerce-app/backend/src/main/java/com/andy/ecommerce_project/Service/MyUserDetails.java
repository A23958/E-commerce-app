package com.andy.ecommerce_project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andy.ecommerce_project.Model.UserPrincipal;
import com.andy.ecommerce_project.Model.Users;
import com.andy.ecommerce_project.Repository.UserRepo;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private  UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if(user ==  null){
            System.out.print("User not found");
            throw new  UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
    
}
