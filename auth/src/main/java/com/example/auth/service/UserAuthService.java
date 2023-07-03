package com.example.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.dto.TokenDTO;
import com.example.auth.dto.UserAuthDto;
import com.example.auth.entity.UserAuth;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.mapper.IUserAuthMapper;
import com.example.auth.repository.IUserAuthRepository;
import com.example.auth.security.TokenProvider;
import com.example.auth.util.Text;

@Service
public class UserAuthService {

    @Autowired
    private IUserAuthRepository iUserAuthRepository;

    @Autowired
    private IUserAuthMapper iUserAuthMapper;

    @Autowired
    private PasswordEncoder passwordEncode;

    @Autowired
    private TokenProvider tokenProvider;

    public UserAuthDto create(UserAuthDto userAuthDto) {
        Optional<UserAuth> optUserAuth = iUserAuthRepository.findByEmail(userAuthDto.getEmail());
        if (optUserAuth.isPresent())
            throw new ResourceNotFoundException(Text.EMAIL_ALREADY_EXISTS);
        userAuthDto.setPass(passwordEncode.encode(userAuthDto.getPass()));
        return save(userAuthDto);
    }

    private UserAuthDto save(UserAuthDto userAuthDto) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.save(iUserAuthMapper.dtoToEntity(userAuthDto)));
    }

    public TokenDTO validateToken(String token) {
        if (!tokenProvider.validateToken(token))
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = tokenProvider
                .getAuthentication(token);
        final String email = (String) usernamePasswordAuthenticationToken.getPrincipal();
        if (!iUserAuthRepository.findByEmail(email).isPresent())
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        return new TokenDTO(token);
    }

}