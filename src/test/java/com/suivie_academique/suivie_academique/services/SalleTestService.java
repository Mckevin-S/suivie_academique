package com.suivie_academique.suivie_academique.services;


import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.entities.Salle;
import com.suivie_academique.suivie_academique.mappers.SalleMappers;
import com.suivie_academique.suivie_academique.repositories.SalleRepos;
import com.suivie_academique.suivie_academique.services.implementations.SalleServiceImplementation;
import com.suivie_academique.suivie_academique.utils.StatusSalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test pour SalleServiceImplementation.
 * Utilise JUnit 5 et Mockito.
 */
@ExtendWith(MockitoExtension.class)
public class SalleTestService {

    @Mock
    private SalleRepos salleRepos;

    @Mock
    private SalleMappers salleMappers;

    @InjectMocks
    private SalleServiceImplementation salleService;

    private Salle salle;
    private SalleDTO salleDTO;

    @BeforeEach
    void setUp() {
        salle = new Salle();
        salle.setCodeSalle("S001");
        salle.setDescSalle("Salle Informatique");
        salle.setContenance(30);
        salle.setStatusSalle(StatusSalle.LIBRE);

        salleDTO = new SalleDTO();
        salleDTO.setCodeSalle("S001");
        salleDTO.setDescSalle("Salle Informatique");
        salleDTO.setContenance(30);
        salleDTO.setStatusSalle(StatusSalle.LIBRE);
    }

    // ================= SAVE =================
    @Test
    void saveSalle_success() {
        when(salleMappers.toSalle(salleDTO)).thenReturn(salle);
        when(salleRepos.save(salle)).thenReturn(salle);
        when(salleMappers.toDTO(salle)).thenReturn(salleDTO);

        SalleDTO result = salleService.save(salleDTO);

        assertNotNull(result);
        assertEquals("S001", result.getCodeSalle());
        verify(salleRepos, times(1)).save(salle);
    }

    @Test
    void saveSalle_invalidData() {
        salleDTO.setContenance(5);

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> salleService.save(salleDTO));

        assertEquals("Donne de salle invalide", exception.getMessage());
    }

    // ================= GET ALL =================
    @Test
    void getAllSalles() {
        when(salleRepos.findAll()).thenReturn(List.of(salle));
        when(salleMappers.toDTO(salle)).thenReturn(salleDTO);

        List<SalleDTO> result = salleService.getAll();

        assertEquals(1, result.size());
        verify(salleRepos).findAll();
    }

    // ================= GET BY ID =================
    @Test
    void getSalleById_success() {
        when(salleRepos.findById("S001")).thenReturn(Optional.of(salle));
        when(salleMappers.toDTO(salle)).thenReturn(salleDTO);

        SalleDTO result = salleService.getById("S001");

        assertNotNull(result);
        assertEquals("S001", result.getCodeSalle());
    }

    @Test
    void getSalleById_notFound() {
        when(salleRepos.findById("S001")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> salleService.getById("S001"));
    }

    // ================= UPDATE =================
    @Test
    void updateSalle_success() {
        when(salleRepos.findById("S001")).thenReturn(Optional.of(salle));
        when(salleRepos.save(any(Salle.class))).thenReturn(salle);
        when(salleMappers.toDTO(salle)).thenReturn(salleDTO);

        SalleDTO result = salleService.update("S001", salleDTO);

        assertNotNull(result);
        verify(salleRepos).save(salle);
    }

    // ================= DELETE =================
    @Test
    void deleteSalle_success() {
        when(salleRepos.existsById("S001")).thenReturn(true);

        salleService.delete("S001");

        verify(salleRepos).deleteById("S001");
    }

    @Test
    void deleteSalle_notFound() {
        when(salleRepos.existsById("S001")).thenReturn(false);

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> salleService.delete("S001"));

        assertEquals("Salle non trouvée", exception.getMessage());
    }

}
