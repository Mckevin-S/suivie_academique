package com.suivie_academique.suivie_academique.services.implementations;


import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.entities.Salle;
import com.suivie_academique.suivie_academique.mappers.SalleMappers;
import com.suivie_academique.suivie_academique.repositories.SalleRepos;
import com.suivie_academique.suivie_academique.services.interfaces.SalleServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalleServiceImplementation implements SalleServiceInterface {

    private SalleRepos salleRepos;

    private SalleMappers salleMappers;

    public SalleServiceImplementation(SalleRepos salleRepos, SalleMappers salleMappers) {
        this.salleRepos = salleRepos;
        this.salleMappers = salleMappers;
    }

    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        System.out.println(salleDTO.getStatusSalle().name());
        if(salleDTO.getCodeSalle().isEmpty() || salleDTO.getContenance() < 10 ){
            throw new RuntimeException("Donne de salle invalide");
        }else{
            Salle salle = salleRepos.save(salleMappers.toSalle(salleDTO));
            return salleMappers.toDTO(salle);
        }
    }

    @Override
    public List<SalleDTO> getAll() {
        return salleRepos.findAll().stream().map(salleMappers::toDTO).collect(Collectors.toList());
    }

    @Override
    public SalleDTO getById(String codeSalle) {
        Salle salle = salleRepos.findById(codeSalle).get();
        if(salle == null ){
            throw new RuntimeException("Salle non trouvée");
        }else {
            return salleMappers.toDTO(salle);
        }
    }

    @Override
    public SalleDTO update(String codeSalle, SalleDTO salleDTO) {
        Salle salle = salleRepos.findById(codeSalle).get();
        if (salle == null ){
            throw  new RuntimeException("salle non trouvé");
        }else {
            salle.setStatusSalle(salleDTO.getStatusSalle());
            salle.setDescSalle(salleDTO.getDescSalle());
            salle.setContenance(salleDTO.getContenance());
            salleRepos.save(salle);
            return salleMappers.toDTO(salle);
        }
    }

    @Override
    public void delete(String codeSalle) {
        boolean exist = salleRepos.existsById(codeSalle);
        if (!exist){
            throw new RuntimeException("Salle non trouvée");
        }else{
            salleRepos.deleteById(codeSalle);
        }
    }
}
