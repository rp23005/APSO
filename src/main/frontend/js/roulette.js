/**
 * VARIABLES USADAS
 * 
 *
 * - canvas: este hace referencia al elemento HTML <canvas> donde se dibuja la ruleta.
 * - ctx: es el contexto de dibujo del canvas en 2D, y nos ayuda a hacer gráficos
 * - angle: angulo actual de rotación de la ruleta en radianes
 * - spinning: Indica si la ruleta está girando para evitar girar al mismo tiempo
 * - animationId: Guarda el ID de la animación, y esto sirvce para detenerla si es necesario.
 * - sections: Lista de nombres de los participantes o elementos de la ruleta
 * - winnerIndex: Índice del elemento que ha ganado en la lista `sections`
 */
let canvas, ctx;
let angle = 0;
let spinning = false;
let animationId;
let sections = [];
let winnerIndex = -1;

/**
 * Inicializa la ruleta con los elementos dados
 * elementsStr - es una cadena de elementos separados por comas.
 * 
 * Esta función agarra una lista de nombres en texto, y la divide en un array
 * elimina espacios innecesarios y luego prepara la ruleka llamando a `initCanvas()`.
 */
window.initRuleta = function(elementsStr) {
    sections = elementsStr.split(',').map(e => e.trim()).filter(e => e);
    initCanvas();
};

/**
 *Actualiza las secciones de la ruleta con nuevos elementos.
 *newElementsStr - Nueva cadena de elementos separados por comas.
 * restablece el angul0 a 0 y borra el indice del ganador antes de dibujar otra vez a  la ruleta.
 */
window.updateSections = function(newElementsStr) {
    sections = newElementsStr.split(',').map(e => e.trim()).filter(e => e);
    angle = 0;
    winnerIndex = -1;
    drawWheel();
};

/**
 * Inicia la animación de giro de la ruleta
 *
 * Usa `requestAnimationFrame()` para hacer una animación que se vea fluida y natural
 *se aplica una función de easing (suavizado) para dar la sensación de aceleración en la ruleta cuabndo está girando
 * al inicio y frenado al final, en lugar de un giro brusco con velocidad constante
 */
window.startSpin = function() {
    if (spinning) return; // Evita iniciar otro giro si la ruleta ya está girando
    spinning = true;
    winnerIndex = -1;
    
    const startTime = Date.now(); // Marca el tiempo de inicio de la animación
    const spinDuration = 3000; // Duración total del giro en milisegundos (3 segundos)
    const startAngle = angle; // Captura el ángulo actual antes de empezar a girar
    const totalRotations = 5 + Math.random() * 5; // Giros entre 5 y 10 vueltas
    /**
     * es una funcion recursiva que anima el giro de la ruleta
     * Utiliza `requestAnimationFrame()` para actualizar la animación en cada frame
     */
    const animate = () => {
        const progress = (Date.now() - startTime) / spinDuration; // Progreso de 0 a 1.

        if (progress < 1) {
            // aplicmos easing  para suavizar la aceleración y cuando deja de acelerar
            const easedProgress = 1 - Math.pow(1 - progress, 3);
            angle = startAngle + (easedProgress * totalRotations * Math.PI * 2);
            drawWheel();
            animationId = requestAnimationFrame(animate);
        } else {
            // Cuando termina el giro normalizamos el angulo para evitar acumulaciones infinitas.}
            spinning = false;
            angle %= Math.PI * 2;
            showResult();
        }
    };
    
    animationId = requestAnimationFrame(animate);
};

/**
 *Crea y configura el elemento canvas donde se dibuja la ruleta
 */
function initCanvas() {
    const container = document.getElementById("ruleta-container");
    container.innerHTML = ''; // Borra cualquier contenido anterior

    // Crea un nuevo elemento <canvas> y lo añade al contenedor
    canvas = document.createElement('canvas');
    canvas.width = 300;
    canvas.height = 300;
    container.appendChild(canvas);
    
    ctx = canvas.getContext('2d'); // Obtiene el contexto 2D para pode rdibujar
    drawWheel(); // dibuja la ruleta con los elementos actuales dados
}

/**
 *dibuja la ruleta en el canvas, y la parte en secciones
 */
function drawWheel() {
    if (!sections.length) return;

    ctx.clearRect(0, 0, 300, 300); //Limpia el area antes de dibujar.
    
    const sectionAngle = (Math.PI * 2) / sections.length; // Calcula el ángulo de cada sección.

    ctx.save(); //para guardar el estado antes de aplicar transformaciones.
    ctx.translate(150, 150); // se ´posiciona al centro de la ruleta
    ctx.rotate(angle); //gira la ruleta al angulo actual

    // Dibuja cada sección de la ruleta.
    for (let i = 0; i < sections.length; i++) {
        ctx.beginPath();
        ctx.moveTo(0, 0); // Comienza en el  centro de la ruleta.
        ctx.arc(0, 0, 150, i * sectionAngle, (i + 1) * sectionAngle); //dibuja un segmento redondo.

        //le da un color  color a la sección. Y la que sea elegida va a ponerse de color dorado (amarillo en realidad).
        ctx.fillStyle = i === winnerIndex ? "gold" : `hsl(${(i * 360) / sections.length}, 70%, 70%)`;
        ctx.fill(); // Rellena la sección con el color correspondiente.

        // Dibuja el texto de cada sección
        ctx.save(); // Guarda la transformación actual.
        ctx.rotate(i * sectionAngle + sectionAngle / 2); //gira el texto para alinearlo correctamente.
        ctx.fillStyle = "black"; // Color del texto
        ctx.font = "14px Arial"; // Fuente y tamaño del texto
        ctx.textAlign = "center"; //alinea el texto en el centro
        ctx.fillText(sections[i], 100, 10); // Dibuja el texto en la sección.
        ctx.restore(); // vuelve a la transformación anterior para evitar rotaciones que se aculuman.
    }
    
    ctx.restore(); //rstaura el estado del contexto por si se dibuja leugo de nuevo.
}

/**
 *selecciona a la sección ganadora gracias al angulo final y la muestra en la pantalla con unos emojis.

 */
function showResult() {
    const sectionAngle = (Math.PI * 2) / sections.length;

    //normaliza el amgulo para calcular la sección en la que se detuvo la ruleta
    const normalizedAngle = (Math.PI * 2 - angle % (Math.PI * 2)) % (Math.PI * 2);
    
    // Calcula el index de la seccion que ganó por rl angulo final
    winnerIndex = Math.floor(normalizedAngle / sectionAngle) % sections.length;
    
    drawWheel(); // Redibuja la ruleta con la sección ganadora resaltada.

    //Muestra el ganador en la pantalla
    const resultContainer = document.getElementById("resultado-container");
    resultContainer.textContent = `🏆🤯 Ganador: ${sections[winnerIndex]} 🪅🎉🎉`;
}
