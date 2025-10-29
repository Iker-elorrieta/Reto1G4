package Backup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
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

	
	 public static void main(String[] args) {
	        try {
	            ExportarDatos exportador = new ExportarDatos();
	            exportador.exportarDatos();
	            System.out.println("Exportación finalizada correctamente.");
	            
	            CrearXML xmlCreator = new CrearXML();
	            xmlCreator.generarXML();
	            System.out.println("Generación de XML finalizada correctamente.");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public void exportarDatos() throws Exception {
	        FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	                .setProjectId("fitup-8e726")
	                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                .build();
	        Firestore db = firestoreOptions.getService();

	        List<Object> datosParaGuardar = new ArrayList<>();

	        // Usuarios
	        ApiFuture<QuerySnapshot> queryUsuarios = db.collection("usuarios").get();
	        List<QueryDocumentSnapshot> usuariosDocs = queryUsuarios.get().getDocuments();
	        for (QueryDocumentSnapshot doc : usuariosDocs) {
	            Usuario u = new Usuario();
	            u.setId(doc.getId() != null ? Integer.parseInt(doc.getId()) : 0);
	            u.setNombre(doc.getString("nombre"));
	            u.setApellido1(doc.getString("apellido1"));
	            u.setApellido2(doc.getString("apellido2"));
	            u.setCorreo(doc.getString("correo"));
	            u.setContraseña(doc.getString("contraseña"));
	            Timestamp timestamp = doc.getTimestamp("fechaNac");
	            Date fecha = timestamp != null ? timestamp.toDate() : null;
	            u.setFechaNac(fecha);
	            Double nivelDouble = doc.getDouble("nivel");
	            u.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);
	            datosParaGuardar.add(u);
	        }

	        // Workouts
	        ApiFuture<QuerySnapshot> queryWorkouts = db.collection("workouts").get();
	        List<QueryDocumentSnapshot> workoutsDocs = queryWorkouts.get().getDocuments();

	        for (QueryDocumentSnapshot workoutDoc : workoutsDocs) {
	            Workout w = new Workout();
	            w.setId(workoutDoc.getId());
	            w.setNombre(workoutDoc.getString("nombre"));

	            Double numEjerciciosDouble = workoutDoc.getDouble("num_ejercicios");
	            w.setNumEjercicios(numEjerciciosDouble != null ? numEjerciciosDouble.intValue() : 0);

	            Double nivelDouble = workoutDoc.getDouble("nivel");
	            w.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);

	            w.setURL(workoutDoc.getString("video"));
	            datosParaGuardar.add(w);

	            // Ejercicios
	            ApiFuture<QuerySnapshot> queryEjercicios = db.collection("workouts")
	                    .document(workoutDoc.getId())
	                    .collection("ejercicios")
	                    .get();
	            List<QueryDocumentSnapshot> ejerciciosDocs = queryEjercicios.get().getDocuments();

	            for (QueryDocumentSnapshot ejDoc : ejerciciosDocs) {
	                Ejercicio ej = new Ejercicio();
	                ej.setId(Integer.parseInt(ejDoc.getId()));
	                ej.setNombre(ejDoc.getString("nombre"));

	                Double numSeriesDouble = ejDoc.getDouble("num_series");
	                ej.setNumSeries(numSeriesDouble != null ? numSeriesDouble.intValue() : 0);

	                Double descansoDouble = ejDoc.getDouble("descanso");
	                ej.setDescanso(descansoDouble != null ? descansoDouble.intValue() : 0);

	                ej.setFoto(ejDoc.getString("foto"));
	                datosParaGuardar.add(ej);

	                // Series
	                ApiFuture<QuerySnapshot> querySeries = db.collection("workouts")
	                        .document(workoutDoc.getId())
	                        .collection("ejercicios")
	                        .document(ejDoc.getId())
	                        .collection("series")
	                        .get();
	                List<QueryDocumentSnapshot> seriesDocs = querySeries.get().getDocuments();

	                for (QueryDocumentSnapshot sDoc : seriesDocs) {
	                    Series s = new Series();
	                    s.setId(sDoc.getId());

	                    Double duracionDouble = sDoc.getDouble("duracion");
	                    s.setDuracion(duracionDouble != null ? duracionDouble.intValue() : 0);

	                    Double repeticionesDouble = sDoc.getDouble("repeticiones");
	                    s.setRepeticiones(repeticionesDouble != null ? repeticionesDouble.intValue() : 0);

	                    datosParaGuardar.add(s);
	                }
	            }
	        }

	        // Guardar todo en archivo .dat
	        try (FileOutputStream fos = new FileOutputStream("datosFirebase.dat");
	             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
	            oos.writeObject(datosParaGuardar);
	        }

	        db.close();
	        System.out.println("Datos exportados correctamente a datosFirebase.dat");
	    }
}
