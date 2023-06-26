package com.example.otherdemo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.otherdemo.dto.SuplyDTO;
import com.example.otherdemo.entity.Suply;

@Mapper(componentModel = "spring")
public interface ISuplyMapper {

    SuplyDTO entityToDTO(Suply suply);

    Suply dtoToEntity(SuplyDTO suplyDTO);

    List<SuplyDTO> entityToDTO(Iterable<Suply> suplies);

    
}