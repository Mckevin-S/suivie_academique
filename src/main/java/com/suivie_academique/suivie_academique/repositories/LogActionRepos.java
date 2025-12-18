package com.suivie_academique.suivie_academique.repositories;


import com.suivie_academique.suivie_academique.entities.LogAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogActionRepos extends JpaRepository<LogAction, Long> {
}

