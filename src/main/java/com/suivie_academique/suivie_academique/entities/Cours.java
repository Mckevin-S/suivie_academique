package com.suivie_academique.suivie_academique.entities;

import com.suivie_academique.suivie_academique.utils.StatusSalle;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
public class Cours {
    @Id
    @Basic(optional = false )
    @Column(unique = true)
    @Length(min = 5)
    private String codeCours;

    @Basic(optional = false )
    private String labelCours;

    private String descCours;

    @Basic(optional = false )
    private int nbCreditCours;

    @Basic(optional = false )
    private int nbHeureCours;



    @Basic(optional = false )
    @Enumerated(EnumType.STRING)
    private StatusSalle statuSalle;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Programmation> programmation;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Programmation> affectations;

    public String getCodeCours() {
        return codeCours;
    }

    public void setCodeCours(String codeCours) {
        this.codeCours = codeCours;
    }

    public String getLabelCours() {
        return labelCours;
    }

    public void setLabelCours(String labelCours) {
        this.labelCours = labelCours;
    }

    public String getDescCours() {
        return descCours;
    }

    public void setDescCours(String descCours) {
        this.descCours = descCours;
    }

    public int getNbCreditCours() {
        return nbCreditCours;
    }

    public void setNbCreditCours(int nbCreditCours) {
        this.nbCreditCours = nbCreditCours;
    }

    public int getNbHeureCours() {
        return nbHeureCours;
    }

    public void setNbHeureCours(int nbHeureCours) {
        this.nbHeureCours = nbHeureCours;
    }

    public StatusSalle getStatuSalle() {
        return statuSalle;
    }

    public void setStatuSalle(StatusSalle statuSalle) {
        this.statuSalle = statuSalle;
    }

    public List<Programmation> getProgrammation() {
        return programmation;
    }

    public void setProgrammation(List<Programmation> programmation) {
        this.programmation = programmation;
    }

    public List<Programmation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Programmation> affectations) {
        this.affectations = affectations;
    }

    public Cours(String codeCours, String labelCours, String descCours, int nbCreditCours, int nbHeureCours, StatusSalle statuSalle, List<Programmation> programmation, List<Programmation> affectations) {
        this.codeCours = codeCours;
        this.labelCours = labelCours;
        this.descCours = descCours;
        this.nbCreditCours = nbCreditCours;
        this.nbHeureCours = nbHeureCours;
        this.statuSalle = statuSalle;
        this.programmation = programmation;
        this.affectations = affectations;
    }

    public Cours() {
    }
}

