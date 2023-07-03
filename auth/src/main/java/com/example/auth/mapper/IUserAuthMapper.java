package com.example.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.auth.dto.UserAuthDto;
import com.example.auth.entity.UserAuth;

@Mapper(componentModel = "spring")
public interface IUserAuthMapper {

    UserAuthDto entityToDTO(UserAuth category);

    UserAuth dtoToEntity(UserAuthDto userAuthDto);

    List<UserAuthDto> entityToDTO(Iterable<UserAuth> iterable);

    
}