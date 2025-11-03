package Modelo;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cronómetro general de workout (cuenta hacia adelante sin límite).
 * Añadidos: getSegundos(), reanudar(), estaPausado().
 */
public class Cronometro implements Runnable {

    private int segundos;
    private final AtomicBoolean enEjecucion;
    private final AtomicBoolean pausado;
    private Thread hilo;
    private final OnTickListener listener;

    public interface OnTickListener {
        void onTick(int segundos);
    }

    public Cronometro(OnTickListener listener) {
        this.listener = listener;
        this.segundos = 0;
        this.enEjecucion = new AtomicBoolean(false);
        this.pausado = new AtomicBoolean(false);
    }

    /**
     * Inicia el cronómetro (si no está ya en ejecución).
     */
    public void iniciar() {
        if (enEjecucion.get()) return;
        enEjecucion.set(true);
        pausado.set(false);
        hilo = new Thread(this, "Cronometro-Workout");
        hilo.start();
    }

    /**
     * Pausa (toggle) — mantiene el valor actual de segundos.
     * Si quieres comportamiento separado que siempre pause en true,
     * reemplaza la implementación por pausado.set(true);
     */
    public void pausar() {
        pausado.set(!pausado.get());
    }

    /**
     * Reanuda la ejecución si estaba pausado.
     */
    public void reanudar() {
        pausado.set(false);
    }

    /**
     * Para y resetea pausa (no resetea segundos; si quieres que resetee,
     * añade un método reset()).
     */
    public void parar() {
        enEjecucion.set(false);
        pausado.set(false);
    }

    /**
     * Devuelve los segundos transcurridos (thread-safe de lectura).
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * Indica si está pausado.
     */
    public boolean estaPausado() {
        return pausado.get();
    }

    @Override
    public void run() {
        try {
            while (enEjecucion.get()) {
                if (!pausado.get()) {
                    segundos++;
                    if (listener != null) listener.onTick(segundos);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
