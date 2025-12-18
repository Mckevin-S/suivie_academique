package com.suivie_academique.suivie_academique.mappers;


import com.suivie_academique.suivie_academique.dto.CoursDTO;
import com.suivie_academique.suivie_academique.entities.Cours;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CoursMappers {

    CoursDTO toDTO(Cours cours);

    Cours toEntity(CoursDTO CoursDTO);
}
