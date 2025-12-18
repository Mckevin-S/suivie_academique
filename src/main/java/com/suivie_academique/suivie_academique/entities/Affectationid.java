package com.suivie_academique.suivie_academique.entities;


import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Affectationid implements Serializable {
    public Affectationid() {
    }
    @Basic(optional = false )
    private String codeCours;

    @Basic(optional = false )
    private String codePersonnel;

    public Affectationid(String codeCours, String codePersonnel) {
        this.codeCours = codeCours;
        this.codePersonnel = codePersonnel;
    }


}
