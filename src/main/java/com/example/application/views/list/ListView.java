package com.example.application.views.list;

import com.example.application.data.entity.Patient;
import com.example.application.data.service.PatientService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

@Route(value = "")
@Theme(themeFolder = "flowcrmtutorial")
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout {
    Grid<Patient> grid = new Grid<>(Patient.class);
    TextField filterText = new TextField();
    PatientService service;
    PatientForm form;

    public ListView(PatientService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }


    private void configureForm() {
        form = new PatientForm();
        form.setWidth("25em");
        form.addListener(PatientForm.SaveEvent.class, this::savePatient);
        form.addListener(PatientForm.DeleteEvent.class, this::deleteContact);
        form.addListener(PatientForm.CloseEvent.class, e -> closeEditor());
    }
    private void savePatient(PatientForm.SaveEvent event) {
        service.savePatient(event.getPatient());
        updateList();
        closeEditor();
    }

    private void deleteContact(PatientForm.DeleteEvent event) {
        service.deletePatient(event.getPatient());
        updateList();
        closeEditor();
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "patientIndex", "problem");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editPatient(event.getValue()));
    }

    public void editPatient(Patient patient) {
        if (patient == null) {
            closeEditor();
        } else {
            form.setPatient(patient);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addPatient() {
        grid.asSingleSelect().clear();
        editPatient(new Patient());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addPatientBtn = new Button("Add contact");
        addPatientBtn.addClickListener(click -> addPatient());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPatientBtn);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.findAllPatients(""));
    }
}