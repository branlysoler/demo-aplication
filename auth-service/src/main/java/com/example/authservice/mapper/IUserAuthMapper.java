package com.example.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.authservice.dto.UserAuthDto;
import com.example.authservice.entity.UserAuth;

@Mapper(componentModel = "spring")
public interface IUserAuthMapper {

    UserAuthDto entityToDTO(UserAuth category);

    UserAuth dtoToEntity(UserAuthDto userAuthDto);

    List<UserAuthDto> entityToDTO(Iterable<UserAuth> iterable);

    
}