package com.example.demo.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entity.Category;
import com.example.demo.Records.CategoryRecord;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryRecord entityToDTO(Category category);

    List<CategoryRecord> entityToDTO(List<Category> categories);

    Category dtoToEntity(CategoryRecord categoryRecord);

    List<Category> dtoToEntity(List<CategoryRecord> categoryRecords);
    
}