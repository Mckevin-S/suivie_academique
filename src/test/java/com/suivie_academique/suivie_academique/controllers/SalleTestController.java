package com.suivie_academique.suivie_academique.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.services.implementations.SalleServiceImplementation;
import com.suivie_academique.suivie_academique.utils.StatusSalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SalleTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalleServiceImplementation salleService; // On simule le service

    @Autowired
    private ObjectMapper objectMapper;

    private SalleDTO testSalle;

    @BeforeEach
    void setUp() {
        testSalle = new SalleDTO();
        testSalle.setCodeSalle("S001");
        testSalle.setContenance(30);
        testSalle.setStatusSalle(StatusSalle.LIBRE);
        testSalle.setDescSalle("Salle Info");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Résout l'erreur 403
    void saveSalle_success_201() throws Exception {
        Mockito.when(salleService.save(Mockito.any(SalleDTO.class))).thenReturn(testSalle);

        mockMvc.perform(post("/salle")
                        .with(csrf()) // Important : Résout le Forbidden sur POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSalle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codeSalle").value("S001"));
    }

    @Test
    @WithMockUser(username = "user")
    void getAllSalles_success_200() throws Exception {
        Mockito.when(salleService.getAll()).thenReturn(Collections.singletonList(testSalle));

        mockMvc.perform(get("/salle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codeSalle").value("S001"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteSalle_success_204() throws Exception {
        Mockito.doNothing().when(salleService).delete("S001");

        mockMvc.perform(delete("/salle/S001")
                        .with(csrf())) // Important pour DELETE
                .andExpect(status().isNoContent());
    }

    @Test
    void access_withoutToken_shouldReturnError() throws Exception {
        mockMvc.perform(get("/salle"))
                .andExpect(status().is(anyOf(is(401), is(403))));
    }
}