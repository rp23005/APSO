package com.example.application.views.dice;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.html.Span;

@PageTitle("Juego de Dados")
@Route("Dice")
public class DiceView extends VerticalLayout {

    ComboBox<Integer> comboBoxFaces;
    ComboBox<Integer> comboBoxDices;
    HorizontalLayout centeredLayout1;
    HorizontalLayout centeredLayout2;
    HorizontalLayout centeredLayout3;
    HorizontalLayout centeredLayout4;
    Button throwDice;
    Random random;
    Image svgImage;
    Span resultDiceRollContainer;

    public DiceView() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        centeredLayout1 = new HorizontalLayout();
        centeredLayout1.setSizeFull();
        centeredLayout1.setAlignItems(Alignment.CENTER);
        centeredLayout1.setJustifyContentMode(JustifyContentMode.CENTER);

        centeredLayout2 = new HorizontalLayout();
        centeredLayout2.setSizeFull();
        centeredLayout2.setAlignItems(Alignment.CENTER);
        centeredLayout2.setJustifyContentMode(JustifyContentMode.CENTER);

        centeredLayout3 = new HorizontalLayout();
        centeredLayout3.setSizeFull();
        centeredLayout3.setAlignItems(Alignment.CENTER);
        centeredLayout3.setJustifyContentMode(JustifyContentMode.CENTER);

        centeredLayout4 = new HorizontalLayout();
        centeredLayout4.setSizeFull();
        centeredLayout4.setAlignItems(Alignment.CENTER);
        centeredLayout4.setJustifyContentMode(JustifyContentMode.CENTER);

        comboBoxFaces = new ComboBox<>("Selecciona un numero");
        comboBoxDices = new ComboBox<>("Selecciona un numero");

        comboBoxFaces.setLabel("Numero de caras");
        comboBoxDices.setLabel("Numero de dados");

        setComboBoxSampleData(comboBoxFaces);
        setComboBoxSampleData(comboBoxDices);

        throwDice = new Button("Tirar Dado");

        throwDice.addClickListener(event -> {

            centeredLayout3.removeAll();
            centeredLayout4.removeAll();

            if (comboBoxFaces.getValue() != null && comboBoxDices.getValue() != null) {
                int resultDiceRollTotal = 0;
                for (int i = 0; i < comboBoxDices.getValue(); i++) {
                    random = new Random();
                    int resultDiceRoll = random.nextInt(comboBoxFaces.getValue()) + 1;
                    StreamResource resource = new StreamResource(
                            "dice.svg",
                            () -> getClass().getResourceAsStream("/Images/dice-" + resultDiceRoll + ".svg"));

                    svgImage = new Image(resource, "SVG Dice");
                    svgImage.getStyle()
                            .set("max-width", "200px")
                            .set("width", "100%")
                            .set("height", "auto")
                            .set("transition", "transform 0.5s ease-in-out")
                            .set("transform", "rotate(720deg) scale(1.2)");

                    svgImage.getElement().executeJs(
                            "this.style.transition = 'transform 0.5s ease-in-out'; this.style.transform = 'rotate(720deg) scale(1.2)';");

                    svgImage.getElement()
                            .executeJs("setTimeout(() => this.style.transform = 'rotate(0deg) scale(1)', 500);");
                    resultDiceRollTotal = resultDiceRollTotal + resultDiceRoll;

                    centeredLayout3.add(svgImage);
                }
                resultDiceRollContainer = new Span("El resultado es: " + String.valueOf(resultDiceRollTotal));

                resultDiceRollContainer.getStyle().set("font-size", "20px").set("font-weight", "bold");

                centeredLayout4.add(resultDiceRollContainer);
            }
        });

        centeredLayout1.add(comboBoxDices, comboBoxFaces);
        centeredLayout2.add(throwDice);

        add(centeredLayout1, centeredLayout2, centeredLayout3, centeredLayout4);

    }

    // Añadiendo numeros al comboBox
    private void setComboBoxSampleData(ComboBox<Integer> comboBox) {
        List<Integer> items = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            items.add(i);
        }
        comboBox.setItems(items);
    }
}
