package com.suivie_academique.suivie_academique.entities;

import jakarta.persistence.*;

import com.suivie_academique.suivie_academique.utils.RolePersonnel;

import java.util.List;

@Entity
public class Personnel {
    @Id
    @Basic(optional = false )
    @Column(unique = true)
    private String codePersonnel;

    @Basic(optional = false )
    private String nomPersonnel;

    private String descCours;

    @Basic(optional = false )
    private String loginPersonnel;

    @Basic(optional = false )
    private String pwdPersonnel;

    @Basic(optional = false )
    private char sexePersonnel;

    @Basic(optional = false )
    private String phonePersonnel;

    @Basic(optional = false )
    @Enumerated(EnumType.STRING)
    private RolePersonnel rolePersonnel;

    @OneToMany(mappedBy = "personnelProg", cascade = CascadeType.ALL)
    private List<Programmation> programmation;

    @OneToMany(mappedBy = "personnelVal", cascade = CascadeType.ALL)
    private List<Programmation> validation;

    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL)
    private List<Affectation> affectations;

    public String getCodePersonnel() {
        return codePersonnel;
    }

    public void setCodePersonnel(String codePersonnel) {
        this.codePersonnel = codePersonnel;
    }

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public String getDescCours() {
        return descCours;
    }

    public void setDescCours(String descCours) {
        this.descCours = descCours;
    }

    public String getLoginPersonnel() {
        return loginPersonnel;
    }

    public void setLoginPersonnel(String loginPersonnel) {
        this.loginPersonnel = loginPersonnel;
    }

    public String getPwdPersonnel() {
        return pwdPersonnel;
    }

    public void setPwdPersonnel(String pwdPersonnel) {
        this.pwdPersonnel = pwdPersonnel;
    }

    public char getSexePersonnel() {
        return sexePersonnel;
    }

    public void setSexePersonnel(char sexePersonnel) {
        this.sexePersonnel = sexePersonnel;
    }

    public String getPhonePersonnel() {
        return phonePersonnel;
    }

    public void setPhonePersonnel(String phonePersonnel) {
        this.phonePersonnel = phonePersonnel;
    }

    public RolePersonnel getRolePersonnel() {
        return rolePersonnel;
    }

    public void setRolePersonnel(RolePersonnel rolePersonnel) {
        this.rolePersonnel = rolePersonnel;
    }

    public List<Programmation> getProgrammation() {
        return programmation;
    }

    public void setProgrammation(List<Programmation> programmation) {
        this.programmation = programmation;
    }

    public List<Programmation> getValidation() {
        return validation;
    }

    public void setValidation(List<Programmation> validation) {
        this.validation = validation;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public Personnel(String codePersonnel, String nomPersonnel, String descCours, String loginPersonnel, String pwdPersonnel, char sexePersonnel, String phonePersonnel, RolePersonnel rolePersonnel, List<Programmation> programmation, List<Programmation> validation, List<Affectation> affectations) {
        this.codePersonnel = codePersonnel;
        this.nomPersonnel = nomPersonnel;
        this.descCours = descCours;
        this.loginPersonnel = loginPersonnel;
        this.pwdPersonnel = pwdPersonnel;
        this.sexePersonnel = sexePersonnel;
        this.phonePersonnel = phonePersonnel;
        this.rolePersonnel = rolePersonnel;
        this.programmation = programmation;
        this.validation = validation;
        this.affectations = affectations;
    }

    public Personnel() {
    }
}
