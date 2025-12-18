package com.suivie_academique.suivie_academique.services.interfaces;


import com.suivie_academique.suivie_academique.dto.SalleDTO;

import java.util.List;

public interface SalleServiceInterface {
    SalleDTO save(SalleDTO salleDTO);

    List<SalleDTO> getAll();

    SalleDTO getById(String codeSalle);

    SalleDTO update(String codeSalle, SalleDTO salleDTO);

    void delete(String codeSalle);

}
