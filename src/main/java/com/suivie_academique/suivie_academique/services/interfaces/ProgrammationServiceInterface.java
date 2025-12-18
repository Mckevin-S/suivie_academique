package com.suivie_academique.suivie_academique.services.interfaces;

import com.suivie_academique.suivie_academique.dto.ProgrammationDTO;

import java.util.List;

public interface ProgrammationServiceInterface {

    ProgrammationDTO save(ProgrammationDTO programmationDTO);

    List<ProgrammationDTO> getAll();

    ProgrammationDTO getById(Integer codeProgrammation);

    ProgrammationDTO update(Integer codeProgrammation, ProgrammationDTO programmationDTO);

    void delete(Integer codeProgrammation);



//    List<ProgrammationDTO> findByRolePersonnel(RolePersonnel role);
//
//    List<ProgrammationDTO> search(String name);

}
