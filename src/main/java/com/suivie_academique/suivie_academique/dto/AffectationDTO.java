package com.suivie_academique.suivie_academique.dto;


import com.suivie_academique.suivie_academique.entities.Affectationid;



public class AffectationDTO {

    private Affectationid codeAffectation;

    private PersonnelDTO personnel;

    private CoursDTO cours;

    public Affectationid getCodeAffectation() {
        return codeAffectation;
    }

    public void setCodeAffectation(Affectationid codeAffectation) {
        this.codeAffectation = codeAffectation;
    }

    public PersonnelDTO getPersonnel() {
        return personnel;
    }

    public void setPersonnel(PersonnelDTO personnel) {
        this.personnel = personnel;
    }

    public CoursDTO getCours() {
        return cours;
    }

    public void setCours(CoursDTO cours) {
        this.cours = cours;
    }

    public AffectationDTO(Affectationid codeAffectation, PersonnelDTO personnel, CoursDTO cours) {
        this.codeAffectation = codeAffectation;
        this.personnel = personnel;
        this.cours = cours;
    }

    public AffectationDTO() {
    }
}
