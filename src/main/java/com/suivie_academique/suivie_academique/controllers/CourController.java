package com.suivie_academique.suivie_academique.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suivie_academique.suivie_academique.dto.CoursDTO;
import com.suivie_academique.suivie_academique.services.implementations.CourServiceImplementation;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cour")
public class CourController {

    private CourServiceImplementation courServiceImplementation;

    public CourController(CourServiceImplementation courServiceImplementation) {
        this.courServiceImplementation = courServiceImplementation;
    }

    // 1. Missing @PostMapping annotation
    @PostMapping // Handles POST requests to /salle
    public ResponseEntity<?> save(@RequestBody CoursDTO coursDTO){
        try {
            CoursDTO coursDTO1 = courServiceImplementation.save(coursDTO);
            return new ResponseEntity<>(coursDTO1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping // Handles GET requests to /salle
    public ResponseEntity<List<CoursDTO>> getAll(){
        // This is the line that previously threw NullPointerException
        return new ResponseEntity<>(courServiceImplementation.getAll(), HttpStatus.OK);
    }

    // 2. Missing @PutMapping annotation
    @PutMapping("/{codeCour}") // Handles PUT requests to /salle/{codeCour}
    public ResponseEntity<?> update(@PathVariable String codeCour, @RequestBody CoursDTO coursDTO){
        try {
            return new ResponseEntity<>(courServiceImplementation.update(codeCour, coursDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeCour}") // Handles DELETE requests to /salle/{codeCour}
    public ResponseEntity<?>delete(@PathVariable String codeCour) {
        try{
            courServiceImplementation.delete(codeCour);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Use 204 No Content for successful deletion
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{codeCour}") // Handles GET requests to /salle/{codeCour}
    public ResponseEntity<?> show(@PathVariable String codeCour){
        try {
            return new ResponseEntity<>(courServiceImplementation.getById(codeCour), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); // Use 404 Not Found for resource not found
        }
    }

}
