package com.suivie_academique.suivie_academique.services.interfaces;

import com.suivie_academique.suivie_academique.dto.AffectationDTO;
import com.suivie_academique.suivie_academique.entities.Affectationid;


import java.util.List;

public interface AffectationServiceInterface {

    AffectationDTO save(AffectationDTO affectationDTO);

    List<AffectationDTO> getAll();

    AffectationDTO getById(Affectationid codeAffectation);

    AffectationDTO update(Affectationid codeAffectation, AffectationDTO affectationDTO);

    void delete(Affectationid codeAffectation);
}
