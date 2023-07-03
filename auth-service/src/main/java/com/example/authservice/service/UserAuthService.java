package com.example.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.AuthCredentials;
import com.example.authservice.dto.TokenDTO;
import com.example.authservice.dto.UserAuthDTO;
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

    public UserAuthDTO createFirst(UserAuthDTO userAuthDto) {
        List<UserAuth> userAuths = iUserAuthRepository.findAll();
        if (!userAuths.isEmpty())
            throw new ResourceNotFoundException(Text.USERS_EXISTS);
        return create(userAuthDto);
    }

    public UserAuthDTO create(UserAuthDTO userAuthDto) {
        Optional<UserAuth> optUserAuth = iUserAuthRepository.findByEmail(userAuthDto.getEmail());
        if (optUserAuth.isPresent())
            throw new ResourceNotFoundException(Text.EMAIL_ALREADY_EXISTS);
        userAuthDto.setPass(passwordEncode.encode(userAuthDto.getPass()));
        return save(userAuthDto);
    }

    public TokenDTO login(AuthCredentials authCredentials) {
        Optional<UserAuth> optUserAuth = iUserAuthRepository.findByEmail(authCredentials.getEmail());
        if (optUserAuth.isEmpty())
            throw new ResourceNotFoundException(Text.EMAIL_ALREADY_EXISTS);
        if (!passwordEncode.matches(authCredentials.getPass(), optUserAuth.get().getPass())) 
            throw new ResourceNotFoundException(Text.PASS_INCORRECT);
        return new TokenDTO(jwtProvider.createToken(optUserAuth.get().getName(), optUserAuth.get().getEmail()));

    }

    public TokenDTO validateToken(String token) {
        if (!jwtProvider.validateToken(token))
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        String email = jwtProvider.getEmailFromToken(token);
        if (iUserAuthRepository.findByEmail(email).isEmpty())
            throw new ResourceNotFoundException(Text.TOKEN_INVALID);
        return new TokenDTO(token);
    }

    public List<UserAuthDTO> findAll(Pageable pageable) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.findAll(pageable));
    }

    private UserAuthDTO save(UserAuthDTO userAuthDto) {
        return iUserAuthMapper.entityToDTO(iUserAuthRepository.save(iUserAuthMapper.dtoToEntity(userAuthDto)));
    }

}