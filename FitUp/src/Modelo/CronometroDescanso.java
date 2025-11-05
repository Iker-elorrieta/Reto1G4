package Modelo;

import java.util.concurrent.atomic.AtomicBoolean;


public class CronometroDescanso implements Runnable {

    private int duracion;
    private AtomicBoolean enEjecucion;
    private AtomicBoolean pausado;
    private Thread hilo;
    private OnRestListener listener;

    public interface OnRestListener {
        void onTick(int segundosTranscurridos);
        void onFinish();
    }

    public CronometroDescanso(int duracion, OnRestListener listener) {
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
            for (int i = 0; i <= duracion && enEjecucion.get(); i++) {
                if (!pausado.get()) {
                    if (listener != null)
                        listener.onTick(i);
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(200);
                    i--;
                }
            }
            if (listener != null && enEjecucion.get())
                listener.onFinish();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
