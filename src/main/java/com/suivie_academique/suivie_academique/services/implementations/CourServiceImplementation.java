package com.suivie_academique.suivie_academique.services.implementations;

import com.suivie_academique.suivie_academique.dto.CoursDTO;
import com.suivie_academique.suivie_academique.entities.Cours;
import com.suivie_academique.suivie_academique.mappers.CoursMappers;
import com.suivie_academique.suivie_academique.repositories.CoursRepos;
import com.suivie_academique.suivie_academique.services.interfaces.CourServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourServiceImplementation implements CourServiceInterface {

    private CoursMappers coursMappers;
    private CoursRepos coursRepos;

    public CourServiceImplementation(CoursMappers coursMappers, CoursRepos coursRepos) {
        this.coursMappers = coursMappers;
        this.coursRepos = coursRepos;
    }

    @Override
    public CoursDTO save(CoursDTO coursDTO) {
        if (coursDTO.getDescCours().isEmpty() || coursDTO.getLabelCours().isEmpty()) {
            throw new RuntimeException("Donne invalide");
        }else{
        return coursMappers.toDTO(coursRepos.save(coursMappers.toEntity(coursDTO)));
        }
    }

    @Override
    public List<CoursDTO> getAll() {
        return coursRepos.findAll().stream().map(coursMappers::toDTO).collect(Collectors.toList());
    }

    @Override
    public CoursDTO getById(String codeCours) {
        Cours cours = coursRepos.findById(codeCours).get();
        if(cours == null ){
            throw new RuntimeException("cours non trouvée");
        }else {
            return coursMappers.toDTO(cours);
        }
    }

    @Override
    public CoursDTO update(String codeCours, CoursDTO coursDTO) {
        Cours cours = coursRepos.findById(codeCours).get();
        if (cours == null ){
            throw  new RuntimeException("cours non trouvé");
        }else {
            cours.setDescCours(coursDTO.getDescCours());
            cours.setLabelCours(coursDTO.getLabelCours());
            cours.setCodeCours(coursDTO.getCodeCours());
            cours.setNbCreditCours(coursDTO.getNbCreditCours());
            cours.setNbHeureCours(coursDTO.getNbHeureCours());
            coursRepos.save(cours);
            return coursMappers.toDTO(cours);
        }
    }

    @Override
    public void delete(String codeCours) {
        boolean exist = coursRepos.existsById(codeCours);
        if (!exist){
            throw new RuntimeException("Cours non trouvée");
        }else{
            coursRepos.deleteById(codeCours);
        }
    }
}
