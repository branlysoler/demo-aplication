package com.example.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.authservice.dto.UserAuthDTO;
import com.example.authservice.entity.UserAuth;

@Mapper(componentModel = "spring")
public interface IUserAuthMapper {

    UserAuthDTO entityToDTO(UserAuth category);

    UserAuth dtoToEntity(UserAuthDTO userAuthDto);

    List<UserAuthDTO> entityToDTO(Iterable<UserAuth> iterable);

    
}