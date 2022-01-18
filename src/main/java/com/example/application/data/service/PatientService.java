package com.example.application.data.service;

import com.example.application.data.entity.Patient;
import com.example.application.data.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAllPatients(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {

            return patientRepository.findAll();
        } else {
            return patientRepository.search(stringFilter);
        }
    }

    public long countPatients() {
        return patientRepository.count();
    }

    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }

    public void savePatient(Patient patient) {
        if (patient == null) {
            System.err.println("Patient is null. Are you sure you have connected your form to the application?");
            return;
        }
        patientRepository.save(patient);
    }

    public Optional<Patient> findPatient(Integer id) {
        return patientRepository.findById(id);
    }

}
