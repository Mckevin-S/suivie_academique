package com.suivie_academique.suivie_academique.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suivie_academique.suivie_academique.dto.AffectationDTO;
import com.suivie_academique.suivie_academique.entities.Affectationid;
import com.suivie_academique.suivie_academique.services.implementations.AffectationServiceImplementation;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/affectation")
public class AffectationController {

    private AffectationServiceImplementation affectationServiceImplementation;

    public AffectationController(AffectationServiceImplementation affectationServiceImplementation) {
        this.affectationServiceImplementation = affectationServiceImplementation;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AffectationDTO affectationDTO){
         try {
            AffectationDTO affectationDTO1 = affectationServiceImplementation.save(affectationDTO);
            return new ResponseEntity<>(affectationDTO1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

         @GetMapping // Handles GET requests to /salle
    public ResponseEntity<List<AffectationDTO>> getAll(){
        // This is the line that previously threw NullPointerException
        return new ResponseEntity<>(affectationServiceImplementation.getAll(), HttpStatus.OK);
    }

    // 2. Missing @PutMapping annotation
    @PutMapping("/{codeAffectation}") // Handles PUT requests to /salle/{codeAffectation}
    public ResponseEntity<?> update(@PathVariable Affectationid codeAffectation, @RequestBody AffectationDTO AffectationDTO){
        try {
            return new ResponseEntity<>(affectationServiceImplementation.update(codeAffectation, AffectationDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeAffectation}") // Handles DELETE requests to /salle/{codeAffectation}
    public ResponseEntity<?>delete(@PathVariable Affectationid codeAffectation) {
        try{
            affectationServiceImplementation.delete(codeAffectation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Use 204 No Content for successful deletion
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{codeAffectation}") // Handles GET requests to /salle/{codeAffectation}
    public ResponseEntity<?> show(@PathVariable Affectationid codeAffectation){
        try {
            return new ResponseEntity<>(affectationServiceImplementation.getById(codeAffectation), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Use 404 Not Found for resource not found
        }
    }


}
