package com.example.application.views.main;

import com.example.application.views.SimpleLayout;
import com.example.application.views.gameMenu.GameMenuView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.dependency.CssImport;

@PageTitle("Menu Principal")
@Route(value = "Menu", layout = SimpleLayout.class)
@AnonymousAllowed
@CssImport(value = "./styles/main-styles.css")
public class MainView extends VerticalLayout {

    private final Button backButton;

    public MainView() {
        // Configuración principal
        addClassName("main-view");
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getElement().setAttribute("theme", "dark");

        // Crear efecto de burbujas
        createBubbles();

        // Botón de atrás
        backButton = new Button("Atrás", e -> {
            getUI().ifPresent(ui -> ui.navigate("empty"));
        });
        backButton.addClassName("back-button");
        add(backButton);

        // Contenedor de la tarjeta - ahora más estrecho
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.addClassName("card-container");
        cardLayout.setSpacing(false);
        cardLayout.setPadding(false);
        cardLayout.setAlignItems(Alignment.CENTER);
        cardLayout.setWidth("300px"); // Ancho más compacto

        // Título principal - texto más compacto
        H1 title = new H1("Bienvenido a APSO");
        title.addClassName("title");

        // Contenedor para botones
        VerticalLayout buttonContainer = new VerticalLayout();
        buttonContainer.addClassName("button-container");
        buttonContainer.setPadding(false);
        buttonContainer.setSpacing(false);
        buttonContainer.setWidthFull();

        // Botones con texto más compacto
        Button registerButton = createAuth0Button(
                "Crear Cuenta",
                "register-button",
                VaadinIcon.USER_CHECK,
                "/oauth2/authorization/auth0"
        );

        Button loginButton = createAuth0Button(
                "Iniciar Sesión",
                "login-button",
                VaadinIcon.SIGN_IN,
                "/oauth2/authorization/auth0"
        );

        Button guestButton = createActionButton(
                "Jugar Invitado", // Texto reducido
                "guest-button",
                VaadinIcon.GAMEPAD,
                GameMenuView.class);

        buttonContainer.add(registerButton, loginButton, guestButton);
        cardLayout.add(title, buttonContainer);
        add(cardLayout);
    }

    private void createBubbles() {
        getElement().executeJs(
            "for(let i = 0; i < 15; i++) {" +
            "   const bubble = document.createElement('div');" +
            "   bubble.className = 'bubble';" +
            "   const size = Math.random() * 20 + 5;" +
            "   bubble.style.width = size + 'px';" +
            "   bubble.style.height = size + 'px';" +
            "   bubble.style.left = Math.random() * 100 + '%';" +
            "   bubble.style.top = Math.random() * 100 + '%';" +
            "   bubble.style.animationDuration = (Math.random() * 6 + 4) + 's';" +
            "   bubble.style.animationDelay = (Math.random() * 5) + 's';" +
            "   this.appendChild(bubble);" +
            "}", 
            getElement()
        );
    }

    private Button createAuth0Button(String text, String buttonClass, VaadinIcon icon, String url) {
        Button button = new Button(text, icon.create());
        button.addClassName("auth-button");
        button.addClassName(buttonClass);
        button.addClickListener(e -> getUI().ifPresent(ui -> ui.getPage().setLocation(url)));
        button.setIconAfterText(false);
        return button;
    }

    private Button createActionButton(String text, String buttonClass, VaadinIcon icon, Class<? extends Component> view) {
        Button button = new Button(text, icon.create());
        button.addClassName("auth-button");
        button.addClassName(buttonClass);
        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(view)));
        button.setIconAfterText(false);
        return button;
    }
}