package com.suivie_academique.suivie_academique.repositories;

import com.suivie_academique.suivie_academique.entities.Affectation;
import com.suivie_academique.suivie_academique.entities.Affectationid;
import com.suivie_academique.suivie_academique.entities.Cours;
import com.suivie_academique.suivie_academique.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface AffectationRepos extends JpaRepository<Affectation, Affectationid> {
    // Trouver toutes les affectations d'un personnel
    List<Affectation> findByPersonnel(Personnel codePersonnel);

    // Trouver toutes les affectations d'un cours
    List<Affectation> findByCours(Cours codeCours);

//    // Vérifier si une affectation existe
//    boolean existsByPersonnel_CodePersonnelAndCours_CodeCours(String codePersonnel, Integer codeCours);
//
//    // Compter le nombre d'affectations par personnel
//    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.personnel.codePersonnel = :codePersonnel")
//    long countAffectationsByPersonnel(@Param("codePersonnel") String codePersonnel);
//
//    // Compter le nombre d'enseignants affectés à un cours
//    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.cours.codeCours = :codeCours")
//    long countEnseignantsByCours(@Param("codeCours") String codeCours);
//
//    // Trouver les cours affectés à un personnel avec informations détaillées
//    @Query("SELECT a FROM Affectation a JOIN FETCH a.cours WHERE a.personnel.codePersonnel = :codePersonnel")
//    List<Affectation> findAffectationsWithCoursDetails(@Param("codePersonnel") String codePersonnel);
}
