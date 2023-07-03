package com.example.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auth.dto.UserAuthDetailsImpl;
import com.example.auth.entity.UserAuth;
import com.example.auth.repository.IUserAuthRepository;
import com.example.auth.util.Text;

@Service
public class UserAuthDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserAuthRepository iUserAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = iUserAuthRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Text.EMAIL_NOT_EXIST));

        return new UserAuthDetailsImpl(userAuth);
    }

}