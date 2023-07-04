package com.example.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.AuthCredentials;
import com.example.authservice.dto.TokenDTO;
import com.example.authservice.dto.UserAuthDto;
import com.example.authservice.entity.UserAuth;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.mapper.IUserAuthMapper;
import com.example.authservice.repository.IUserAuthRepository;
import com.example.authservice.security.JwtnProvider;
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
    private JwtnProvider jwtProvider;

    public UserAuthDto createFirst(UserAuthDto userAuthDto) {
        List<UserAuth> userAuths = iUserAuthRepository.findAll();
        if (!userAuths.isEmpty())
            throw new ResourceNotFoundException(Text.USERS_EXISTS);
        return create(userAuthDto);
    }

    public UserAuthDto create(UserAuthDto userAuthDto) {
        Optional<UserAuth> optUserAuth = iUserAuthRepository.findByUsername(userAuthDto.getUsername());
        if (optUserAuth.isPresent())
            throw new ResourceNotFoundException(Text.USERNAME_ALREADY_EXISTS);
        String pass = passwordEncode.encode(userAuthDto.getPass());
        userAuthDto.setPass(pass);
        return save(userAuthDto);
    }

    public TokenDTO login(AuthCredentials authCredentials) {
        Optional<UserAuth> optUserAuth = iUserAuthRepository.findByUsername(authCredentials.getUsername());
        if (optUserAuth.isEmpty())
            throw new ResourceNotFoundException(Text.USERNAME_ALREADY_EXISTS);
        if (!passwordEncode.matches(authCredentials.getPass(), optUserAuth.get().getPass())) 
            throw new ResourceNotFoundException(Text.PASS_INCORRECT);
        return new TokenDTO(jwtProvider.createToken(optUserAuth.get().getUsername(), optUserAuth.get().getEmail()));

    }

    public TokenDTO validateToken(String token) {
        if (!jwtProvider.validateToken(token))
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        String username = jwtProvider.getUsernameFromToken(token);
        if (iUserAuthRepository.findByUsername(username).isEmpty())
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        return new TokenDTO(token);
    }

    public List<UserAuthDto> findAll(Pageable pageable) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.findAll(pageable));
    }

    private UserAuthDto save(UserAuthDto userAuthDto) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.save(iUserAuthMapper.dtoToEntity(userAuthDto)));
    }

}