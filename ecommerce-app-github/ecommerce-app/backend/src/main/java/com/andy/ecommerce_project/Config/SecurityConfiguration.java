package com.andy.ecommerce_project.Config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//telling spring to use the config class not the default
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/auth/csrf").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler((request, response, authentication) ->
                                response.setStatus(204))
                        .failureHandler((request, response, exception) ->
                                response.setStatus(401)))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .exceptionHandling(errors -> errors
                        .authenticationEntryPoint((request, response, exception) ->
                                response.setStatus(401)))
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){      //telling spring to use this authetication provider

        //DaoAuthenticationProvider implements AuthenticationProvider with connects with the database 
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
         provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); //for reading encoded password 
         //provider.setUserDetailsService(userDetailsService);
         
        return provider;
    }

    

}
