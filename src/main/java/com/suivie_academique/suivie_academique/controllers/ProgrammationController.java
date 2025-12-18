package com.suivie_academique.suivie_academique.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suivie_academique.suivie_academique.dto.ProgrammationDTO;
import com.suivie_academique.suivie_academique.services.implementations.ProgrammationServiceImplementation;

import lombok.AllArgsConstructor;

@RequestMapping("/programmation")
@RestController
public class ProgrammationController {

    private ProgrammationServiceImplementation programmationServiceImplementation;

    public ProgrammationController(ProgrammationServiceImplementation programmationServiceImplementation) {
        this.programmationServiceImplementation = programmationServiceImplementation;
    }

    @PostMapping // ⬅️ CORRECTION : Annotation POST requise
    public ResponseEntity<?> save(@RequestBody ProgrammationDTO programmationDTO){
        try {
            ProgrammationDTO programmationDTO1 = programmationServiceImplementation.save(programmationDTO);
            return new ResponseEntity<>(programmationDTO1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

         @GetMapping // Handles GET requests to /salle
    public ResponseEntity<List<ProgrammationDTO>> getAll(){
        // This is the line that previously threw NullPointerException
        return new ResponseEntity<>(programmationServiceImplementation.getAll(), HttpStatus.OK);
    }

    // 2. Missing @PutMapping annotation
    @PutMapping("/{codeProgrammation}") // Handles PUT requests to /salle/{codeProgrammation}
    public ResponseEntity<?> update(@PathVariable Integer codeProgrammation, @RequestBody ProgrammationDTO programmationDTO){
        try {
            return new ResponseEntity<>(programmationServiceImplementation.update(codeProgrammation, programmationDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeProgrammation}") // Handles DELETE requests to /salle/{codeProgrammation}
    public ResponseEntity<?>delete(@PathVariable Integer codeProgrammation) {
        try{
            programmationServiceImplementation.delete(codeProgrammation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Use 204 No Content for successful deletion
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{codeProgrammation}") // Handles GET requests to /salle/{codeProgrammation}
    public ResponseEntity<?> show(@PathVariable Integer codeProgrammation){
        try {
            return new ResponseEntity<>(programmationServiceImplementation.getById(codeProgrammation), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Use 404 Not Found for resource not found
        }
    }
}

