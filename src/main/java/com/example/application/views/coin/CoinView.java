package com.example.application.views.coin;

import java.util.Timer;
import java.util.TimerTask;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Coin Game")
@Route("Coin")
@CssImport("./styles/coin-styles.css")
public class CoinView extends VerticalLayout {
    
    private final Image coinImage = new Image();
    private final Button flipButton = new Button("Lanzar");

    private final UI ui;

    public CoinView() {
        this.ui = UI.getCurrent();

        coinImage.setSrc("images/coin-front.png");
        coinImage.addClassName("coin");
        flipButton.addClickListener(e -> animateFlip());

        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        add(coinImage, flipButton);
    }
    
    private void animateFlip() {
        // Lógica aleatoria
        boolean isHeads = Math.random() < 0.5;
        String resultImage = isHeads ? "images/coin-front.png" : "images/coin-back.png";

        // Reiniciar animación forzadamente usando JS
        coinImage.getElement().executeJs(
            "this.style.animation = 'none';" +
            "this.offsetHeight;" +
            "this.style.animation = 'flip 1.5s ease-out';"
        );

        // Esperar a que termine la animación (1.5s)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ui.access(() -> {
                    coinImage.setSrc(resultImage);

                    // Mostrar notificación con estilo personalizado
                    Notification notification = new Notification(isHeads ? "¡Cara! 🎉🪅🎉" : "¡Cruz! -🎉🪅🎉", 3000, Notification.Position.MIDDLE);
                    notification.addThemeName("custom-notification");
                    notification.open();
                });
            }
        }, 1500); 
    }
}