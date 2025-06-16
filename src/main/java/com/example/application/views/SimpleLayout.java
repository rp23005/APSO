package com.example.application.views;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
public class SimpleLayout extends AppLayout implements RouterLayout {

    public SimpleLayout() {
        setPrimarySection(Section.NAVBAR);
        
        // Configuración minimalista sin navbar visible
        getElement().getStyle()
            .set("min-height", "100vh")
            .set("background", "transparent");
            
        // Ocultar elementos innecesarios del AppLayout
        getElement().executeJs(""
            + "this.$.navbar.style.display = 'none';"
            + "this.$.drawer.style.display = 'none';"
            + "this.$.content.style.padding = '0';"
            + "this.$.content.style.height = '100%';");
    }
}
