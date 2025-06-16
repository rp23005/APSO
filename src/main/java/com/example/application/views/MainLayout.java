package com.example.application.views;

import com.example.application.views.amigosecreto.AmigoSecretoView;
import com.example.application.views.coin.CoinView;
import com.example.application.views.dice.DiceView;
import com.example.application.views.groupgenerator.GroupGeneratorView;
import com.example.application.views.numAleatorios.NumAleatoriosView;
import com.example.application.views.roulette.RouletteView;
import com.example.application.views.timer.TimerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;

import org.vaadin.lineawesome.LineAwesomeIcon;


@Layout
@AnonymousAllowed
@CssImport(value = "./styles/layout-styles.css")
@JavaScript("./js/layout.js")
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {

        // Botón de regreso
        Button backButton = new Button("Atrás", e -> 
            getUI().ifPresent(ui -> ui.navigate("games")));
        backButton.addClassName("back-button");

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        addToDrawer(backButton);
    }


    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        toggle.addClassName("menu-toggle"); // Clase para estilos

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        viewTitle.addClassName("view-title"); // Clase para estilos

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        // Encabezado con logo
        Div headerContent = new Div();
        headerContent.addClassName("sidebar-header");
        
        Image logo = new Image("images/LOGO-APSO.png", "APSO");
        logo.addClassName("sidebar-logo");
        logo.setHeight("50px");
        
        Span appName = new Span("APSO");
        appName.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.FontSize.XLARGE);
        appName.addClassName("app-name");
        
        headerContent.add(logo, appName);
        Header header = new Header(headerContent);

        Scroller scroller = new Scroller(createNavigation());
        scroller.addClassName("sidebar-scroller");

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
    SideNav nav = new SideNav();
    nav.addClassName("side-nav");
    
    // Lista de juegos con sus iconos.
    SideNavItem[] items = {
        new SideNavItem("Generador de grupos", GroupGeneratorView.class, LineAwesomeIcon.USERS_SOLID.create()),
        new SideNavItem("Ruleta", RouletteView.class, LineAwesomeIcon.CIRCLE.create()),
        new SideNavItem("Números Aleatorios", NumAleatoriosView.class, LineAwesomeIcon.HASHTAG_SOLID.create()),
        new SideNavItem("Cronómetro", TimerView.class, LineAwesomeIcon.CLOCK.create()),
        new SideNavItem("Dados", DiceView.class, LineAwesomeIcon.CUBES_SOLID.create()),
        new SideNavItem("Moneda", CoinView.class, LineAwesomeIcon.COINS_SOLID.create()),
        new SideNavItem("Amigos secretos", AmigoSecretoView.class, LineAwesomeIcon.GIFT_SOLID.create())
    };
    
    // Agregar items con índice para animaciones CSS
    for (int i = 0; i < items.length; i++) {
        SideNavItem item = items[i];
        item.getElement().setAttribute("style", "--index: " + i);
        nav.addItem(item);
    }
    
    return nav;
}

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
