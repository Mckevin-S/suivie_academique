package com.suivie_academique.suivie_academique.dto;

import org.hibernate.validator.constraints.Length;


public class CoursDTO {

    @Length(min = 5)
    private String codeCours;

    private String labelCours;

    private String descCours;

    private int nbCreditCours;

    private int nbHeureCours;

    public CoursDTO() {
        
    }

    public CoursDTO(String codeCours, String labelCours, String descCours, int nbCreditCours, int nbHeureCours) {
        this.codeCours = codeCours;
        this.labelCours = labelCours;
        this.descCours = descCours;
        this.nbCreditCours = nbCreditCours;
        this.nbHeureCours = nbHeureCours;
    }

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
}
