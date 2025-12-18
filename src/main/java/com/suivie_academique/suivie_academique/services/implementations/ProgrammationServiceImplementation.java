package com.suivie_academique.suivie_academique.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.suivie_academique.suivie_academique.dto.ProgrammationDTO;
import com.suivie_academique.suivie_academique.entities.Programmation;
import com.suivie_academique.suivie_academique.mappers.ProgrammationMappers;
import com.suivie_academique.suivie_academique.repositories.ProgrammationRepos;
import com.suivie_academique.suivie_academique.services.interfaces.ProgrammationServiceInterface;

import lombok.AllArgsConstructor;

@Service
public class ProgrammationServiceImplementation implements ProgrammationServiceInterface{

    private ProgrammationMappers programmationMappers;
    private ProgrammationRepos programmationRepos;

    public ProgrammationServiceImplementation(ProgrammationMappers programmationMappers, ProgrammationRepos programmationRepos) {
        this.programmationMappers = programmationMappers;
        this.programmationRepos = programmationRepos;
    }

    public ProgrammationServiceImplementation() {
    }

    @Override
    public ProgrammationDTO save(ProgrammationDTO programmationDTO) {
        if ( programmationDTO.getDateProgrammation() == null || programmationDTO.getDateProgrammation() == null ) {
            throw new RuntimeException("Donne de programmation invalide");
        } else {
            Programmation programmation = programmationRepos.save(programmationMappers.toEntity(programmationDTO));
            return programmationMappers.toDTO(programmation);

        }
    }

    @Override
    public List<ProgrammationDTO> getAll() {
        return programmationRepos.findAll().stream().map(programmationMappers::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProgrammationDTO getById(Integer codeProgrammation) {
        Programmation programmation = programmationRepos.findById(codeProgrammation).get();
        if(programmation == null ){
            throw new RuntimeException("programmation non trouvée");
        }else {
            return programmationMappers.toDTO(programmation);
        }    
    }

    @Override
    public ProgrammationDTO update(Integer codeProgrammation, ProgrammationDTO programmationDTO) {
        Programmation programmation = programmationRepos.findById(codeProgrammation).get();
        if (programmation == null ){
            throw  new RuntimeException("programation non trouvé");
        }else {
            programmation.setNbreHeureProgrammation(programmationDTO.getNbreHeureProgrammation());
            programmation.setDateProgrammation(programmationDTO.getDateProgrammation());
            programmation.setDebutProgrammation(programmationDTO.getDebutProgrammation());
            programmation.setFinProgrammation(programmationDTO.getFinProgrammation());
            programmation.setStatutProgrammation(programmationDTO.getStatutProgrammation());
            programmationRepos.save(programmation);
            return programmationMappers.toDTO(programmation);
        }
    }

    @Override
    public void delete(Integer codeProgrammation) {
        boolean exist = programmationRepos.existsById(codeProgrammation);
        if (!exist){
            throw new RuntimeException("Salle non trouvée");
        }else{
            programmationRepos.deleteById(codeProgrammation);
        }    
    }

}
