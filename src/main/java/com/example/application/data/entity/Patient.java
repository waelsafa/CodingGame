package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Entity
public class Patient extends AbstractEntity {

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @Digits(integer = 6, fraction = 0)
    private Integer patientIndex = 0;

    private String problem = "N/A";


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getPatientIndex() {
        return patientIndex;
    }

    public void setPatientIndex(Integer patientIndex) {
        if(patientIndex % 5 == 0 ){
            setProblem("Traumatologie");
        } else if(patientIndex % 3 == 0){
            setProblem("Cardiologie");
        }
        if(patientIndex % 5 == 0  && patientIndex % 3 == 0){
            setProblem("Traumatologie, Cardiologie");
        }
        this.patientIndex = patientIndex;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblem() {
        return this.problem;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
