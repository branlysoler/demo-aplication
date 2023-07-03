package com.example.otherdemoservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.otherdemoservice.dto.SuplyDTO;
import com.example.otherdemoservice.entity.Suply;
import com.example.otherdemoservice.exception.ResourceNotFoundException;
import com.example.otherdemoservice.mapper.ISuplyMapper;
import com.example.otherdemoservice.repository.ISuplyRepository;
import com.example.otherdemoservice.util.Text;

@Service
public class SuplyService {

    @Autowired
    private ISuplyRepository iSuplyRepository;

    @Autowired
    private ISuplyMapper suplyMapper;

    public SuplyDTO create(SuplyDTO suplyDto) {
        Optional<Suply> optSuply = iSuplyRepository.findByName(suplyDto.getName());
        if (optSuply.isPresent())
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);
        return save(suplyDto);
    }

    public SuplyDTO update(SuplyDTO suplyDto) {
        Optional<SuplyDTO> optSuplyById = findById(suplyDto.getId());
        if (optSuplyById.isEmpty())
            throw new ResourceNotFoundException(Text.ID_NOT_EXISTS);
        Optional<SuplyDTO> optSuplyByName = findByName(suplyDto.getName());
        String nameCompare = optSuplyById.get().getName();
        if (optSuplyByName.isPresent() && nameCompare != optSuplyByName.get().getName())
            throw new ResourceNotFoundException(Text.NAME_ALREADY_EXISTS);
        return save(suplyDto);
    }

    private SuplyDTO save(SuplyDTO suplyDto) {
        return suplyMapper.entityToDTO(iSuplyRepository.save(suplyMapper.dtoToEntity(suplyDto)));
    }

    public List<SuplyDTO> findAll(Pageable pageable) {
        return suplyMapper.entityToDTO(iSuplyRepository.findAll(pageable));
    }

    public Optional<SuplyDTO> findById(Long id) {
        return iSuplyRepository.findById(id).map(suplyMapper::entityToDTO);
    }

    public Optional<SuplyDTO> findByName(String name) {
        return iSuplyRepository.findByName(name).map(suplyMapper::entityToDTO);
    }

    public void deleteById(Long id) {
        iSuplyRepository.deleteById(id);
    }

    public void deleteAll() {
        iSuplyRepository.deleteAll();
    }

}