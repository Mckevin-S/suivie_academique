package com.suivie_academique.suivie_academique.repositories;

import com.suivie_academique.suivie_academique.entities.Cours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepos extends JpaRepository<Cours, String> {

   List<Cours>findCoursByCodeCours(String codeCours);

   List<Cours>findCoursByLabelCours(String labelCour);

}
