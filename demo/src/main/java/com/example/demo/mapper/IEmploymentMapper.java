package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.EmploymentDTO;
import com.example.demo.entity.Employment;

@Mapper(componentModel = "spring")
public interface IEmploymentMapper {

    @Mapping(target = "category.employments", ignore = true)
    EmploymentDTO entityToDTO(Employment employment);

    Employment dtoToEntity(EmploymentDTO employmentDTO);

    @Mapping(target = "category.employments", ignore = true)
    List<EmploymentDTO> entityToDTO(Iterable<Employment> employments);
    
}