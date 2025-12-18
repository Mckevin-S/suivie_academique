package com.suivie_academique.suivie_academique.repositories;

import com.suivie_academique.suivie_academique.entities.Salle;
import com.suivie_academique.suivie_academique.utils.StatusSalle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SalleRepos extends JpaRepository<Salle, String> {

    boolean existsSalleByContenance(int contenance);

    List<Salle> findAllByContenanceGreaterThanEqual(int contenance);

    List<Salle> findByCodeSalleContaining(String codeSalle);

    List<Salle> findByStatusSalle(StatusSalle status);

    @Query("SELECT s FROM Salle s WHERE s.statusSalle = 'LIBRE' AND s.contenance >= :minContenance")
    List<Salle> findAvailableSalles(@Param("minContenance") int minContenance);

    @Query("SELECT COUNT(s) FROM Salle s WHERE s.statusSalle = :status")
    long countByStatus(@Param("status") StatusSalle status);
}
