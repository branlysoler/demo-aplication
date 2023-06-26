package com.example.demo.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private IUserRepository iUserRepository;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("The user with email " + email + "does not exist"));

        return new UserDetailsImpl(user);
    }
    
}