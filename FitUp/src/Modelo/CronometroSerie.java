package Modelo;

import java.util.concurrent.atomic.AtomicBoolean;


public class CronometroSerie implements Runnable {

    private int duracion;
    private AtomicBoolean enEjecucion;
    private AtomicBoolean pausado;
    private Thread hilo;
    private OnCountdownListener listener;

    public interface OnCountdownListener {
        void onTick(int segundosRestantes);
        void onFinish();
    }

    public CronometroSerie(int duracion, OnCountdownListener listener) {
        this.duracion = duracion;
        this.listener = listener;
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
            for (int i = duracion; i >= 0 && enEjecucion.get(); i--) {
                if (!pausado.get()) {
                    if (listener != null) listener.onTick(i);
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(200);
                    i++;
                }
            }
            if (listener != null && enEjecucion.get())
                listener.onFinish();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
