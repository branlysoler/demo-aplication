package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    @Mapping(target = "employments", ignore = true)
    CategoryDTO entityToDTO(Category category);

    Category dtoToEntity(CategoryDTO categoryDto);

    @Mapping(target = "employments", ignore = true)
    List<CategoryDTO> entityToDTO(Iterable<Category> categories);

    
}