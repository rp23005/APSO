package com.example.application.views.gameMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;

@PageTitle("Menú de Juegos")
@Route("games")
@CssImport(value = "./styles/game-styles.css") // Archivo CSS específico
public class GameMenuView extends VerticalLayout {

    public GameMenuView() {
        addClassName("game-menu-view");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Tarjetas informativas 

        Div infoCard = new Div();
        infoCard.addClassName("info-card");
        infoCard.add(new Span("Descubre una variedad de herramientas divertidas y útiles para cualquier ocasión"));

        Div infoCard2 = new Div();
        infoCard2.addClassName("info-card");
        infoCard2.add(new Span("Todos los juegos están diseñados para ser intuitivos y fáciles de usar"));

        Div infoCard3 = new Div();
        infoCard3.addClassName("info-card");
        infoCard3.add(new Span("Selecciona un juego del menú lateral para comenzar a jugar"));

        // Contenedor principal
        Div content = new Div();
        content.addClassName("content-container");
        
        // Contenedor para el título y subtítulo
        Div titleContainer = new Div();
        titleContainer.addClassName("title-container");
        
        // Título principal
        H2 texto = new H2("Menú de Juegos");
        texto.addClassName("main-title");
        
        // Agregar título y subtítulo al contenedor
        titleContainer.add(texto, infoCard, infoCard2, infoCard3);
        
        // Agregar el contenedor de título al contenido principal
        content.add(titleContainer);
        
        // Botón de regreso
        Button backToEmpty = new Button("Página principal", e -> 
            getUI().ifPresent(ui -> ui.navigate("empty")));
        backToEmpty.addClassName("back-button");
        
        add(content, backToEmpty);
        
        // Fondo decorativo
        getElement().executeJs(
            "const background = document.createElement('div');" +
            "background.style.position = 'absolute';" +
            "background.style.top = '0';" +
            "background.style.left = '0';" +
            "background.style.width = '100%';" +
            "background.style.height = '100%';" +
            "background.style.background = 'radial-gradient(circle at top right, rgba(106, 17, 203, 0.05), transparent 70%)';" +
            "background.style.zIndex = '0';" +
            "background.style.pointerEvents = 'none';" +
            "this.appendChild(background);"
        );
    }
}