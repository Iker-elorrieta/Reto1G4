package Modelo;

import java.util.concurrent.atomic.AtomicBoolean;

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

    public void iniciar() {
        if (enEjecucion.get()) return;
        enEjecucion.set(true);
        pausado.set(false);
        hilo = new Thread(this, "Cronometro-Workout");
        hilo.start();
    }


    public void pausar() {
        pausado.set(!pausado.get());
    }


    public void reanudar() {
        pausado.set(false);
    }

 
    public void parar() {
        enEjecucion.set(false);
        pausado.set(false);
    }

    public int getSegundos() {
        return segundos;
    }

  
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
