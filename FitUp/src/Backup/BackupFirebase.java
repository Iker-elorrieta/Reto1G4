package Backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


import Modelo.Historico;

import Modelo.Usuario;
import Modelo.Workout;


	    public class BackupFirebase {

	        // Campos de Usuario
	        public static final String campo_id = "id";
	        public static final String campo_nombre = "nombre";
	        public static final String campo_apellido1 = "apellido1";
	        public static final String campo_apellido2 = "apellido2";
	        public static final String campo_correo = "correo";
	        public static final String campo_contrasena = "contraseña";
	        public static final String campo_fechaNac = "fechaNac";
	        public static final String campo_nivel = "nivel";

	        // Campos de Workout
	        public static final String campo_video = "video";
	        public static final String campo_num_ejercicios = "num_ejercicios";

	        // Campos de Historico
	        public static final String campo_fecha = "fecha";
	        public static final String campo_tiempoTotal = "tiempoTotal";
	        public static final String campo_completado = "completado";
	        public static final String campo_usuario = "usuario";
	        public static final String campo_workout = "workout";

	        public static void guardarUsuarios(ArrayList<Usuario> usuarios) {
	            try {
	                File dir = new File("backups");
	                if (!dir.exists()) dir.mkdirs();
	                File f = new File("backups/usuarios.dat");
	                if (!f.exists()) f.createNewFile();
	                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
	                    if (usuarios != null) {
	                        for (Usuario usu : usuarios) {
	                            oos.writeObject(usu);
	                        }
	                    }
	                    System.out.println("Usuarios escritos correctamente");
	                }
	            } catch (IOException e) {
	                System.err.println("Usuarios no escritos");
	                e.printStackTrace();
	            }
	        }

	        public static void guardarWorkouts(ArrayList<Workout> workouts) {
	            try {
	                File dir = new File("backups");
	                if (!dir.exists()) dir.mkdirs();
	                File f = new File("backups/workouts.dat");
	                if (!f.exists()) f.createNewFile();
	                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
	                    if (workouts != null) {
	                        for (Workout wot : workouts) {
	                            oos.writeObject(wot);
	                        }
	                    }
	                    System.out.println("Workouts escritos correctamente");
	                }
	            } catch (IOException e) {
	                System.err.println("Workouts no escritos");
	                e.printStackTrace();
	            }
	        }

	        public static void guardarHistoricoWorkoutsXML() {
	            try {
	                FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	                FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	                        .setProjectId("fitup-8e726")
	                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                        .build();
	                Firestore db = firestoreOptions.getService();

	                List<Historico> historicos = new ArrayList<>();
	                ApiFuture<QuerySnapshot> queryHistorico = db.collection("historicoWorkouts").get();
	                List<QueryDocumentSnapshot> historicoDocs = queryHistorico.get().getDocuments();

	                for (QueryDocumentSnapshot doc : historicoDocs) {
	                    Historico h = new Historico();
	                    h.setId(doc.getId() != null ? Integer.parseInt(doc.getId()) : 0);
	                    h.setCompletado(doc.getLong(campo_completado) != null ? doc.getLong(campo_completado).intValue() : 0);

	                    Object fechaObj = doc.get(campo_fecha);
	                    if (fechaObj instanceof Timestamp) {
	                        h.setFecha(((Timestamp) fechaObj).toDate());
	                    } else if (fechaObj instanceof String) {
	                        try {
	                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	                            Date fecha = formato.parse((String) fechaObj);
	                            h.setFecha(fecha);
	                        } catch (ParseException e) {
	                            System.err.println("Error al parsear fecha: " + fechaObj);
	                            h.setFecha(null);
	                        }
	                    } else {
	                        h.setFecha(null);
	                    }

	                    h.setTiempoTotal(doc.getLong(campo_tiempoTotal) != null ? doc.getLong(campo_tiempoTotal).intValue() : 0);

	                    DocumentReference usuarioRef = doc.get(campo_usuario, DocumentReference.class);
	                    if (usuarioRef != null) {
	                        DocumentSnapshot usuarioDoc = usuarioRef.get().get();
	                        if (usuarioDoc.exists()) {
	                            Usuario usuarioObj = usuarioDoc.toObject(Usuario.class);
	                            h.setUsuario(usuarioObj);
	                        }
	                    }

	                    DocumentReference workoutRef = doc.get(campo_workout, DocumentReference.class);
	                    if (workoutRef != null) {
	                        DocumentSnapshot workoutDoc = workoutRef.get().get();
	                        if (workoutDoc.exists()) {
	                            Workout workoutObj = workoutDoc.toObject(Workout.class);
	                            h.setWorkout(workoutObj);
	                        }
	                    }

	                    historicos.add(h);
	                }

	                db.close();

	                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	                Document xmlDoc = dBuilder.newDocument();
	                Element rootElement = xmlDoc.createElement("historicoWorkouts");
	                xmlDoc.appendChild(rootElement);

	                for (Historico h : historicos) {
	                    Element historicoElem = xmlDoc.createElement("historico");

	                    Element usuarioElem = xmlDoc.createElement("usuario");
	                    usuarioElem.appendChild(xmlDoc.createTextNode(h.getUsuario() != null ? h.getUsuario().getNombre() : "Usuario no encontrado"));
	                    historicoElem.appendChild(usuarioElem);

	                    Element workoutElem = xmlDoc.createElement("workout");
	                    workoutElem.appendChild(xmlDoc.createTextNode(h.getWorkout() != null ? h.getWorkout().getNombre() : "Workout no encontrado"));
	                    historicoElem.appendChild(workoutElem);

	                    Element fechaElem = xmlDoc.createElement("fecha");
	                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	                    fechaElem.appendChild(xmlDoc.createTextNode(h.getFecha() != null ? formato.format(h.getFecha()) : ""));
	                    historicoElem.appendChild(fechaElem);

	                    Element tiempoElem = xmlDoc.createElement("tiempoTotal");
	                    tiempoElem.appendChild(xmlDoc.createTextNode(String.valueOf(h.getTiempoTotal())));
	                    historicoElem.appendChild(tiempoElem);

	                    Element completadoElem = xmlDoc.createElement("completado");
	                    completadoElem.appendChild(xmlDoc.createTextNode(String.valueOf(h.getCompletado())));
	                    historicoElem.appendChild(completadoElem);

	                    rootElement.appendChild(historicoElem);
	                }

	                File dir = new File("backups");
	                if (!dir.exists()) dir.mkdirs();
	                File outFile = new File(dir, "historicoWorkouts.xml");

	                TransformerFactory tf = TransformerFactory.newInstance();
	                Transformer transformer = tf.newTransformer();
	                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	                DOMSource source = new DOMSource(xmlDoc);
	                try (FileOutputStream fos = new FileOutputStream(outFile)) {
	                    StreamResult result = new StreamResult(fos);
	                    transformer.transform(source, result);
	                }

	                System.out.println("XML escrito correctamente");

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        public static void generarBackupsDesdeServidor() {
	            ArrayList<Usuario> usuarios = new ArrayList<>();
	            ArrayList<Workout> workouts = new ArrayList<>();
	            try {
	                FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	                FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	                        .setProjectId("fitup-8e726")
	                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                        .build();
	                Firestore db = firestoreOptions.getService();

	                // Obtener usuarios
	                ApiFuture<QuerySnapshot> queryUsuarios = db.collection("usuarios").get();
	                for (QueryDocumentSnapshot doc : queryUsuarios.get().getDocuments()) {
	                    Usuario u = new Usuario();
	                    u.setId(doc.getId() != null ? Integer.parseInt(doc.getId()) : 0);
	                    u.setNombre(doc.getString(campo_nombre));
	                    u.setApellido1(doc.getString(campo_apellido1));
	                    u.setApellido2(doc.getString(campo_apellido2));
	                    u.setCorreo(doc.getString(campo_correo));
	                    u.setContraseña(doc.getString(campo_contrasena));
	                    Timestamp timestamp = doc.getTimestamp(campo_fechaNac);
	                    u.setFechaNac(timestamp != null ? timestamp.toDate() : null);
	                    Double nivelDouble = doc.getDouble(campo_nivel);
	                    u.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);
	                    usuarios.add(u);
	                }

	                // Obtener workouts
	                ApiFuture<QuerySnapshot> queryWorkouts = db.collection("workouts").get();
	                List<QueryDocumentSnapshot> workoutDocs = queryWorkouts.get().getDocuments();

	                for (QueryDocumentSnapshot workoutDoc : workoutDocs) {
	                    Workout w = new Workout();
	                    w.setId(workoutDoc.getId());
	                    w.setNombre(workoutDoc.getString(campo_nombre));

	                    Double numEjerciciosDouble = workoutDoc.getDouble(campo_num_ejercicios);
	                    w.setNum_ejercicios(numEjerciciosDouble != null ? numEjerciciosDouble.intValue() : 0);

	                    Double nivelDouble = workoutDoc.getDouble(campo_nivel);
	                    w.setNivel(nivelDouble != null ? nivelDouble.intValue() : 0);

	                    w.setVideo(workoutDoc.getString(campo_video));

	                    workouts.add(w);
	                }

	                db.close();
	                System.out.println("Datos obtenidos correctamente desde Firestore.");

	                // Guardar en archivos locales
	                guardarUsuarios(usuarios);
	                guardarWorkouts(workouts);

	            } catch (Exception e) {
	                System.err.println("Error al obtener datos desde Firestore:");
	                e.printStackTrace();
	            }
	        }

	        public static void main(String[] args) {
	            generarBackupsDesdeServidor();
	            guardarHistoricoWorkoutsXML();
	        }


}
