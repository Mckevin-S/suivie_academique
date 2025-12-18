package com.suivie_academique.suivie_academique.entities;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acteur;       // loginPersonnel
    private String action;       // CREATE / UPDATE / DELETE / LOGIN etc.
    private String description;
    private LocalDateTime date = LocalDateTime.now();

    public LogAction() {}

    public LogAction(String acteur, String action, String description) {
        this.acteur = acteur;
        this.action = action;
        this.description = description;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActeur() {
        return acteur;
    }

    public void setActeur(String acteur) {
        this.acteur = acteur;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
