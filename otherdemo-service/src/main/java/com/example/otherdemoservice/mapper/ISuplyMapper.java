package com.example.otherdemoservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.otherdemoservice.dto.SuplyDTO;
import com.example.otherdemoservice.entity.Suply;

@Mapper(componentModel = "spring")
public interface ISuplyMapper {

    SuplyDTO entityToDTO(Suply suply);

    Suply dtoToEntity(SuplyDTO suplyDTO);

    List<SuplyDTO> entityToDTO(Iterable<Suply> suplies);

    
}