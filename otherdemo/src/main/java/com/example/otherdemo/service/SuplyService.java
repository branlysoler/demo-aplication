package com.example.otherdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.otherdemo.dto.SuplyDTO;
import com.example.otherdemo.mapper.ISuplyMapper;
import com.example.otherdemo.repository.ISuplyRepository;


@Service
public class SuplyService {

    @Autowired
    private ISuplyRepository iSuplyRepository;

    @Autowired
    private ISuplyMapper suplyMapper;

 
    public Boolean verifyCreate(SuplyDTO suplyDTO) {
        return findByName(suplyDTO.getName()).isEmpty();
    }

    public Boolean verifyUpdate(SuplyDTO suplyDTO) {
        Optional<SuplyDTO> optSuplyById = findById(suplyDTO.getId());
        Optional<SuplyDTO> optSuplyByName = findByName(suplyDTO.getName());
        if (optSuplyById.isPresent()) {
            String nameCompare = optSuplyById.get().getName();
            if (optSuplyByName.isPresent() && nameCompare != optSuplyByName.get().getName()) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public SuplyDTO save(SuplyDTO suplyDto) {
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