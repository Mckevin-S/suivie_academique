package com.suivie_academique.suivie_academique.entities;


import com.suivie_academique.suivie_academique.utils.StatutProgrammation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity()
@Table(name = "programmation")
public class Programmation {
    public Programmation() {

    }
    @Id
    @Basic(optional = false )
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codeProgrammation;

    @Basic(optional = false )
    private int nbreHeureProgrammation;

    @Basic(optional = false )
    private Date dateProgrammation;

    @Basic(optional = false )
    private Date debutProgrammation;

    @Basic(optional = false )
    private Date finProgrammation;

    @Basic(optional = false )
    private StatutProgrammation statutProgrammation;

    @ManyToOne
    @JoinColumn(name = "codeSalle",referencedColumnName = "codeSalle")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "codeCours",referencedColumnName = "codeCours")
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "codePersonnelProg",referencedColumnName = "codePersonnel")
    private Personnel personnelProg;

    @ManyToOne(optional = true)
    @JoinColumn(name = "codePersonnelVal",referencedColumnName = "codePersonnel")
    private Personnel personnelVal;

    public int getCodeProgrammation() {
        return codeProgrammation;
    }

    public void setCodeProgrammation(int codeProgrammation) {
        this.codeProgrammation = codeProgrammation;
    }

    public int getNbreHeureProgrammation() {
        return nbreHeureProgrammation;
    }

    public void setNbreHeureProgrammation(int nbreHeureProgrammation) {
        this.nbreHeureProgrammation = nbreHeureProgrammation;
    }

    public Date getDateProgrammation() {
        return dateProgrammation;
    }

    public void setDateProgrammation(Date dateProgrammation) {
        this.dateProgrammation = dateProgrammation;
    }

    public Date getDebutProgrammation() {
        return debutProgrammation;
    }

    public void setDebutProgrammation(Date debutProgrammation) {
        this.debutProgrammation = debutProgrammation;
    }

    public Date getFinProgrammation() {
        return finProgrammation;
    }

    public void setFinProgrammation(Date finProgrammation) {
        this.finProgrammation = finProgrammation;
    }

    public StatutProgrammation getStatutProgrammation() {
        return statutProgrammation;
    }

    public void setStatutProgrammation(StatutProgrammation statutProgrammation) {
        this.statutProgrammation = statutProgrammation;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Personnel getPersonnelProg() {
        return personnelProg;
    }

    public void setPersonnelProg(Personnel personnelProg) {
        this.personnelProg = personnelProg;
    }

    public Personnel getPersonnelVal() {
        return personnelVal;
    }

    public void setPersonnelVal(Personnel personnelVal) {
        this.personnelVal = personnelVal;
    }

    public Programmation(int codeProgrammation, int nbreHeureProgrammation, Date dateProgrammation, Date debutProgrammation, Date finProgrammation, StatutProgrammation statutProgrammation, Salle salle, Cours cours, Personnel personnelProg, Personnel personnelVal) {
        this.codeProgrammation = codeProgrammation;
        this.nbreHeureProgrammation = nbreHeureProgrammation;
        this.dateProgrammation = dateProgrammation;
        this.debutProgrammation = debutProgrammation;
        this.finProgrammation = finProgrammation;
        this.statutProgrammation = statutProgrammation;
        this.salle = salle;
        this.cours = cours;
        this.personnelProg = personnelProg;
        this.personnelVal = personnelVal;
    }


}
