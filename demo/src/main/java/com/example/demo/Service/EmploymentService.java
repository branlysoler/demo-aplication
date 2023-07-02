package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmploymentDTO;
import com.example.demo.entity.Employment;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.IEmploymentMapper;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IEmploymentRepository;
import com.example.demo.util.Text;

@Service
public class EmploymentService {

    @Autowired
    private IEmploymentRepository iEmploymentRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private IEmploymentMapper employmentMapper;


    public EmploymentDTO create(EmploymentDTO employmentDto) {

        Optional<Employment> optEmploymentByName = iEmploymentRepository.findByName(employmentDto.getName());
        if (optEmploymentByName.isPresent())
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);

        Optional<Employment> optEmploymentByLevel = iEmploymentRepository.findByLevel(employmentDto.getLevel());
        if (optEmploymentByLevel.isPresent())
            throw new ResourceNotFoundException(Text.LEVEL_ALREADY_EXISTS);

        return save(employmentDto);
    }

    public EmploymentDTO update(EmploymentDTO employmentDto) {

        Optional<EmploymentDTO> optEmploymentById = findById(employmentDto.getId());
        if (optEmploymentById.isEmpty())
            throw new ResourceNotFoundException(Text.ID_NOT_EXISTS);

        Optional<EmploymentDTO> optEmploymentByName = findByName(employmentDto.getName());
        String nameCompare = optEmploymentById.get().getName();
        if ((optEmploymentByName.isPresent() && nameCompare != optEmploymentByName.get().getName()))
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);

        Optional<EmploymentDTO> optEmploymentByLevel = findByLevel(employmentDto.getLevel());
        Integer levelCompare = optEmploymentById.get().getLevel();
        if ((optEmploymentByLevel.isPresent() && levelCompare != optEmploymentByLevel.get().getLevel()))
            throw new ResourceNotFoundException(Text.LEVEL_ALREADY_EXISTS);
      
        return save(employmentDto);
    }

    private EmploymentDTO save(EmploymentDTO employmentDTO) {
        return iCategoryRepository.findById(employmentDTO.getCategory().getId()).map(catego -> {
            Employment employment = employmentMapper.dtoToEntity(employmentDTO);
            employment.setCategory(catego);
            final Employment savedEmployment = iEmploymentRepository.save(employment);
            return employmentMapper.entityToDTO(savedEmployment);
        }).orElseThrow(() -> new ResourceNotFoundException(Text.ID_CATEGORY_NOT_EXISTS));
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