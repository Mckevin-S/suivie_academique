package com.suivie_academique.suivie_academique.controllers;

import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
import com.suivie_academique.suivie_academique.dto.LoginResponse;
import com.suivie_academique.suivie_academique.entities.Personnel;
import com.suivie_academique.suivie_academique.repositories.PersonnelRepos;
import com.suivie_academique.suivie_academique.security.JwtUtils;
import com.suivie_academique.suivie_academique.services.AuditService;
import com.suivie_academique.suivie_academique.services.implementations.PersonnelDetailsService;
import com.suivie_academique.suivie_academique.utils.RolePersonnel;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authManager;
    private final PersonnelDetailsService personnelDetailsService;
    private final PersonnelRepos personnelRepos;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final AuditService auditService;

    public AuthController(AuthenticationManager authManager,
                          PersonnelDetailsService personnelDetailsService,
                          PersonnelRepos personnelRepos,
                          JwtUtils jwtUtils,
                          PasswordEncoder encoder,
                          AuditService auditService) {
        this.authManager = authManager;
        this.personnelDetailsService = personnelDetailsService;
        this.personnelRepos = personnelRepos;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.auditService = auditService;
    }

    /**
     * Récupère l'adresse IP du client.
     */
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Endpoint de connexion avec journalisation complète.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PersonnelDTO dto, HttpServletRequest request) {

        String ipAddress = getClientIP(request);
        logger.info("Tentative de connexion : login={}, IP={}", dto.getLoginPersonnel(), ipAddress);

        // Validation
        if (dto.getLoginPersonnel() == null || dto.getLoginPersonnel().trim().isEmpty()) {
            logger.warn("Tentative de connexion avec login vide depuis IP={}", ipAddress);
            auditService.logLoginFailure("UNKNOWN", ipAddress, "Login vide");
            return ResponseEntity.badRequest().body("Le login est requis");
        }

        if (dto.getPwdPersonnel() == null || dto.getPwdPersonnel().trim().isEmpty()) {
            logger.warn("Tentative de connexion avec mot de passe vide : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            auditService.logLoginFailure(dto.getLoginPersonnel(), ipAddress, "Mot de passe vide");
            return ResponseEntity.badRequest().body("Le mot de passe est requis");
        }

        try {
            // Authentification
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getLoginPersonnel(),
                            dto.getPwdPersonnel()
                    )
            );

            // Génération du token
            var userDetails = personnelDetailsService.loadUserByUsername(dto.getLoginPersonnel());
            String token = jwtUtils.generateToken(userDetails);

            // Logs de succès
            logger.info("Connexion réussie : login={}, IP={}", dto.getLoginPersonnel(), ipAddress);
            auditService.logLoginSuccess(dto.getLoginPersonnel(), ipAddress);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (BadCredentialsException e) {
            logger.warn("Échec de connexion (identifiants incorrects) : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            auditService.logLoginFailure(dto.getLoginPersonnel(), ipAddress, "Identifiants incorrects");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Identifiants incorrects");

        } catch (DisabledException e) {
            logger.warn("Échec de connexion (compte désactivé) : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            auditService.logLoginFailure(dto.getLoginPersonnel(), ipAddress, "Compte désactivé");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Compte désactivé");

        } catch (LockedException e) {
            logger.warn("Échec de connexion (compte verrouillé) : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            auditService.logLoginFailure(dto.getLoginPersonnel(), ipAddress, "Compte verrouillé");
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body("Compte verrouillé");

        } catch (Exception e) {
            logger.error("Erreur inattendue lors de l'authentification : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress, e);
            auditService.logSecurityError(dto.getLoginPersonnel(), "AUTHENTICATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'authentification");
        }
    }

    /**
     * Endpoint d'inscription avec journalisation.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PersonnelDTO dto, HttpServletRequest request) {

        String ipAddress = getClientIP(request);
        logger.info("Tentative d'inscription : login={}, IP={}", dto.getLoginPersonnel(), ipAddress);

        // Validations
        if (dto.getLoginPersonnel() == null || dto.getLoginPersonnel().trim().isEmpty()) {
            logger.warn("Tentative d'inscription avec login vide depuis IP={}", ipAddress);
            return ResponseEntity.badRequest().body("Le login est requis");
        }

        if (dto.getPwdPersonnel() == null || dto.getPwdPersonnel().isEmpty()) {
            logger.warn("Tentative d'inscription avec mot de passe vide : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            return ResponseEntity.badRequest().body("Le mot de passe est requis");
        }

        if (dto.getNomPersonnel() == null || dto.getNomPersonnel().trim().isEmpty()) {
            logger.warn("Tentative d'inscription avec nom vide : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            return ResponseEntity.badRequest().body("Le nom est requis");
        }

        // Vérifier si le login existe déjà
        if (personnelRepos.findByLoginPersonnel(dto.getLoginPersonnel()) != null) {
            logger.warn("Tentative d'inscription avec login existant : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ce login existe déjà");
        }

        // Validation du mot de passe
        if (dto.getPwdPersonnel().length() < 6) {
            logger.warn("Tentative d'inscription avec mot de passe trop court : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress);
            return ResponseEntity.badRequest()
                    .body("Le mot de passe doit contenir au moins 6 caractères");
        }

        try {
            // Création du personnel
            Personnel personnel = new Personnel();
            personnel.setCodePersonnel(dto.getCodePersonnel() != null ?
                    dto.getCodePersonnel() : dto.getLoginPersonnel());
            personnel.setNomPersonnel(dto.getNomPersonnel());
            personnel.setLoginPersonnel(dto.getLoginPersonnel());
            personnel.setPwdPersonnel(encoder.encode(dto.getPwdPersonnel()));

            personnel.setPhonePersonnel(dto.getPhonePersonnel() != null ?
                    dto.getPhonePersonnel() : "0000000000");
            personnel.setRolePersonnel(dto.getRolePersonnel() != null ?
                    dto.getRolePersonnel() : RolePersonnel.RESPONSSALE_ACCADEMIQUE);

            personnelRepos.save(personnel);

            logger.info("Inscription réussie : login={}, code={}, IP={}",
                    dto.getLoginPersonnel(), personnel.getCodePersonnel(), ipAddress);

            auditService.logCreate("PERSONNEL", personnel.getCodePersonnel(),
                    "Inscription depuis IP: " + ipAddress + ", Nom: " + dto.getNomPersonnel());

            // Conversion pour la réponse (sans mot de passe)
            PersonnelDTO responseDto = new PersonnelDTO();
            responseDto.setCodePersonnel(personnel.getCodePersonnel());
            responseDto.setNomPersonnel(personnel.getNomPersonnel());
            responseDto.setLoginPersonnel(personnel.getLoginPersonnel());
            responseDto.setPhonePersonnel(personnel.getPhonePersonnel());
            responseDto.setRolePersonnel(personnel.getRolePersonnel());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription : login={}, IP={}",
                    dto.getLoginPersonnel(), ipAddress, e);
            auditService.logSecurityError(dto.getLoginPersonnel(), "REGISTRATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création du compte");
        }
    }
}

//import com.suivie_academique.suivie_academique.dto.LoginRequest;
//import com.suivie_academique.suivie_academique.dto.LoginResponse;
//import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
//import com.suivie_academique.suivie_academique.entities.Personnel;
//import com.suivie_academique.suivie_academique.repositories.PersonnelRepos;
//import com.suivie_academique.suivie_academique.security.JwtUtils;
//import com.suivie_academique.suivie_academique.services.implementations.PersonnelDetailsService;
//import com.suivie_academique.suivie_academique.utils.RolePersonnel;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin
//public class AuthController {
//
//    private final AuthenticationManager authManager;
//    private final PersonnelDetailsService personnelDetailsService;
//    private final PersonnelRepos personnelRepos;
//    private final JwtUtils jwtUtils;
//    private final PasswordEncoder encoder;
//
//    // Constructeur pour injection de dépendances
//    public AuthController(AuthenticationManager authManager,
//                          PersonnelDetailsService personnelDetailsService,
//                          PersonnelRepos personnelRepos,
//                          JwtUtils jwtUtils,
//                          PasswordEncoder encoder) {
//        this.authManager = authManager;
//        this.personnelDetailsService = personnelDetailsService;
//        this.personnelRepos = personnelRepos;
//        this.jwtUtils = jwtUtils;
//        this.encoder = encoder;
//    }
//
//    /**
//     * Endpoint de connexion.
//     * @return JWT token si authentification réussie
//     */
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody PersonnelDTO request) {
//        try {
//            // Authentification via Spring Security
//            authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getLoginPersonnel(),
//                            request.getPwdPersonnel()
//                    )
//            );
//
//            // Génération du token JWT
//            var userDetails = personnelDetailsService.loadUserByUsername(request.getLoginPersonnel());
//            String token = jwtUtils.generateToken(userDetails);
//
//            return ResponseEntity.ok(new LoginResponse(token));
//
//        } catch (BadCredentialsException e) {
//            // ✅ CORRIGÉ : Concaténation correcte pour le message d'erreur
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Identifiants incorrects pour l'utilisateur : " + request.getLoginPersonnel());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erreur lors de l'authentification");
//        }
//    }
//
//    /**
//     * Endpoint d'inscription (à décommenter si besoin).
//     */
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
//
//        // ✅ Vérification si le login existe déjà
//        if (personnelRepos.findByLoginPersonnel(request.getUsername()) != null) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body("Ce nom d'utilisateur existe déjà");
//        }
//
//        // ✅ Validation basique du mot de passe (ajouter plus de règles si besoin)
//        if (request.getPassword() == null || request.getPassword().length() < 6) {
//            return ResponseEntity.badRequest()
//                    .body("Le mot de passe doit contenir au moins 6 caractères");
//        }
//
//        // Création du nouveau personnel
//        Personnel p = new Personnel();
//        p.setCodePersonnel(request.getUsername()); // ⚠️ Considérez un UUID auto-généré
//        p.setLoginPersonnel(request.getUsername());
//        p.setPwdPersonnel(encoder.encode(request.getPassword()));
//        p.setNomPersonnel("Nouvel utilisateur");
//        p.setPhonePersonnel("0000");
//        p.setSexePersonnel('M');
//        p.setRolePersonnel(RolePersonnel.RESPONSSALE_ACCADEMIQUE); // ⚠️ Typo : RESPONSABLE_ACADEMIQUE
//
//        personnelRepos.save(p);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("Compte créé avec succès");
//    }
//}
