package com.example.application.data.generator;

import com.example.application.data.entity.Patient;
import com.example.application.data.repository.PatientRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PatientRepository patientRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (patientRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Patient> patientGenerator = new ExampleDataGenerator<>(Patient.class,
                    LocalDateTime.now());
            patientGenerator.setData(Patient::setFirstName, DataType.FIRST_NAME);
            patientGenerator.setData(Patient::setLastName, DataType.LAST_NAME);
            patientGenerator.setData(Patient::setPatientIndex, DataType.NUMBER_UP_TO_1000);
            List<Patient> patients = patientRepository.saveAll(patientGenerator.create(5, seed));

            logger.info("... generating 50 Patient entities...");
            logger.info("... " + patients.get(1) + "...");
            logger.info("... " + patients.get(2) + "...");
            logger.info("... " + patients.get(3) + "...");
            logger.info("... " + patients.get(4) + "...");
            Random r = new Random(seed);


            logger.info("Generated demo data");
        };
    }

}
