package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmploymentDTO;
import com.example.demo.entity.Employment;
import com.example.demo.mapper.IEmploymentMapper;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IEmploymentRepository;

@Service
public class EmploymentService {

    @Autowired
    private IEmploymentRepository iEmploymentRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private IEmploymentMapper employmentMapper;

    public Boolean verifyCreate(EmploymentDTO employmentDTO) {
        Boolean result = findByName(employmentDTO.getName()).isEmpty()
                || findByLevel(employmentDTO.getLevel()).isEmpty();
        return result;
    }

    public Boolean verifyUpdate(EmploymentDTO employmentDTO) {
        Optional<EmploymentDTO> optEmploymentById = findById(employmentDTO.getId());
        Optional<EmploymentDTO> optEmploymentByName = findByName(employmentDTO.getName());
        Optional<EmploymentDTO> optEmploymentByLevel = findByLevel(employmentDTO.getLevel());
        if (optEmploymentById.isPresent()) {
            String nameCompare = optEmploymentById.get().getName();
            Integer levelCompare = optEmploymentById.get().getLevel();
            if ((optEmploymentByName.isPresent() && nameCompare != optEmploymentByName.get().getName())
                    || (levelCompare != optEmploymentByLevel.get().getLevel())) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public EmploymentDTO save(EmploymentDTO employmentDTO) {
        return iCategoryRepository.findById(employmentDTO.getCategory().getId()).map(catego -> {
            Employment employment = employmentMapper.dtoToEntity(employmentDTO);
            employment.setCategory(catego);
            final Employment createdEmployment = iEmploymentRepository.save(employment);
            return employmentMapper.entityToDTO(createdEmployment);
        }).orElse(null);
    }

    public List<EmploymentDTO> findAll(Pageable pageable) {
        return employmentMapper.entityToDTO(iEmploymentRepository.findAll(pageable));
    }

    public Optional<EmploymentDTO> findById(Long id) {
        return iEmploymentRepository.findById(id).map(employmentMapper::entityToDTO);
    }

    public Optional<EmploymentDTO> findByName(String name) {
        return iEmploymentRepository.findByName(name).map(employmentMapper::entityToDTO);
    }

    public Optional<EmploymentDTO> findByLevel(Integer level) {
        return iEmploymentRepository.findByLevel(level).map(employmentMapper::entityToDTO);
    }

    public Optional<EmploymentDTO> findByNameAndLevel(String name, Integer level) {
        return iEmploymentRepository.findByNameAndLevel(name, level).map(employmentMapper::entityToDTO);
    }

    public void deleteById(Long id) {
        iEmploymentRepository.deleteById(id);
    }

    public void deleteAll() {
        iEmploymentRepository.deleteAll();
    }

}