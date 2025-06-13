package com.example.application.views.gameMenu;

import com.example.application.views.roulette.RouletteView;
import com.example.application.views.timer.TimerView;
import com.example.application.views.coin.CoinView;
import com.example.application.views.dice.DiceView;
import com.example.application.views.groupgenerator.GroupGeneratorView;
import com.example.application.views.numAleatorios.NumAleatoriosView;
import com.example.application.views.amigosecreto.AmigoSecretoView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("games")
public class GameMenuView extends AppLayout {

    public GameMenuView() {
        VerticalLayout menuLayout = new VerticalLayout();

        Button backToLogin = new Button("Volver al inicio", e -> 
            getUI().ifPresent(ui -> ui.navigate(""))
        );

        menuLayout.add(new RouterLink("Juego de dados", DiceView.class));
        menuLayout.add(new RouterLink("Generador de grupos", GroupGeneratorView.class));
        menuLayout.add(new RouterLink("Juego de la ruleta", RouletteView.class));
        menuLayout.add(new RouterLink("Juego de la moneda", CoinView.class));
        menuLayout.add(new RouterLink("Numeros Aleatorios", NumAleatoriosView.class));
        menuLayout.add(new RouterLink("Amigo Secreto", AmigoSecretoView.class));
        menuLayout.add(new RouterLink("Cronómetro", TimerView.class));
        menuLayout.add(backToLogin);

        addToDrawer(menuLayout);
    }
}
