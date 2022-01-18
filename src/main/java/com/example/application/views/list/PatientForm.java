package com.example.application.views.list;

import com.example.application.data.entity.Patient;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class PatientForm extends FormLayout {
  TextField firstName = new TextField("First name"); 
  TextField lastName = new TextField("Last name");
  IntegerField patientIndex = new IntegerField("Patient Index");
  Binder<Patient> binder = new BeanValidationBinder<>(Patient.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");
  private Patient patient;

  public PatientForm() {
    addClassName("patient-form");

    binder.bindInstanceFields(this);

    add(firstName, 
        lastName,
        patientIndex, createButtonsLayout());
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
    binder.readBean(patient);
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, patient)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
    close.addClickShortcut(Key.ESCAPE);

    return new HorizontalLayout(save, delete, close); 
  }

  private void validateAndSave() {
    try {
      binder.writeBean(patient);
      fireEvent(new SaveEvent(this, patient));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class PatientFormEvent extends ComponentEvent<PatientForm> {
    private Patient patient;

    protected PatientFormEvent(PatientForm source, Patient patient) {
      super(source, false);
      this.patient = patient;
    }

    public Patient getPatient() {
      return patient;
    }
  }

  public static class SaveEvent extends PatientFormEvent {
    SaveEvent(PatientForm source, Patient patient) {
      super(source, patient);
    }
  }

  public static class DeleteEvent extends PatientFormEvent {
    DeleteEvent(PatientForm source, Patient patient) {
      super(source, patient);
    }

  }

  public static class CloseEvent extends PatientFormEvent {
    CloseEvent(PatientForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}