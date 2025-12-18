package com.suivie_academique.suivie_academique.mappers;


import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.entities.Salle;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SalleMappers {
    public SalleDTO toDTO(Salle salle);

    public Salle toSalle(SalleDTO salleDTO);
}
