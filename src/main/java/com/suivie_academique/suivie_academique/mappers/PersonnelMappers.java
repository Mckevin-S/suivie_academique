package com.suivie_academique.suivie_academique.mappers;


import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
import com.suivie_academique.suivie_academique.entities.Personnel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PersonnelMappers {

    PersonnelDTO toDTO(Personnel personnel);

    Personnel toEntity(PersonnelDTO PersonnelDTO);

}
