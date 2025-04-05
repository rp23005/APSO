package com.example.application.views.roulette;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Roulette")
@JavaScript("./js/roulette.js")
@Route("Roulette")
public class RouletteView extends VerticalLayout {

    private final Div canvasContainer; // Contenedor para el canvas donde se dibuja la ruleta
    private final TextField elementsInput; // Campo de texto para ingresar los elementos de la ruleta
    private final Button configureButton; // Botón subir nuevos elementos a la  ruleta
    private final Button spinButton; // Botón para iniciar el giro de la ruleta
    private final Div resultContainer; // Contenedor donde se muestra el resultado del giro

    /**
     * Este es el principal que configura la GUI de la vista
     *  definimos los componentes de la interfaz
     */
    public RouletteView() {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        
        // Configuración del campo donde va a ser la entrada del texto que el usuario pondrá para que vaya dentro de la ruleta
        elementsInput = new TextField("Elementos (separados por comas)");
        elementsInput.setWidth("300px");
        elementsInput.setValue("Elemento 1, Elemento 2, Elemento 3, Elemento 4");
        
        // Configuración de todos los botones a usar
        configureButton = new Button("Ingresar elementos a la Ruleta", e -> configureRoulette());
        spinButton = new Button("Girar Ruleta", e -> spinRoulette());

        configureButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        spinButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        
        // Contenedor del canvas para la ruleta ruleta
        canvasContainer = new Div();
        canvasContainer.setId("ruleta-container");
        canvasContainer.setWidth("300px");
        canvasContainer.setHeight("300px");
        canvasContainer.getStyle().set("border", "1px solid #ddd");
        
        // Contenedor del resultado donde se muestra el ganador seleccionado de forma ramdom
        resultContainer = new Div();
        resultContainer.setId("resultado-container");
        resultContainer.getStyle()
                .set("font-size", "1.5em")
                .set("margin-top", "1em");

        // agregamso a los componentes a la vista en orden
        add(elementsInput, configureButton, canvasContainer, spinButton, resultContainer);
    }

    /**
     * es un metodo que se va a ejecutar cuando la vista se adjunta al DOM
     * se inicia la ruleta con los elementos por defecto (los que están escritos arriba en la parte de elementInput en el const.).
     * 
     * attachEvent es un Evento de adjunción de la vista
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        String elements = elementsInput.getValue().replaceAll("\\s*,\\s*", ","); // Limpia espacios que esten puestos de más para evitar errores
        attachEvent.getUI().getPage().executeJs("window.initRuleta($0)", elements); // Llama a la función JavaScript para inicializar la ruleta
    }

    /**
     * este metodo tiene como finalidad actualizar los elementos de la ruleta con los valores que el usuario haya ingresado por pantalla en el campo de texto.
     * luego llama a la funcion de JavaScript creada con el nombre updateSections() con los elementos ingresados
     */
    private void configureRoulette() {
        String elements = elementsInput.getValue().replaceAll("\\s*,\\s*", ","); // Limpia los espacios que están al lado de las comas
        getElement().executeJs("window.updateSections($0)", elements); //lama a la función JavaScript para actualizar la ruleta
    }

    /**
     * este mnetodo es el que inicia la animación que hace que la ruleta gire
     * y llama a la función de JavaScript startSpin() para inciarlo
     */
    private void spinRoulette() {
        getElement().executeJs("window.startSpin()"); // Llama a la función JavaScript para girar la ruleta
    }
}