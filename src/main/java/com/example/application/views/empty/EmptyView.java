package com.example.application.views.empty;

import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.example.application.views.SimpleLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

@PageTitle("Bienvenido/a")
@Route(value = "", layout = SimpleLayout.class)
@RouteAlias(value = "empty", layout = SimpleLayout.class)
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
@CssImport(value = "./styles/empty-styles.css") // Ruta al archivo CSS
public class EmptyView extends VerticalLayout {

    public EmptyView() {
        // Aplicar clase CSS principal
        addClassName("empty-view");
        
        // Encabezado con emojis
        H2 header = new H2("✨Diviértete con los diferentes juegos que podrás encontrar en APSO 🎡🎲🎮");
        header.addClassName("welcome-message");
        
        // Contenedor para el logo con efectos
        Div logoContainer = new Div();
        logoContainer.addClassName("logo-container");
        
        Image img = new Image("images/LOGO-APSO.png", "Logo APSO");
        img.setWidth("500px");
        img.setHeight("auto");
        logoContainer.add(img);
        
        // Botón de jugar con estilo mejorado
        Button playButton = new Button("Jugar", e -> {
            getUI().ifPresent(ui -> ui.navigate("Menu"));
        });
        playButton.addClassName("play-button");
        
        add(header, logoContainer, playButton);
        
        // Configuración de layout
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setPadding(false);
        setSpacing(false);
        
        // Agregar efecto de partículas después de que la vista esté en el DOM
        getElement().executeJs(
            "setTimeout(() => {" +
            "   for(let i = 0; i < 20; i++) {" +
            "       const particle = document.createElement('div');" +
            "       particle.className = 'particle';" +
            "       particle.style.width = Math.random() * 15 + 5 + 'px';" +
            "       particle.style.height = particle.style.width;" +
            "       particle.style.left = Math.random() * 100 + '%';" +
            "       particle.style.top = Math.random() * 100 + '%';" +
            "       particle.style.animation = `float ${Math.random() * 6 + 4}s linear infinite`;" +
            "       particle.style.animationDelay = Math.random() * 5 + 's';" +
            "       this.appendChild(particle);" +
            "   }" +
            "}, 500);", 
            getElement()
        );
    }
}