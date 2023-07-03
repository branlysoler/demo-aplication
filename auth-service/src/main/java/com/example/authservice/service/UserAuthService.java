package com.example.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.TokenDTO;
import com.example.authservice.dto.UserAuthDto;
import com.example.authservice.entity.UserAuth;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.mapper.IUserAuthMapper;
import com.example.authservice.repository.IUserAuthRepository;
import com.example.authservice.security.TokenProvider;
import com.example.authservice.util.Text;

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

    public UserAuthDto createFirst(UserAuthDto userAuthDto) {
        List<UserAuth> userAuths = iUserAuthRepository.findAll();
        if (!userAuths.isEmpty())
            throw new ResourceNotFoundException(Text.USERS_EXISTS);
        return create(userAuthDto);
    }

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

    public List<UserAuthDto> findAll(Pageable pageable) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.findAll(pageable));
    }

}