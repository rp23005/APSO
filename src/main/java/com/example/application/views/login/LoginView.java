package com.example.application.views.login;



import com.example.application.services.ParticipantService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("login")
public class LoginView extends VerticalLayout {

    private final ParticipantService participantService;

    public LoginView(ParticipantService participantService) {
        this.participantService = participantService;
        
        // Configuración del layout principal
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setMargin(true);
        setHeightFull();

        // Contenedor del formulario
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setMaxWidth("600px");
        formLayout.setPadding(true);
        formLayout.getStyle()
                .set("border", "1px solid lightgray")
                .set("border-radius", "10px")
                .set("padding", "30px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)")
                .set("background-color", "white");

        // Campos de entrada
        TextField usernameField = new TextField("Nombre de usuario");
        PasswordField passwordField = new PasswordField("Contraseña");
        
        // Estilo campos de texto
        usernameField.setWidthFull();
        passwordField.setWidthFull();
        usernameField.getStyle().set("font-size", "20px");
        passwordField.getStyle().set("font-size", "20px");

        // Botón de login
        Button loginButton = new Button("Iniciar sesión", event -> {
            boolean authenticated = this.participantService.Login(
                usernameField.getValue(), 
                passwordField.getValue()
            ).isPresent();

            if (authenticated) {
                Notification.show("Inicio de sesión exitoso");
                getUI().ifPresent(ui -> ui.navigate("games"));
            } else {
                Notification.show("Credenciales incorrectas", 3000, Notification.Position.MIDDLE);
            }
        });

        // Estilo del botón
        loginButton.getStyle()
                .set("font-size", "20px")
                .set("background-color", "#007bff")
                .set("color", "white");
        loginButton.setWidthFull();

        // Enlace para el registro
        Anchor registerLink = new Anchor("register", "¿No tienes cuenta? Regístrate aquí");
        registerLink.getStyle()
                .set("margin-top", "20px")
                .set("color", "#007bff")
                .set("text-decoration", "none");

        // agregar componentes al formulario
        formLayout.add(usernameField, passwordField, loginButton, registerLink);
        
        // Centrar el formulario verticalmente
        add(formLayout);
    }
}