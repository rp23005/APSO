import { LitElement, html, css } from 'lit';

export class TimerComponent extends LitElement {
    static properties = {
        time: { type: Number },
        running: { type: Boolean },
        formattedTime: { type: String },
        timerInterval: { type: Object }
    };

    static styles = css`
        .timer-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            border-radius: 12px;
            padding: 2rem;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: 0 auto;
        }

        .display {
            font-size: 4rem;
            font-weight: bold;
            margin: 20px 0;
            color: #2c3e50;
            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .controls {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 20px;
            justify-content: center;
        }

        button {
            padding: 12px 24px;
            font-size: 1rem;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s;
            font-weight: 600;
            min-width: 120px;
        }

        .start-btn {
            background-color: #2ecc71;
            color: white;
        }

        .pause-btn {
            background-color: #f39c12;
            color: white;
        }

        .reset-btn {
            background-color: #3498db;
            color: white;
        }

        .stop-btn {
            background-color: #e74c3c;
            color: white;
        }

        button:hover {
            opacity: 0.9;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }

        button:active {
            transform: translateY(0);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        /* Estilos responsivos */
        @media (max-width: 768px) {
            .display {
                font-size: 3rem;
            }
            
            .controls {
                flex-direction: column;
                align-items: stretch;
            }
            
            button {
                width: 100%;
            }
        }

        /* Animación para el tiempo activo */
        .running .display {
            animation: pulse 1.5s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.03); }
            100% { transform: scale(1); }
        }
    `;

    constructor() {
        super();
        this.time = 0;
        this.running = false;
        this.formattedTime = '00:00:00';
        this.timerInterval = null;
    }

    formatTime(seconds) {
        const hrs = Math.floor(seconds / 3600);
        const mins = Math.floor((seconds % 3600) / 60);
        const secs = seconds % 60;
        return `${hrs.toString().padStart(2, '0')}:${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    }

    startTimer() {
        if (this.running) return;
        this.running = true;
        const startTime = Date.now() - this.time * 1000;
        
        this.timerInterval = setInterval(() => {
            this.time = Math.floor((Date.now() - startTime) / 1000);
            this.formattedTime = this.formatTime(this.time);
            this.requestUpdate();
        }, 1000);
    }

    pauseTimer() {
        if (!this.running) return;
        this.running = false;
        clearInterval(this.timerInterval);
    }

    resetTimer() {
        this.pauseTimer();
        this.time = 0;
        this.formattedTime = this.formatTime(this.time);
    }

    stopTimer() {
        this.resetTimer();
        this.dispatchEvent(new CustomEvent('timer-stopped', {
            bubbles: true,
            composed: true
        }));
    }

    render() {
        return html`
            <div class="timer-container ${this.running ? 'running' : ''}">
                <div class="display">${this.formattedTime}</div>
                <div class="controls">
                    <button class="start-btn" @click=${this.startTimer}>Iniciar</button>
                    <button class="pause-btn" @click=${this.pauseTimer}>Pausar</button>
                    <button class="reset-btn" @click=${this.resetTimer}>Reiniciar</button>
                    <button class="stop-btn" @click=${this.stopTimer}>Finalizar</button>
                </div>
            </div>
        `;
    }
}

customElements.define('timer-component', TimerComponent);