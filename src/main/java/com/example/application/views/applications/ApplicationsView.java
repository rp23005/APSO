package com.example.application.views.applications;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.UI;

@PageTitle("Aplications")
@Route("Aplications")
@Menu(order = 1, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class ApplicationsView extends HorizontalLayout {
    private Button moveToDice;

    public ApplicationsView() {
        moveToDice = new Button("Lanzar dados", event -> 
        UI.getCurrent().navigate("Dice") // Navigate to the Dice view
    );

         VerticalLayout centeredLayout = new VerticalLayout(moveToDice);
         centeredLayout.setSizeFull(); 
         centeredLayout.setAlignItems(Alignment.CENTER);
         centeredLayout.setJustifyContentMode(JustifyContentMode.CENTER); 
 
         add(centeredLayout); 
         setSizeFull(); 
    }
}
