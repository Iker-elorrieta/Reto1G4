package Modelo;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cronómetro general de workout (cuenta hacia adelante sin límite).
 */
public class Cronometro implements Runnable {

    private int segundos;
    private AtomicBoolean enEjecucion;
    private AtomicBoolean pausado;
    private Thread hilo;
    private OnTickListener listener;

    public interface OnTickListener {
        void onTick(int segundos);
    }

    public Cronometro(OnTickListener listener) {
        this.listener = listener;
        this.segundos = 0;
        this.enEjecucion = new AtomicBoolean(false);
        this.pausado = new AtomicBoolean(false);
    }

    public void iniciar() {
        if (enEjecucion.get()) return;
        enEjecucion.set(true);
        pausado.set(false);
        hilo = new Thread(this);
        hilo.start();
    }

    public void pausar() {
        pausado.set(!pausado.get());
    }

    public void parar() {
        enEjecucion.set(false);
        pausado.set(false);
    }

    @Override
    public void run() {
        try {
            while (enEjecucion.get()) {
                if (!pausado.get()) {
                    segundos++;
                    if (listener != null)
                        listener.onTick(segundos);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
