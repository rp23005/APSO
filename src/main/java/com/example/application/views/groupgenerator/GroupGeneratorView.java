package com.example.application.views.groupgenerator;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.application.utils.PDFExporter;
import com.example.application.utils.Participant;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

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
    Button printResultsButton;
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
            printResultsButton.setVisible(true);
            String input = textArea.getValue();
            participants = new ArrayList<>();
            participants = Arrays.asList(input.split("\\R"));
            if (numberOfGroups.getValue() > 0 && numberOfParticipants.getValue() > 0 &&
                    numberOfGroups.getValue() != null && numberOfParticipants.getValue() != 0
                    && participants.size() != 0) {
                gritLayout.removeAll();
                gritLayout.setVisible(true);
                generateGroups(participants, numberOfGroups.getValue(), numberOfParticipants.getValue());
            }

        });

        cleanTextAreaButton = new Button("Limpiar");

        cleanTextAreaButton.addClickListener(e -> {
            textArea.clear();
        });

        printResultsButton = new Button("Imprimir resultados");
        printResultsButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        printResultsButton.setVisible(false);
        printResultsButton.addClickListener(e -> {
            List<List<Participant>> allGroups = new ArrayList<>();
            gritLayout.getChildren()
                    // Solo permite componentes que sean vertical Layouts
                    .filter(component -> component instanceof VerticalLayout)
                    // Convertimos cada componente(grit) en objetos para poder usar sus metodos
                    .map(component -> (VerticalLayout) component)
                    .forEach(individualGrit -> {
                        Grid<Participant> grid = findGridInLayout(individualGrit);
                        if (grid != null) {
                            List<Participant> participants = grid.getGenericDataView()
                                    .getItems()
                                    .collect(Collectors.toList());
                            allGroups.add(participants);
                        }
                    });

            Anchor downloadLink = createPdfDownloadAnchor(allGroups);
            add(downloadLink);
            downloadLink.getElement().executeJs("this.click();");
        });

        centeredLayout1.add(textArea);
        customizationLayout.add(numberOfParticipants, numberOfGroups);
        buttonLayout.add(generateGroupsButton, cleanTextAreaButton, printResultsButton);
        centeredLayout2.add(customizationLayout, buttonLayout);
        container2.add(centeredLayout1, centeredLayout2);
        container1.add(container2, gritLayout);
        add(container1);

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

    private Grid<Participant> findGridInLayout(VerticalLayout layout) {
        for (Component component : layout.getChildren().toList()) {
            if (component instanceof Grid) {
                return (Grid<Participant>) component;
            }
        }
        return null;
    }

    private Anchor createPdfDownloadAnchor(List<List<Participant>> allGroups) {
        StreamResource resource = new StreamResource("groups.pdf", () -> {
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            PDFExporter.exportToPdf(allGroups, pdfStream);
            return new ByteArrayInputStream(pdfStream.toByteArray());
        });

        Anchor downloadLink = new Anchor(resource, "");
        downloadLink.getElement().setAttribute("download", true);
        return downloadLink;
    }

}
