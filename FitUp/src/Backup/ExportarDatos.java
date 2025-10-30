package Backup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import Modelo.Ejercicio;
import Modelo.Series;
import Modelo.Usuario;
import Modelo.Workout;

public class ExportarDatos {

    // CONSTANTES DE CONFIGURACIÓN
    public static final String CREDENCIALES_FIREBASE = "fitUp.json";
    public static final String ID_PROYECTO_FIREBASE = "fitup-8e726";
    public static final String ARCHIVO_USUARIOS = "backups/usuarios.dat";
    public static final String ARCHIVO_WORKOUTS = "backups/workouts.dat";

    // CONSTANTES DE COLECCIONES
    public static final String COLECCION_USUARIOS = "usuarios";
    public static final String COLECCION_WORKOUTS = "workouts";
    public static final String SUBCOLECCION_EJERCICIOS = "ejercicios";
    public static final String SUBCOLECCION_SERIES = "series";

    // CONSTANTES DE CAMPOS USUARIO
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_APELLIDO1 = "apellido1";
    public static final String CAMPO_APELLIDO2 = "apellido2";
    public static final String CAMPO_CORREO = "correo";
    public static final String CAMPO_CONTRASENA = "contraseña";
    public static final String CAMPO_FECHA_NAC = "fechaNac";
    public static final String CAMPO_NIVEL = "nivel";

    // CONSTANTES DE CAMPOS WORKOUT
    public static final String CAMPO_NUM_EJERCICIOS = "num_ejercicios";
    public static final String CAMPO_VIDEO = "video";

    // CONSTANTES DE CAMPOS EJERCICIO
    public static final String CAMPO_NUM_SERIES = "num_series";
    public static final String CAMPO_DESCANSO = "descanso";
    public static final String CAMPO_FOTO = "foto";

    // CONSTANTES DE CAMPOS SERIE
    public static final String CAMPO_DURACION = "duracion";
    public static final String CAMPO_REPETICIONES = "repeticiones";

    public static void main(String[] args) {
        try {
            ExportarDatos exportador = new ExportarDatos();
            System.out.println("EXPORTANDO DATOS");
            exportador.exportarDatos();
            System.out.println("DATOS COMPLETADO");

            System.out.println("CREANDO XML");
            CrearXML creadorXml = new CrearXML();
            creadorXml.generarXML();
            System.out.println("XML COMPLETADO");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportarDatos() throws Exception {
        FileInputStream archivoCredenciales = new FileInputStream(CREDENCIALES_FIREBASE);
        FirestoreOptions opcionesFirestore = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(ID_PROYECTO_FIREBASE)
                .setCredentials(GoogleCredentials.fromStream(archivoCredenciales))
                .build();
        Firestore baseDatos = opcionesFirestore.getService();

        List<Usuario> listaUsuarios = new ArrayList<>();
        List<Object> listaWorkouts = new ArrayList<>();

        // Usuarios
        ApiFuture<QuerySnapshot> consultaUsuarios = baseDatos.collection(COLECCION_USUARIOS).get();
        List<QueryDocumentSnapshot> documentosUsuarios = consultaUsuarios.get().getDocuments();
        for (QueryDocumentSnapshot doc : documentosUsuarios) {
            Usuario usuario = new Usuario();
            usuario.setId(doc.getId() != null ? Integer.parseInt(doc.getId()) : 0);
            usuario.setNombre(doc.getString(CAMPO_NOMBRE));
            usuario.setApellido1(doc.getString(CAMPO_APELLIDO1));
            usuario.setApellido2(doc.getString(CAMPO_APELLIDO2));
            usuario.setCorreo(doc.getString(CAMPO_CORREO));
            usuario.setContraseña(doc.getString(CAMPO_CONTRASENA));

            Timestamp fechaNacTimestamp = doc.getTimestamp(CAMPO_FECHA_NAC);
            usuario.setFechaNac(fechaNacTimestamp != null ? fechaNacTimestamp.toDate() : null);

            Double nivelDouble = doc.getDouble(CAMPO_NIVEL);
            usuario.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);
            listaUsuarios.add(usuario);
        }

        // Workouts
        ApiFuture<QuerySnapshot> consultaWorkouts = baseDatos.collection(COLECCION_WORKOUTS).get();
        List<QueryDocumentSnapshot> documentosWorkouts = consultaWorkouts.get().getDocuments();

        for (QueryDocumentSnapshot docWorkout : documentosWorkouts) {
            Workout workout = new Workout();
            workout.setId(docWorkout.getId());
            workout.setNombre(docWorkout.getString(CAMPO_NOMBRE));

            Double numEjerciciosDouble = docWorkout.getDouble(CAMPO_NUM_EJERCICIOS);
            workout.setNum_ejercicios(numEjerciciosDouble != null ? numEjerciciosDouble.intValue() : 0);

            Double nivelDouble = docWorkout.getDouble(CAMPO_NIVEL);
            workout.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);

            workout.setVideo(docWorkout.getString(CAMPO_VIDEO));
            listaWorkouts.add(workout);

            // Ejercicios
            ApiFuture<QuerySnapshot> consultaEjercicios = baseDatos.collection(COLECCION_WORKOUTS)
                    .document(docWorkout.getId())
                    .collection(SUBCOLECCION_EJERCICIOS)
                    .get();
            List<QueryDocumentSnapshot> documentosEjercicios = consultaEjercicios.get().getDocuments();

            for (QueryDocumentSnapshot docEjercicio : documentosEjercicios) {
                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setId(Integer.parseInt(docEjercicio.getId()));
                ejercicio.setNombre(docEjercicio.getString(CAMPO_NOMBRE));

                Double numSeriesDouble = docEjercicio.getDouble(CAMPO_NUM_SERIES);
                ejercicio.setNumSeries(numSeriesDouble != null ? numSeriesDouble.intValue() : 0);

                Double descansoDouble = docEjercicio.getDouble(CAMPO_DESCANSO);
                ejercicio.setDescanso(descansoDouble != null ? descansoDouble.intValue() : 0);

                ejercicio.setFoto(docEjercicio.getString(CAMPO_FOTO));
                listaWorkouts.add(ejercicio);

                // Series
                ApiFuture<QuerySnapshot> consultaSeries = baseDatos.collection(COLECCION_WORKOUTS)
                        .document(docWorkout.getId())
                        .collection(SUBCOLECCION_EJERCICIOS)
                        .document(docEjercicio.getId())
                        .collection(SUBCOLECCION_SERIES)
                        .get();
                List<QueryDocumentSnapshot> documentosSeries = consultaSeries.get().getDocuments();

                for (QueryDocumentSnapshot docSerie : documentosSeries) {
                    Series serie = new Series();
                    serie.setId(docSerie.getId());

                    Double duracionDouble = docSerie.getDouble(CAMPO_DURACION);
                    serie.setDuracion(duracionDouble != null ? duracionDouble.intValue() : 0);

                    Double repeticionesDouble = docSerie.getDouble(CAMPO_REPETICIONES);
                    serie.setRepeticiones(repeticionesDouble != null ? repeticionesDouble.intValue() : 0);

                    listaWorkouts.add(serie);
                }
            }
        }

        try (ObjectOutputStream oosUsuarios = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oosUsuarios.writeObject(listaUsuarios);
        }

        try (ObjectOutputStream oosWorkouts = new ObjectOutputStream(new FileOutputStream(ARCHIVO_WORKOUTS))) {
            oosWorkouts.writeObject(listaWorkouts);
        }

        baseDatos.close();
        
    }
}