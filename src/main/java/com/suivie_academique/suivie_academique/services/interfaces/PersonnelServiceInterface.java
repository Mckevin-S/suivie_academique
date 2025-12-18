package com.suivie_academique.suivie_academique.services.interfaces;

import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
import com.suivie_academique.suivie_academique.utils.RolePersonnel;

import java.util.List;

public interface PersonnelServiceInterface {

    PersonnelDTO save(PersonnelDTO personnelDTO);

    List<PersonnelDTO> getAll();

    PersonnelDTO getById(String personnelDTO);

    PersonnelDTO update(String codePersonnel, PersonnelDTO personnelDTO);

    void delete(String codePersonnel);

    List<PersonnelDTO> findByRolePersonnel(RolePersonnel role);

    List<PersonnelDTO> search(String name);

    void changePassword(String codePersonnel, String oldPassword, String newPassword);

    public void resetPassword(String codePersonnel, String newPassword);


}
