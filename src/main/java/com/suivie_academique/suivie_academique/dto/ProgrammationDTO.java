package com.suivie_academique.suivie_academique.dto;

import com.suivie_academique.suivie_academique.utils.StatutProgrammation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;




public class ProgrammationDTO {

    private int codeProgrammation;

    private int nbreHeureProgrammation;

    private Date dateProgrammation;

    private Date debutProgrammation;

    private Date finProgrammation;

    private StatutProgrammation statutProgrammation;

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

    public ProgrammationDTO(int codeProgrammation, int nbreHeureProgrammation, Date dateProgrammation, Date debutProgrammation, Date finProgrammation, StatutProgrammation statutProgrammation) {
        this.codeProgrammation = codeProgrammation;
        this.nbreHeureProgrammation = nbreHeureProgrammation;
        this.dateProgrammation = dateProgrammation;
        this.debutProgrammation = debutProgrammation;
        this.finProgrammation = finProgrammation;
        this.statutProgrammation = statutProgrammation;
    }

    public ProgrammationDTO() {
    }
}
