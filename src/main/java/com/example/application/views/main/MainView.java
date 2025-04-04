package com.example.application.views.main;

import com.example.application.views.MainLayout;
import com.example.application.views.gameMenu.GameMenuView;
import com.example.application.views.login.LoginView;
import com.example.application.views.register.RegisterView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu Principal")
@Route(value = "Menu", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {
        // configuración del layout principal 
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(false);
        setHeightFull();
        getStyle()
            .set("background", "linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)")
            .set("font-family", "Arial, sans-serif");

        // contenedor de la tarjeta 
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.setWidth("500px");
        cardLayout.setPadding(true);
        cardLayout.getStyle()
            .set("background", "#ffffff")
            .set("border-radius", "16px")
            .set("box-shadow", "0 8px 30px rgba(0,0,0,0.12)")
            .set("padding", "2rem")
            .set("margin", "1rem");

        // Título principal 
        H1 title = new H1("Bienvenido a APSO");
        title.getStyle()
            .set("color", "#2c3e50")
            .set("margin", "0 0 2rem 0")
            .set("font-size", "2rem")
            .set("text-align", "center");

        // Botones 
        Button registerButton = createActionButton(
            "Crear Cuenta", 
            "#2ecc71", 
            VaadinIcon.USER_CHECK,
            RegisterView.class
        );

        Button loginButton = createActionButton(
            "Iniciar Sesión", 
            "#3498db", 
            VaadinIcon.SIGN_IN,
            LoginView.class
        );

        Button guestButton = createActionButton(
            "Jugar como Invitado", 
            "#e67e22", 
            VaadinIcon.GAMEPAD,
            GameMenuView.class
        );

        cardLayout.add(title, registerButton, loginButton, guestButton);
        add(cardLayout);
    }

    private Button createActionButton(String text, String color, VaadinIcon icon, Class<? extends Component> view) {
        Button button = new Button(text, icon.create());
        button.setWidthFull();
        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(view)));
        
        // Estilo base
        button.getStyle()
            .set("background", color)
            .set("color", "white")
            .set("border", "none")
            .set("border-radius", "8px")
            .set("padding", "1.25rem")
            .set("font-size", "1.1rem")
            .set("margin", "8px 0")
            .set("cursor", "pointer")
            .set("transition", "all 0.2s ease-in-out")
            .set("display", "flex")
            .set("align-items", "center")
            .set("gap", "0.75rem");

        // eventos hover
        DomEventListener hoverEffect = e -> button.getStyle()
            .set("transform", "translateY(-2px)")
            .set("box-shadow", "0 4px 12px rgba(0,0,0,0.15)");
        
        DomEventListener resetEffect = e -> button.getStyle()
            .set("transform", "translateY(0)")
            .set("box-shadow", "none");

        button.getElement().addEventListener("mouseenter", hoverEffect);
        button.getElement().addEventListener("mouseleave", resetEffect);

        button.setIconAfterText(false);
        return button;
    }
}