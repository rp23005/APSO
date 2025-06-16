window.addEventListener('WebComponentsReady', () => {
    const appLayout = document.querySelector('vaadin-app-layout');
    
    if (appLayout) {
        // Observador para detectar cuando el drawer se abre/cierra
        const observer = new MutationObserver(() => {
            animateMenuItems(appLayout);
        });
        
        observer.observe(appLayout, {
            attributes: true,
            attributeFilter: ['drawer-opened']
        });
        
        // Animar elementos inicialmente.
        setTimeout(() => animateMenuItems(appLayout), 300);
    }
});

function animateMenuItems(appLayout) {
    const drawer = appLayout.shadowRoot.querySelector('[part="drawer"]');
    
    if (!drawer) return;
    
    const items = drawer.querySelectorAll('[part="item"]');
    
    items.forEach((item, index) => {
        // Solo animar si el drawer está abierto
        if (appLayout.drawerOpened) {
            item.style.animation = 'none';
            setTimeout(() => {
                item.style.animation = `slideIn 0.3s ease ${index * 50}ms forwards`;
            }, 10);
        }
    });
}