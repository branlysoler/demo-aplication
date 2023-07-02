package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ICategoryMapper;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.util.Text;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private ICategoryMapper categoryMapper;

    public CategoryDTO create(CategoryDTO categoryDto) {
        Optional<Category> optCategory = iCategoryRepository.findByName(categoryDto.getName());
        if (optCategory.isPresent())
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);
        return save(categoryDto);
    }

    public CategoryDTO update(CategoryDTO categoryDto) {
        Optional<CategoryDTO> optCategoryById = findById(categoryDto.getId());
        if (optCategoryById.isEmpty())
            throw new ResourceNotFoundException(Text.ID_NOT_EXISTS);
        Optional<CategoryDTO> optCategoryByName = findByName(categoryDto.getName());
        String nameCompare = optCategoryById.get().getName();
        if (optCategoryByName.isPresent() && nameCompare != optCategoryByName.get().getName())
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);
        return save(categoryDto);
    }

    private CategoryDTO save(CategoryDTO categoryDto) {
        return categoryMapper.entityToDTO(iCategoryRepository.save(categoryMapper.dtoToEntity(categoryDto)));
    }

    public List<CategoryDTO> findAll(Pageable pageable) {
        return categoryMapper.entityToDTO(iCategoryRepository.findAll(pageable));
    }

    public Optional<CategoryDTO> findById(Long id) {
        return iCategoryRepository.findById(id).map(categoryMapper::entityToDTO);
    }

    public Optional<CategoryDTO> findByName(String name) {
        return iCategoryRepository.findByName(name).map(categoryMapper::entityToDTO);
    }

    public void deleteById(Long id) {
        iCategoryRepository.deleteById(id);
    }

    public void deleteAll() {
        iCategoryRepository.deleteAll();
    }

}