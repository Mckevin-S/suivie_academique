package com.suivie_academique.suivie_academique.dto;


import com.suivie_academique.suivie_academique.utils.StatusSalle;


public class SalleDTO {
    private String codeSalle;

    private String DescSalle;

    private int contenance;

    private StatusSalle statusSalle;

    public String getCodeSalle() {
        return codeSalle;
    }

    public void setCodeSalle(String codeSalle) {
        this.codeSalle = codeSalle;
    }

    public String getDescSalle() {
        return DescSalle;
    }

    public void setDescSalle(String descSalle) {
        DescSalle = descSalle;
    }

    public int getContenance() {
        return contenance;
    }

    public void setContenance(int contenance) {
        this.contenance = contenance;
    }

    public StatusSalle getStatusSalle() {
        return statusSalle;
    }

    public void setStatusSalle(StatusSalle statusSalle) {
        this.statusSalle = statusSalle;
    }

    public SalleDTO(String codeSalle, String descSalle, int contenance, StatusSalle statusSalle) {
        this.codeSalle = codeSalle;
        DescSalle = descSalle;
        this.contenance = contenance;
        this.statusSalle = statusSalle;
    }

    public SalleDTO() {
    }
}

