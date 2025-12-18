package com.suivie_academique.suivie_academique.controllers;

import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
import com.suivie_academique.suivie_academique.services.AuditService;
import com.suivie_academique.suivie_academique.services.implementations.PersonnelServiceImplementation;
import com.suivie_academique.suivie_academique.utils.RolePersonnel;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnel")
@CrossOrigin
public class PersonnelController {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);

    private final PersonnelServiceImplementation personnelService;
    private final AuditService auditService;

    public PersonnelController(PersonnelServiceImplementation personnelService,
                               AuditService auditService) {
        this.personnelService = personnelService;
        this.auditService = auditService;
    }

    /**
     * Récupérer tous les personnels.
     */
    @GetMapping
    public ResponseEntity<List<PersonnelDTO>> getAll() {
        logger.info("Requête GET /api/personnel - Récupération de tous les personnels");

        try {
            List<PersonnelDTO> personnels = personnelService.getAll();
            auditService.logRead("PERSONNEL", "ALL");

            logger.info("Récupération réussie : {} personnels trouvés", personnels.size());
            return ResponseEntity.ok(personnels);

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des personnels", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer un personnel par son code.
     */
    @GetMapping("/{codePersonnel}")
    public ResponseEntity<?> getById(@PathVariable String codePersonnel) {
        logger.info("Requête GET /api/personnel/{} - Récupération d'un personnel", codePersonnel);

        try {
            PersonnelDTO personnel = personnelService.getById(codePersonnel);
            auditService.logRead("PERSONNEL", codePersonnel);

            logger.info("Personnel {} récupéré avec succès", codePersonnel);
            return ResponseEntity.ok(personnel);

        } catch (RuntimeException e) {
            logger.warn("Personnel {} non trouvé", codePersonnel);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Personnel non trouvé : " + codePersonnel);
        }
    }

    /**
     * Créer un nouveau personnel.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSSALE_ACCADEMIQUE')")
    public ResponseEntity<?> create(@RequestBody PersonnelDTO personnelDTO,
                                    HttpServletRequest request) {
        logger.info("Requête POST /api/personnel - Création d'un nouveau personnel");
        logger.debug("Données reçues : nom={}, login={}, role={}",
                personnelDTO.getNomPersonnel(),
                personnelDTO.getLoginPersonnel(),
                personnelDTO.getRolePersonnel());

        try {
            PersonnelDTO created = personnelService.save(personnelDTO);

            auditService.logCreate("PERSONNEL", created.getCodePersonnel(),
                    "Nom: " + created.getNomPersonnel() + ", Role: " + created.getRolePersonnel());

            logger.info("Personnel créé avec succès : code={}, nom={}",
                    created.getCodePersonnel(), created.getNomPersonnel());

            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (RuntimeException e) {
            logger.error("Erreur lors de la création du personnel", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Mettre à jour un personnel.
     */
    @PutMapping("/{codePersonnel}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESPONSSALE_ACCADEMIQUE')")
    public ResponseEntity<?> update(@PathVariable String codePersonnel,
                                    @RequestBody PersonnelDTO personnelDTO) {
        logger.info("Requête PUT /api/personnel/{} - Mise à jour d'un personnel", codePersonnel);
        logger.debug("Nouvelles données : nom={}, phone={}",
                personnelDTO.getNomPersonnel(),
                personnelDTO.getPhonePersonnel());

        try {
            PersonnelDTO updated = personnelService.update(codePersonnel, personnelDTO);

            auditService.logUpdate("PERSONNEL", codePersonnel,
                    "Nom: " + updated.getNomPersonnel() + ", Phone: " + updated.getPhonePersonnel());

            logger.info("Personnel {} mis à jour avec succès", codePersonnel);
            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            logger.error("Erreur lors de la mise à jour du personnel {}", codePersonnel, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Supprimer un personnel.
     */
    @DeleteMapping("/{codePersonnel}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String codePersonnel) {
        logger.info("Requête DELETE /api/personnel/{} - Suppression d'un personnel", codePersonnel);

        try {
            personnelService.delete(codePersonnel);

            auditService.logDelete("PERSONNEL", codePersonnel);

            logger.info("Personnel {} supprimé avec succès", codePersonnel);
            return ResponseEntity.ok("Personnel supprimé avec succès");

        } catch (RuntimeException e) {
            logger.error("Erreur lors de la suppression du personnel {}", codePersonnel, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Rechercher des personnels par rôle.
     */
    @GetMapping("/role/{rolePersonnel}")
    public ResponseEntity<List<PersonnelDTO>> findByRole(@PathVariable RolePersonnel rolePersonnel) {
        logger.info("Requête GET /api/personnel/role/{} - Recherche par rôle", rolePersonnel);

        try {
            List<PersonnelDTO> personnels = personnelService.findByRolePersonnel(rolePersonnel);
            auditService.logRead("PERSONNEL", "ROLE:" + rolePersonnel);

            logger.info("{} personnels trouvés pour le rôle {}", personnels.size(), rolePersonnel);
            return ResponseEntity.ok(personnels);

        } catch (Exception e) {
            logger.error("Erreur lors de la recherche par rôle {}", rolePersonnel, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Rechercher des personnels par nom.
     */
    @GetMapping("/search")
    public ResponseEntity<List<PersonnelDTO>> search(@RequestParam String name) {
        logger.info("Requête GET /api/personnel/search?name={} - Recherche par nom", name);

        try {
            List<PersonnelDTO> personnels = personnelService.search(name);
            auditService.logRead("PERSONNEL", "SEARCH:" + name);

            logger.info("{} personnels trouvés pour la recherche '{}'", personnels.size(), name);
            return ResponseEntity.ok(personnels);

        } catch (Exception e) {
            logger.error("Erreur lors de la recherche de personnels avec '{}'", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}



//package com.suivie_academique.suivie_academique.controllers;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
//import com.suivie_academique.suivie_academique.services.implementations.PersonnelServiceImplementation;
//
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/personnel")
//public class PersonnelController {
//
//    private PersonnelServiceImplementation personnelServiceImplementation;
//
//    public PersonnelController(PersonnelServiceImplementation personnelServiceImplementation) {
//        this.personnelServiceImplementation = personnelServiceImplementation;
//    }
//
//    @PostMapping // Handles POST requests to /salle
//    public ResponseEntity<?> save(@RequestBody PersonnelDTO personnelDTO){
//        try {
//            PersonnelDTO personnelDTO1 = personnelServiceImplementation.save(personnelDTO);
//            return new ResponseEntity<>(personnelDTO1, HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping // Handles GET requests to /salle
//    public ResponseEntity<List<PersonnelDTO>> getAll(){
//        // This is the line that previously threw NullPointerException
//        return new ResponseEntity<>(personnelServiceImplementation.getAll(), HttpStatus.OK);
//    }
//
//    // 2. Missing @PutMapping annotation
//    @PutMapping("/{codePersonnel}") // Handles PUT requests to /salle/{codePersonnel}
//    public ResponseEntity<?> update(@PathVariable String codePersonnel, @RequestBody PersonnelDTO personnelDTO){
//        try {
//            return new ResponseEntity<>(personnelServiceImplementation.update(codePersonnel, personnelDTO), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/{codePersonnel}") // Handles DELETE requests to /salle/{codePersonnel}
//    public ResponseEntity<?>delete(@PathVariable String codePersonnel) {
//        try{
//            personnelServiceImplementation.delete(codePersonnel);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Use 204 No Content for successful deletion
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/{codePersonnel}") // Handles GET requests to /salle/{codePersonnel}
//    public ResponseEntity<?> show(@PathVariable String codePersonnel){
//        try {
//            return new ResponseEntity<>(personnelServiceImplementation.getById(codePersonnel), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Use 404 Not Found for resource not found
//        }
//    }
//
//}
