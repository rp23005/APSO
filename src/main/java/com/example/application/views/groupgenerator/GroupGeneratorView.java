package com.example.application.views.groupgenerator;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import java.util.*;

@PageTitle("Generador de grupos")
@Route("Generador_de_grupos")
public class GroupGeneratorView extends VerticalLayout {

    VerticalLayout container1;
    VerticalLayout container2;
    HorizontalLayout centeredLayout1;
    VerticalLayout centeredLayout2;
    HorizontalLayout buttonLayout;
    HorizontalLayout customizationLayout;
    VerticalLayout gritLayout;
    VerticalLayout individualGrit;
    IntegerField numberOfParticipants;
    IntegerField numberOfGroups;
    Button generateGroupsButton;
    Button cleanTextAreaButton;
    H2 gNumber;

    List<String> participants;

    public GroupGeneratorView() {

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

        customizationLayout = new HorizontalLayout();
        customizationLayout.setSizeFull();
        customizationLayout.setAlignItems(Alignment.CENTER);
        customizationLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        gritLayout = new VerticalLayout();
        gritLayout.getStyle().set("background-color", "white");
        gritLayout.setAlignItems(Alignment.CENTER);
        gritLayout.setPadding(true);
        gritLayout.setSpacing(true);
        gritLayout.setWidth("80%");
        gritLayout.getStyle().set("border-radius", "8px").set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        gritLayout.setVisible(false);

        TextArea textArea = new TextArea("Ingresa los participantes");
        textArea.setPlaceholder("Cada participante debe de estar en una nueva linea");
        textArea.setWidth("50%");

        numberOfParticipants = new IntegerField("Participantes por equipo");
        numberOfGroups = new IntegerField("Cantidad de grupos");

        generateGroupsButton = new Button("Generar equipos");

        generateGroupsButton.addClickListener(e -> {
            String input = textArea.getValue();
            participants = new ArrayList<>();
            participants = Arrays.asList(input.split("\\R"));
            if (numberOfGroups.getValue() > 0 && numberOfParticipants.getValue() > 0 &&
                    numberOfGroups.getValue() != null && numberOfParticipants.getValue() != 0 && participants.size() != 0) {
                gritLayout.removeAll();
                gritLayout.setVisible(true);
                generateGroups(participants, numberOfGroups.getValue(), numberOfParticipants.getValue());
            }

        });

        cleanTextAreaButton = new Button("Limpiar");

        cleanTextAreaButton.addClickListener(e -> {
            textArea.clear();
        });

        centeredLayout1.add(textArea);
        customizationLayout.add(numberOfParticipants, numberOfGroups);
        buttonLayout.add(generateGroupsButton, cleanTextAreaButton);
        centeredLayout2.add(customizationLayout, buttonLayout);
        container2.add(centeredLayout1, centeredLayout2);
        container1.add(container2, gritLayout);
        add(container1);

    }

    public static class Participant {
        private int numero;
        private String nombre;

        public Participant() {
        }

        public Participant(int numero, String nombre) {
            this.numero = numero;
            this.nombre = nombre;
        }

        public int getNumero() {
            return numero;
        }

        public String getNombre() {
            return nombre;
        }
    }

    void generateGroups(List<String> participants, int nGroups, int nParticipants) {
        Collections.shuffle(participants, new Random());

        int totalParticipants = participants.size();
        int maxPossibleGroups = totalParticipants / nParticipants;
        int remainder = totalParticipants % nParticipants;

        int actualGroups = Math.min(nGroups, maxPossibleGroups + (remainder > 0 ? 1 : 0));

        int currentIndex = 0;
        for (int i = 0; i < actualGroups; i++) {
            int groupSize = Math.min(nParticipants, totalParticipants - currentIndex);
            int nextIndex = currentIndex + groupSize;

            gNumber = new H2("Grupo numero " + (i + 1));
            Grid<Participant> grid = new Grid<>(Participant.class);
            grid.setColumns("numero", "nombre");
            grid.getColumns().forEach(column -> column.setSortable(false));

            List<String> newGroup = participants.subList(currentIndex, nextIndex);
            List<Participant> participantList = new ArrayList<>();
            grid.setAllRowsVisible(true);
            for (int j = 0; j < newGroup.size(); j++) {
                participantList.add(new Participant((j + 1), newGroup.get(j)));
            }

            grid.setItems(participantList);

            individualGrit = new VerticalLayout();
            individualGrit.setPadding(true);
            individualGrit.setSpacing(true);
            individualGrit.setWidth("80%");
            individualGrit.setAlignItems(Alignment.CENTER);
            individualGrit.getStyle().set("border-radius", "8px").set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

            currentIndex = nextIndex;

            individualGrit.add(gNumber, grid);
            gritLayout.add(individualGrit);

        }

    }

}

