package com.web;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Game phase entity. It represents game phase/action.
 * Contains only id and the phase/action description.
 * @author Theofanis Tsiantas
 * @version  2022.01.03 - version 1
 */
@Entity
public class GamePhase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Description is mandatory")
    private String phaseDescription;

    public GamePhase() {
    }

    public GamePhase(String phaseDescription) {
        this.phaseDescription = phaseDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhaseDescription() {
        return phaseDescription;
    }

    public void setPhaseDescription(String phaseDescription) {
        this.phaseDescription = phaseDescription;
    }

    @Override
    public String toString() {
        return "GamePhase{" +
                "id=" + id +
                ", phaseDescription='" + phaseDescription + '\'' +
                '}';
    }
}


