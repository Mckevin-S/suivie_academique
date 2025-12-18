package com.suivie_academique.suivie_academique.services.interfaces;

import com.suivie_academique.suivie_academique.dto.CoursDTO;

import java.util.List;

public interface CourServiceInterface {

    CoursDTO save(CoursDTO coursDTO);

    List<CoursDTO> getAll();

    CoursDTO getById(String codeCours);

    CoursDTO update(String codeCours, CoursDTO coursDTO);

    void delete(String codeCours);


}
