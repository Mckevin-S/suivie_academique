package com.suivie_academique.suivie_academique.mappers;


import com.suivie_academique.suivie_academique.dto.ProgrammationDTO;
import com.suivie_academique.suivie_academique.entities.Programmation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProgrammationMappers {

    ProgrammationDTO toDTO(Programmation programmation);

    Programmation toEntity(ProgrammationDTO programmationDTO);
}
