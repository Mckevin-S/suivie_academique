package com.suivie_academique.suivie_academique.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.security.JwtUtils; // Import des dépendances de sécurité
import com.suivie_academique.suivie_academique.services.implementations.PersonnelDetailsService;
import com.suivie_academique.suivie_academique.services.implementations.SalleServiceImplementation;
import com.suivie_academique.suivie_academique.utils.StatusSalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import; // Importe la configuration de sécurité complète
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Utilisation de @Import pour charger la configuration de sécurité (y compris le filtre JWT)
// Note: Assurez-vous que SecurityConfig est accessible et non final/static/privé.
@WebMvcTest(SalleController.class)
@Import(com.suivie_academique.suivie_academique.security.SecurityConfig.class)
class SalleTestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Services et utilitaires à mocker
    @MockBean
    private SalleServiceImplementation salleServiceImplementation;

    @MockBean
    private JwtUtils jwtUtils; // Mock du générateur/validateur de token

    @MockBean
    private PersonnelDetailsService personnelDetailsService; // Mock du chargeur d'utilisateur

    private static final String VALID_TOKEN = "valid-jwt-token";
    private static final String INVALID_TOKEN = "invalid-jwt-token";
    private SalleDTO salleDTO;
    private User authorizedUser;

    @BeforeEach
    void setUp() {
        // --- 1. Initialisation des données de test ---
        salleDTO = new SalleDTO();
        salleDTO.setCodeSalle("S001");
        salleDTO.setDescSalle("Salle Informatique");
        salleDTO.setContenance(30);
        salleDTO.setStatusSalle(StatusSalle.LIBRE);

        // --- 2. Configuration de l'utilisateur de sécurité ---
        // Utilisateur simulant un rôle ayant les permissions
        authorizedUser = new User(
                "authorized_user",
                "password", // mot de passe factice
                List.of(new SimpleGrantedAuthority("RESPONSSALE_ACCADEMIQUE")) // ROLE_ADMIN pour les opérations CUD
        );

        // --- 3. Configuration du comportement du JWT Mock ---
        // Pour un token valide : le filtre doit valider, extraire le nom d'utilisateur, et charger les détails de l'utilisateur
        when(jwtUtils.validateToken(VALID_TOKEN)).thenReturn(true);
        when(jwtUtils.getUsernameFromToken(VALID_TOKEN)).thenReturn(authorizedUser.getUsername());
        when(personnelDetailsService.loadUserByUsername(authorizedUser.getUsername()))
                .thenReturn(authorizedUser);

        // Pour un token invalide ou manquant, le filtre doit échouer (testé ci-dessous)
        when(jwtUtils.validateToken(INVALID_TOKEN)).thenReturn(false);
    }

    // Méthode utilitaire pour construire l'appel avec le token
    private String getAuthHeader(String token) {
        return "Bearer " + token;
    }


    // =========================================================
    // TESTS D'OPÉRATIONS CRUD (Avec authentification valide)
    // =========================================================

    // ---------- POST /salle (save) ----------
    @Test
    void saveSalle_success_201() throws Exception {
        when(salleServiceImplementation.save(any(SalleDTO.class))).thenReturn(salleDTO);

        mockMvc.perform(post("/salle")
                        .header("Authorization", getAuthHeader(VALID_TOKEN)) // Utilisation du TOKEN valide
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salleDTO)))
                .andExpect(status().isCreated());
    }

    // ---------- GET /salle (getAll) ----------
    @Test
    void getAllSalles_success_200() throws Exception {
        when(salleServiceImplementation.getAll()).thenReturn(List.of(salleDTO));

        mockMvc.perform(get("/salle")
                        .header("Authorization", getAuthHeader(VALID_TOKEN))) // Utilisation du TOKEN valide
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codeSalle").value("S001"));
    }

    // ---------- DELETE /salle/{codeSalle} (delete) ----------
    @Test
    void deleteSalle_success_204() throws Exception {
        doNothing().when(salleServiceImplementation).delete("S001");

        mockMvc.perform(delete("/salle/{codeSalle}", "S001")
                        .header("Authorization", getAuthHeader(VALID_TOKEN))) // Utilisation du TOKEN valide
                .andExpect(status().isNoContent());
    }

    // =========================================================
    // TESTS DE SÉCURITÉ (Authentification échouée ou manquante)
    // =========================================================

    @Test
    void access_withoutToken_shouldReturnUnauthorized_401() throws Exception {
        // Tentative d'accès sans l'en-tête Authorization
        mockMvc.perform(get("/salle"))
                .andExpect(status().isUnauthorized()); // HTTP 401

        verifyNoInteractions(salleServiceImplementation); // Le service ne doit jamais être appelé
    }

    @Test
    void access_withInvalidToken_shouldReturnUnauthorized_401() throws Exception {
        // Tentative d'accès avec un token invalide (mocké pour retourner false)
        mockMvc.perform(get("/salle")
                        .header("Authorization", getAuthHeader(INVALID_TOKEN)))
                .andExpect(status().isUnauthorized()); // HTTP 401

        verifyNoInteractions(salleServiceImplementation); // Le service ne doit jamais être appelé
    }
}