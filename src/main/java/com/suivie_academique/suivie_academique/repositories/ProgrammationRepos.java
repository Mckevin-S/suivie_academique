package com.suivie_academique.suivie_academique.repositories;

import com.suivie_academique.suivie_academique.entities.Programmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammationRepos extends JpaRepository<Programmation, Integer> {

}
