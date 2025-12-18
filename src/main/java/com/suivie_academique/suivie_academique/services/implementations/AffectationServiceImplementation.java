package com.suivie_academique.suivie_academique.services.implementations;

import com.suivie_academique.suivie_academique.dto.AffectationDTO;
import com.suivie_academique.suivie_academique.entities.Affectation;
import com.suivie_academique.suivie_academique.entities.Affectationid;
import com.suivie_academique.suivie_academique.mappers.AffectationMappers;
import com.suivie_academique.suivie_academique.mappers.CoursMappers;
import com.suivie_academique.suivie_academique.mappers.PersonnelMappers;
import com.suivie_academique.suivie_academique.repositories.AffectationRepos;
// import com.suivie_academique.suivie_academique.repositories.CoursRepos;
import com.suivie_academique.suivie_academique.services.interfaces.AffectationServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffectationServiceImplementation implements AffectationServiceInterface {

    private AffectationRepos affectationRepos;

    private AffectationMappers affectationMapper;

    private PersonnelMappers personnelMapper;

    private CoursMappers coursMapper;


    public AffectationServiceImplementation(AffectationRepos affectationRepos, AffectationMappers affectationMapper, PersonnelMappers personnelMapper, CoursMappers coursMapper) {
        this.affectationRepos = affectationRepos;
        this.affectationMapper = affectationMapper;
        this.personnelMapper = personnelMapper;
        this.coursMapper = coursMapper;
    }

    @Override
    public AffectationDTO save(AffectationDTO affectationDTO) {
//        if(coursRepos.existsById(affectationDTO.getCodeAffectation())) && personnelRepository.existsById(affectationDTO.getCodeAffectation().getCodePersonne()){
//            return affectationMapper.toDTO(affectationRepository.save(affectationMapper.toEntity(affectationDTO)));
//        }

        return null;
    }

    @Override
    public List<AffectationDTO> getAll() {
        return affectationRepos.findAll().stream().map(
                affectationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AffectationDTO getById(Affectationid codeAffectation) {
        Affectation affectation = affectationRepos.findById(codeAffectation).orElse(null);
        if (affectation == null) {
            throw new RuntimeException("Affectation non trouvé");
        }else{
            return  affectationMapper.toDTO(affectation);
        }
    }

    @Override
    public AffectationDTO update(Affectationid codeAffectation, AffectationDTO affectationDTO) {
        Affectation affectation = affectationRepos.findById(codeAffectation).orElse(null);
        if(affectation == null){
            throw new RuntimeException("AffectationId non trouvé");
        }
        affectation.setPersonnel(personnelMapper.toEntity(affectationDTO.getPersonnel()));
        affectation.setCours(coursMapper.toEntity(affectationDTO.getCours()));
        affectationRepos.save(affectation);
        return affectationMapper.toDTO(affectation);
    }

    @Override
    public void delete(Affectationid codeAffectation) {
        boolean exist = affectationRepos.existsById(codeAffectation);
        if (!exist){
            throw new RuntimeException("Cours non trouvée");
        }else{
            affectationRepos.deleteById(codeAffectation);
        }
    }



}