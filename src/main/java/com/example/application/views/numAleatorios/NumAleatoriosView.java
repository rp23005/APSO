package com.example.application.views.numAleatorios;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.*;

@PageTitle("Números aleatorios")
@Route("numeros-aleatorios")
@Menu(order = 1, icon = LineAwesomeIconUrl.RANDOM_SOLID)
public class NumAleatoriosView extends Composite<VerticalLayout> {

    public NumAleatoriosView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        TextField textField = new TextField("Nombre del sorteo");
        NumberField minField = new NumberField("Número mínimo");
        NumberField maxField = new NumberField("Número máximo");
        NumberField cantidadField = new NumberField("Números a generar");
        Checkbox repeticionCheckbox = new Checkbox("Con repetición");
        Button generarBtn = new Button("Generar números");
        Button limpiarBtn = new Button("Limpiar");
        VerticalLayout resultadoLayout = new VerticalLayout(); // aquí se mostrarán los números

        resultadoLayout.setPadding(true);
        resultadoLayout.setSpacing(true);
        resultadoLayout.getStyle().set("flex-wrap", "wrap");

        generarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        limpiarBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        // Configurar campos
        minField.setMin(1);
        minField.setStepButtonsVisible(true);
        maxField.setMin(1);
        maxField.setStepButtonsVisible(true);
        cantidadField.setMin(1);
        cantidadField.setStepButtonsVisible(true);

        // Acción del botón
        generarBtn.addClickListener(e -> {
            resultadoLayout.removeAll();
            int min = minField.getValue() != null ? minField.getValue().intValue() : 1;
            int max = maxField.getValue() != null ? maxField.getValue().intValue() : 10;
            int cantidad = cantidadField.getValue() != null ? cantidadField.getValue().intValue() : 1;
            boolean conRepeticion = repeticionCheckbox.getValue();
            String titulo = textField.getValue() != null ? textField.getValue() : "Sorteo";

            if (min > max || cantidad < 1 || (!conRepeticion && cantidad > (max - min + 1))) {
                Notification.show("Parámetros inválidos").setPosition(Notification.Position.MIDDLE);
                return;
            }

            // Generar números
            List<Integer> numeros = generarNumeros(min, max, cantidad, conRepeticion);

            // Título del sorteo
            H2 tituloSorteo = new H2(titulo);
            resultadoLayout.add(tituloSorteo);

            // Mostrar números
            HorizontalLayout numerosLayout = new HorizontalLayout();
            numerosLayout.getStyle().set("flex-wrap", "wrap");
            numerosLayout.getStyle().set("display", "flex");

            for (Integer num : numeros) {
                Div numeroBox = new Div();
                numeroBox.setText(num.toString());
                numeroBox.getStyle().set("background-color", "#007bff");
                numeroBox.getStyle().set("color", "white");
                numeroBox.getStyle().set("padding", "20px");
                numeroBox.getStyle().set("border-radius", "15px");
                numeroBox.getStyle().set("font-size", "24px");
                numeroBox.getStyle().set("margin", "10px");
                numeroBox.getStyle().set("text-align", "center");
                numeroBox.getStyle().set("min-width", "60px");
                numerosLayout.add(numeroBox);
            }

            resultadoLayout.add(numerosLayout);

            // Mostrar primer y último número
            if (!numeros.isEmpty()) {
                Paragraph resumen = new Paragraph("Inicia con: " + numeros.get(0) + " – Finaliza con: " + numeros.get(numeros.size() - 1));
                resultadoLayout.add(resumen);
            }
        });

        //Acción del botón limpiar.
        limpiarBtn.addClickListener(e ->{
            textField.clear();
            minField.clear();
            maxField.clear();
            cantidadField.clear();
            repeticionCheckbox.clear();
            resultadoLayout.removeAll();
        });

        // Estructura
        layoutColumn2.add(textField, minField, maxField, cantidadField, repeticionCheckbox, generarBtn, limpiarBtn);
        layoutRow.add(layoutColumn2, resultadoLayout);
        getContent().add(layoutRow);
    }

    private List<Integer> generarNumeros(int min, int max, int cantidad, boolean conRepeticion) {
        Random random = new Random();
        List<Integer> resultado = new ArrayList<>();

        if (conRepeticion) {
            for (int i = 0; i < cantidad; i++) {
                resultado.add(random.nextInt((max - min + 1)) + min);
            }
        } else {
            List<Integer> pool = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                pool.add(i);
            }
            Collections.shuffle(pool);
            resultado.addAll(pool.subList(0, cantidad));
        }

        return resultado;
    }
}

