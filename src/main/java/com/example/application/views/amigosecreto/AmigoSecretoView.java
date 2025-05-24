package com.example.application.views.amigosecreto;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import java.util.*;

@PageTitle("Amigo Secreto")
@Route("Amigo_secreto")
public class AmigoSecretoView extends VerticalLayout {

    VerticalLayout container1;
    VerticalLayout container2;
    HorizontalLayout centeredLayout1;
    VerticalLayout centeredLayout2;
    HorizontalLayout buttonLayout;
    VerticalLayout gridLayout;
    VerticalLayout individualGrid;
    Button generateButton;
    Button cleanButton;
    H2 title;

    List<String> participants;
    Map<String, String> assignments;

    public AmigoSecretoView() {
        setAlignItems(Alignment.CENTER);  
        setJustifyContentMode(JustifyContentMode.CENTER);

        container1 = new VerticalLayout();
        container1.setWidthFull();
        container1.setHeightFull();
        container1.getStyle().set("background-color", "#f0f0f0");
        container1.setPadding(true);
        container1.setAlignItems(Alignment.CENTER);
        container1.setJustifyContentMode(JustifyContentMode.CENTER);

        container2 = new VerticalLayout();
        container2.getStyle().set("background-color", "white");
        container2.setPadding(true);
        container2.setSpacing(true);
        container2.setWidth("80%");
        container2.setAlignItems(Alignment.CENTER);
        container2.getStyle().set("border-radius", "8px").set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        centeredLayout1 = new HorizontalLayout();
        centeredLayout1.setSizeFull();
        centeredLayout1.setAlignItems(Alignment.CENTER);
        centeredLayout1.setJustifyContentMode(JustifyContentMode.CENTER);

        centeredLayout2 = new VerticalLayout();
        centeredLayout2.setSizeFull();
        centeredLayout2.setAlignItems(Alignment.CENTER);
        centeredLayout2.setJustifyContentMode(JustifyContentMode.CENTER);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        gridLayout = new VerticalLayout();
        gridLayout.getStyle().set("background-color", "white");
        gridLayout.setAlignItems(Alignment.CENTER);
        gridLayout.setPadding(true);
        gridLayout.setSpacing(true);
        gridLayout.setWidth("80%");
        gridLayout.getStyle().set("border-radius", "8px").set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        gridLayout.setVisible(false);

        TextArea textArea = new TextArea("Ingresa los participantes");
        textArea.setPlaceholder("Cada participante debe estar en el mismo cuadro");
        textArea.setWidth("50%");

        generateButton = new Button("Generar amigo secreto");
        generateButton.addClickListener(e -> {
            String input = textArea.getValue();
            participants = Arrays.asList(input.split("\\R"));
            
            if (participants.size() >= 2) {
                gridLayout.removeAll();
                gridLayout.setVisible(true);
                generateAssignments(participants);
            }
        });

        cleanButton = new Button("Limpiar");
        cleanButton.addClickListener(e -> {
            textArea.clear();
            gridLayout.setVisible(false);
        });

        centeredLayout1.add(textArea);
        buttonLayout.add(generateButton, cleanButton);
        centeredLayout2.add(buttonLayout);
        container2.add(centeredLayout1, centeredLayout2);
        container1.add(container2, gridLayout);
        add(container1);
    }

    public static class Assignment {
        private String participant;
        private String secretFriend;

        public Assignment(String participant, String secretFriend) {
            this.participant = participant;
            this.secretFriend = secretFriend;
        }

        public String getParticipant() {
            return participant;
        }

        public String getSecretFriend() {
            return secretFriend;
        }
    }

    void generateAssignments(List<String> participants) {
        List<String> shuffled = new ArrayList<>(participants);
        Collections.shuffle(shuffled);
        
        // Asegurar que nadie se tenga a sí mismo
        boolean valid;
        int attempts = 0;
        final int maxAttempts = 100;
        
        do {
            valid = true;
            Collections.shuffle(shuffled);
            
            for (int i = 0; i < participants.size(); i++) {
                if (participants.get(i).equals(shuffled.get(i))) {
                    valid = false;
                    break;
                }
            }
            
            attempts++;
        } while (!valid && attempts < maxAttempts);
        
        if (!valid) {
            title = new H2("No se pudo generar una asignación válida");
            gridLayout.add(title);
            return;
        }
        
        assignments = new LinkedHashMap<>();
        for (int i = 0; i < participants.size(); i++) {
            assignments.put(participants.get(i), shuffled.get(i));
        }
        
        title = new H2("Asignaciones de Amigo Secreto");
        Grid<Assignment> grid = new Grid<>(Assignment.class);
        grid.setColumns("participant", "secretFriend");
        grid.getColumns().forEach(column -> column.setSortable(false));
        
        List<Assignment> assignmentList = new ArrayList<>();
        for (Map.Entry<String, String> entry : assignments.entrySet()) {
            assignmentList.add(new Assignment(entry.getKey(), entry.getValue()));
        }
        
        grid.setItems(assignmentList);
        
        individualGrid = new VerticalLayout();
        individualGrid.setPadding(true);
        individualGrid.setSpacing(true);
        individualGrid.setWidth("80%");
        individualGrid.setAlignItems(Alignment.CENTER);
        individualGrid.getStyle().set("border-radius", "8px").set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        
        individualGrid.add(title, grid);
        gridLayout.add(individualGrid);
    }
}