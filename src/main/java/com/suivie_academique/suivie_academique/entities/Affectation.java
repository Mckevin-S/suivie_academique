package com.suivie_academique.suivie_academique.entities;


import jakarta.persistence.*;



@Entity
public class Affectation {


    public Affectation(Affectationid codeAffectation, Personnel personnel, Cours cours) {
        this.codeAffectation = codeAffectation;
        this.personnel = personnel;
        this.cours = cours;
    }

    public Affectation() {}


    @EmbeddedId
    private Affectationid codeAffectation;
    @MapsId("codePersonnel")

    @JoinColumn(name ="codePersonnel",referencedColumnName = "codePersonnel")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    private Personnel personnel;

    @MapsId("codeCours")
    @JoinColumn(name = "codeCours", referencedColumnName = "codeCours")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    private Cours cours;

    public Affectationid getCodeAffectation() {
        return codeAffectation;
    }

    public void setCodeAffectation(Affectationid codeAffectation) {
        this.codeAffectation = codeAffectation;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
}
