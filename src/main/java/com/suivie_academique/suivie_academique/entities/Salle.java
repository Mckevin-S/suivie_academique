package com.suivie_academique.suivie_academique.entities;

import java.util.List;

import com.suivie_academique.suivie_academique.utils.StatusSalle;
import jakarta.persistence.*;

@Entity()
@Table(name = "salle")
//@NamedQueries({
//    @NamedQuery(name = "Salle.findByStatus", query = "SELECT s FROM Salle s WHERE s.statuSalle = :status"),
//    @NamedQuery(name = "Salle.findAvailable", query = "SELECT s FROM Salle s WHERE s.statuSalle = 'LIBRE'"),
//    @NamedQuery(name = "Salle.countByStatus", query = "SELECT COUNT(s) FROM Salle s WHERE s.statuSalle = :status")
//})

public class Salle {

    @Id
    @Basic(optional = false )
    private String codeSalle;

    private String descSalle;

    @Basic(optional = false )
    private int contenance;

    @Basic(optional = false )
    @Enumerated(EnumType.STRING)
    private StatusSalle statusSalle;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL)
    private List<Programmation> programmation;

    public String getCodeSalle() {
        return codeSalle;
    }

    public void setCodeSalle(String codeSalle) {
        this.codeSalle = codeSalle;
    }

    public String getDescSalle() {
        return descSalle;
    }

    public void setDescSalle(String descSalle) {
        this.descSalle = descSalle;
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

    public List<Programmation> getProgrammation() {
        return programmation;
    }

    public void setProgrammation(List<Programmation> programmation) {
        this.programmation = programmation;
    }

    public Salle() {
    }

    public Salle(String codeSalle, String descSalle, int contenance, StatusSalle statusSalle, List<Programmation> programmation) {
        this.codeSalle = codeSalle;
        this.descSalle = descSalle;
        this.contenance = contenance;
        this.statusSalle = statusSalle;
        this.programmation = programmation;
    }
}
