package com.suivie_academique.suivie_academique.repositories;

import com.suivie_academique.suivie_academique.entities.Personnel;
import com.suivie_academique.suivie_academique.utils.RolePersonnel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonnelRepos extends JpaRepository<Personnel, String> {

    @Query("select p from Personnel p WHERE p.nomPersonnel LIKE '%:token%'")
    List<Personnel> searchByName(@Param("token") String token);

    @Query(value = "SELECT count(*) from personnel where  sexePersonnel=:sexe", nativeQuery = true)
    int countBySexe(@Param("sexe") char sexe);

    List<Personnel> findByRolePersonnel(RolePersonnel rolePersonnel);

    @Query("SELECT p FROM Personnel p WHERE p.sexePersonnel = :sexe")
    List<Personnel> findBySexe(@Param("sexe") char sexe);

    @Query("SELECT COUNT(p) FROM Personnel p WHERE p.rolePersonnel = :role")
    long countByRolePersonnel(@Param("rolePersonnel") RolePersonnel rolePersonnel);

    @Query(value = "SELECT ROLE FROM Personnel WHERE Rolepersonnel=:rolePersonnel", nativeQuery = true)
    List<Personnel> searchByRolePersonnel(@Param("rolePersonnel") RolePersonnel rolePersonnel);

    Personnel findByLoginPersonnel(String loginPersonnel);

}
