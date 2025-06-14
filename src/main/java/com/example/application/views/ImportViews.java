package com.example.application.views;

import com.example.application.models.Student;
import com.example.application.services.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "importar", layout = MainLayout.class)
public class ImportViews extends VerticalLayout {

    private final StudentService studentService;
    private Grid<Student> grid = new Grid<>(Student.class);

    @Autowired
    public ImportViews(StudentService studentService) {
        this.studentService = studentService;

        TextField rutaInput = new TextField("Ruta del archivo JSON");
        rutaInput.setPlaceholder("ej: students.json");

        Button cargarBtn = new Button("Cargar Estudiantes", event -> {
            String ruta = rutaInput.getValue();
            List<Student> lista = this.studentService.importarDesdeJSON(ruta);
            if (lista != null && !lista.isEmpty()) {
                grid.setItems(lista);
                Notification.show("Estudiantes cargados: " + lista.size());
            } else {
                Notification.show("No se pudo leer o está vacío.");
            }
        });

        add(rutaInput, cargarBtn, grid);

    }    
}
