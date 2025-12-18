package com.suivie_academique.suivie_academique.controllers;

import com.suivie_academique.suivie_academique.dto.SalleDTO;
import com.suivie_academique.suivie_academique.services.implementations.SalleServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salle") // Lombok creates a constructor using the private field
public class SalleController {

    // Dependency injection via the constructor created by @AllArgsConstructor
    private SalleServiceImplementation salleServiceImplementation;

    public SalleController(SalleServiceImplementation salleServiceImplementation) {
        this.salleServiceImplementation = salleServiceImplementation;
    }

    // 1. Missing @PostMapping annotation
    @PostMapping // Handles POST requests to /salle
    public ResponseEntity<?> save(@RequestBody SalleDTO salleDTO){
        System.out.println(salleDTO.getStatusSalle().name());
        try {
            SalleDTO salleDTO1 = salleServiceImplementation.save(salleDTO);
            return new ResponseEntity<>(salleDTO1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping // Handles GET requests to /salle
    public ResponseEntity<List<SalleDTO>> getAll(){
        // This is the line that previously threw NullPointerException
        return new ResponseEntity<>(salleServiceImplementation.getAll(), HttpStatus.OK);
    }

    // 2. Missing @PutMapping annotation
    @PutMapping("/{codeSalle}") // Handles PUT requests to /salle/{codeSalle}
    public ResponseEntity<?> update(@PathVariable String codeSalle, @RequestBody SalleDTO salleDTO){
        try {
            return new ResponseEntity<>(salleServiceImplementation.update(codeSalle, salleDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeSalle}") // Handles DELETE requests to /salle/{codeSalle}
    public ResponseEntity<?>delete(@PathVariable String codeSalle) {
        try{
            salleServiceImplementation.delete(codeSalle);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Use 204 No Content for successful deletion
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{codeSalle}") // Handles GET requests to /salle/{codeSalle}
    public ResponseEntity<?> show(@PathVariable String codeSalle){
        try {
            return new ResponseEntity<>(salleServiceImplementation.getById(codeSalle), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Use 404 Not Found for resource not found
        }
    }
}