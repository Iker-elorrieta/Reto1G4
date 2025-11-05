package Modelo;

import java.io.*;
import java.util.*;

public class TestOffline {
    private final Gestor g = new Gestor(); // Instancia del gestor para pruebas offline

    public void runAll() {
        try {
            // Intentar localizar el archivo usuarios.dat en la carpeta backups o en la raíz del proyecto
            String usuariosPath = new File("backups/usuarios.dat").exists() ? "backups/usuarios.dat" : "usuarios.dat";
            File usuariosFile = new File(usuariosPath);

            if (!usuariosFile.exists()) {
                System.out.println("No existe " + usuariosPath + " — crea un archivo con objetos Usuario serializados para probar.");
                // Aunque no exista, se continúa con las pruebas offline
            } else {
                // Leer usuarios desde el archivo .dat
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usuariosFile))) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Usuario> lista = (ArrayList<Usuario>) ois.readObject();
                    System.out.println("Usuarios leídos: " + lista.size());

                    if (!lista.isEmpty()) {
                        Usuario u = lista.get(0);
                        System.out.println("Primer usuario: id=" + u.getId() + " nombre=" + u.getNombre() + " correo=" + u.getCorreo());
                        // Iniciar sesión offline con el primer usuario
                        g.inicioSesionOffline(u);
                    }
                }
            }

            // Listar workouts disponibles según el nivel del usuario
            ArrayList<Workout> workouts = g.listarworkoutsOffline();
            System.out.println("Workouts offline filtrados por nivel usuario (nivel=" + (g.getDatos() != null ? g.getDatos().getNivel() : 0) + "): " + workouts.size());
            for (Workout w : workouts) {
                System.out.println(" - " + w.getId() + ": " + w.getNombre() + " (nivel=" + w.getNivel() + ")");
            }

            // Si hay workouts, mostrar detalles del primero
            if (!workouts.isEmpty()) {
                Workout first = workouts.get(0);
                System.out.println("Tiempo previsto para workout " + first.getId() + " -> " + g.conseguirTiempoPrevistoOffline(first.getId()) + " segundos");

                // Listar ejercicios del primer workout
                ArrayList<Ejercicio> ejercicios = g.listarEjerciciosOffline(first.getId());
                System.out.println("Ejercicios en workout: " + ejercicios.size());

                if (!ejercicios.isEmpty()) {
                    Ejercicio e = ejercicios.get(0);
                    // Mostrar series del primer ejercicio
                    System.out.println("Series del primer ejercicio: " + g.listarSeriesOffline(first.getId(), String.valueOf(e.getId())).size());
                }
            }

            // Probar lectura de histórico si hay usuario en sesión
            if (g.getDatos() != null && g.getDatos().getId() != 0) {
                ArrayList<Historico> hs = g.listarHistoricoOffline(g.getDatos().getId());
                System.out.println("Históricos del usuario (id=" + g.getDatos().getId() + "): " + hs.size());
                for (Historico h : hs) {
                    System.out.println(" - id=" + h.getId() + " workoutId=" + (h.getWorkout() != null ? h.getWorkout().getId() : "?") + " completado=" + h.getCompletado());
                }

                // Probar cambio de nivel offline
                System.out.println("Intentando cambiar nivel (offline):");
                String mensaje = g.cambiarNivelOffline();
                System.out.println(mensaje);
            } else {
                System.out.println("No hay usuario en sesión — inicia sesión offline con un usuario existente para probar históricos y cambio de nivel.");
            }

            // Probar escritura de histórico offline
            Historico nuevo = new Historico();
            nuevo.setId((int)(System.currentTimeMillis() % Integer.MAX_VALUE));
            Usuario uu = g.getDatos() != null ? g.getDatos() : new Usuario();
            if (uu.getId() == 0) uu.setId(100);
            nuevo.setUsuario(uu);

            if (!workouts.isEmpty()) {
                nuevo.setWorkout(workouts.get(0));
            } else {
                Workout w = new Workout();
                w.setId("w-offline-1");
                nuevo.setWorkout(w);
            }

            nuevo.setFecha(new Date());
            nuevo.setTiempoTotal(123);
            nuevo.setCompletado(3);

            g.escribirHistoricoOffline(nuevo);
            System.out.println("Histórico offline escrito (comprobable en el archivo XML de histórico).");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error durante pruebas offline: " + ex.getMessage());
        }
    }
}
