package com.suivie_academique.suivie_academique.mappers;


import com.suivie_academique.suivie_academique.dto.AffectationDTO;
import com.suivie_academique.suivie_academique.entities.Affectation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {CoursMappers.class,PersonnelMappers.class /* autres mappers */})
public interface AffectationMappers {

    AffectationDTO toDTO(Affectation affectation);
    Affectation toEntity(AffectationDTO affectationDTO);
}
