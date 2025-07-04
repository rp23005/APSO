package com.example.application.views.register;

import com.example.application.models.Participant;
import com.example.application.services.ParticipantService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends VerticalLayout {

    private final ParticipantService participantService;

    public RegisterView(ParticipantService participantService) {
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
        EmailField emailField = new EmailField("Correo (Opcional)");

        usernameField.setWidthFull();
        passwordField.setWidthFull();
        emailField.setWidthFull();

        usernameField.getStyle().set("font-size", "20px");
        passwordField.getStyle().set("font-size", "20px");
        emailField.getStyle().set("font-size", "20px");

        // Botón de registro
        Button registerButton = new Button("Registrarse", event -> {
            Participant newParticipant = new Participant();
            newParticipant.setUsername(usernameField.getValue());
            newParticipant.setPassword(passwordField.getValue());
            newParticipant.setEmail(emailField.getValue());

            this.participantService.register(newParticipant);
            Notification.show("Registro exitoso");
            getUI().ifPresent(ui -> ui.navigate("games"));
        });

        registerButton.getStyle()
                .set("font-size", "20px")
                .set("background-color", "#007bff")
                .set("color", "white");
        registerButton.setWidthFull();

        // Enlace para login 
        Anchor loginLink = new Anchor("login", "¿Ya tienes una cuenta? Inicia sesión aquí");
        loginLink.getStyle()
                .set("margin-top", "20px")
                .set("color", "#007bff")
                .set("text-decoration", "none");

        // Agregar componentes
        formLayout.add(usernameField, passwordField, emailField, registerButton, loginLink);
        add(formLayout);
    }
}