package com.example.demoservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demoservice.dto.CategoryDTO;
import com.example.demoservice.entity.Category;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    @Mapping(target = "employments", ignore = true)
    CategoryDTO entityToDTO(Category category);

    Category dtoToEntity(CategoryDTO categoryDto);

    @Mapping(target = "employments", ignore = true)
    List<CategoryDTO> entityToDTO(Iterable<Category> categories);

    
}