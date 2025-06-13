package com.example.application.views.timer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cronómetro")
@Route("Timer")
@CssImport("./styles/timer-styles.css")
@JavaScript("./js/timer.js")
public class TimerView extends VerticalLayout {

    public TimerView(){

        /* Alimeamos los elementos en el centro */
        setAlignItems(Alignment.CENTER);

        addClassName("timer-view-container");

        /* Usamos el componente Wrapper creado */ 
        TimerComponent timer = new TimerComponent();

        /* Escuchamos el evento personalizado */
        timer.getElement().addEventListener("timer-stopped", e -> {
            Notification notification = Notification.show(
                "El temporizador se ha detenido correctamente.",
                3000,
                Position.BOTTOM_CENTER
            );
            notification.addClassName("sucess-notification");
        });
        
        add(timer);
    }
}
