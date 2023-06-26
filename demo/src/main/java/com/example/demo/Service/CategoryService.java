package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.mapper.ICategoryMapper;
import com.example.demo.repository.ICategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private ICategoryMapper categoryMapper;

    public Boolean verifyCreate(CategoryDTO categoryDTO) {
        return findByName(categoryDTO.getName()).isEmpty();
    }

    public Optional<CategoryDTO> verifyUpdate(CategoryDTO categoryDTO) {
        Optional<CategoryDTO> optCategoryById = findById(categoryDTO.getId());
        Optional<CategoryDTO> optCategoryByName = findByName(categoryDTO.getName());
        if (optCategoryById.isPresent()) {
            String nameCompare = optCategoryById.get().getName();
            if (optCategoryByName.isPresent() && nameCompare != optCategoryByName.get().getName()) {
                return Optional.empty();
            }
            return optCategoryById;
        }
        return Optional.empty();
    }

    public CategoryDTO save(CategoryDTO categoryDto) {
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