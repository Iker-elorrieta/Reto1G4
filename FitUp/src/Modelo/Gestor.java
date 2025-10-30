package Modelo;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;

public class Gestor {
	private static final String FIREBASE_JSON = "fitUp.json";
	private static final String FIREBASE_PROJECT_ID = "fitup-8e726";

	private static final String COLECCION_USUARIOS = "usuarios";
	private static final String COLECCION_WORKOUTS = "workouts";
	private static final String SUBCOLECCION_EJERCICIOS = "ejercicios";

	private static final String CAMPO_NOMBRE = "nombre";
	private static final String CAMPO_APELLIDO1 = "apellido1";
	private static final String CAMPO_APELLIDO2 = "apellido2";
	private static final String CAMPO_CORREO = "correo";
	private static final String CAMPO_CONTRASENA = "contraseña";
	private static final String CAMPO_NIVEL = "nivel";
	private static final String CAMPO_FECHA_NAC = "fechaNac";
	private static final String CAMPO_NUM_EJERCICIOS = "num_ejercicios";
	private static final String CAMPO_VIDEO = "video";
	private static final String CAMPO_NUM_SERIES = "num_series";
	private static final String CAMPO_DESCANSO = "descanso";

	Usuario datos = new Usuario();
	ArrayList<Workout> workouts = new ArrayList<>();
	Workout workoutAnadir = new Workout();
	ArrayList<Ejercicio> ejercicios = new ArrayList<>();

	public Usuario getDatos() {
		return datos;
	}

	public boolean inicioSesion(Usuario usuario) throws Exception {
		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();

		ApiFuture<QuerySnapshot> query = db.collection(COLECCION_USUARIOS).get();
		List<QueryDocumentSnapshot> usuarios = query.get().getDocuments();

		for (QueryDocumentSnapshot usu : usuarios) {
			if (usuario.getCorreo().equals(usu.getString(CAMPO_CORREO))
					&& usuario.getContraseña().equals(usu.getString(CAMPO_CONTRASENA))) {

				datos.setNombre(usu.getString(CAMPO_NOMBRE));
				datos.setContraseña(usu.getString(CAMPO_CONTRASENA));
				datos.setId(Integer.parseInt(usu.getId()));
				datos.setApellido1(usu.getString(CAMPO_APELLIDO1));
				datos.setApellido2(usu.getString(CAMPO_APELLIDO2));
				datos.setCorreo(usu.getString(CAMPO_CORREO));
				datos.setNivel(usu.getDouble(CAMPO_NIVEL).intValue());

				db.close();
				return true;
			}
		}

		db.close();
		return false;
	}

	public ArrayList<Workout> listarworkouts() throws Exception {
		ArrayList<Workout> resultado = new ArrayList<>();

		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();

		ApiFuture<QuerySnapshot> query = db.collection(COLECCION_WORKOUTS).get();
		List<QueryDocumentSnapshot> documents = query.get().getDocuments();

		for (QueryDocumentSnapshot doc : documents) {
			int nivelWorko = doc.getDouble(CAMPO_NIVEL).intValue();
			if (nivelWorko <= datos.getNivel()) {
				Workout workoutAnadir = new Workout();
				workoutAnadir.setId(doc.getId());
				workoutAnadir.setNivel(nivelWorko);
				workoutAnadir.setNombre(doc.getString(CAMPO_NOMBRE));
				workoutAnadir.setNum_ejercicios(doc.getDouble(CAMPO_NUM_EJERCICIOS).intValue());
				workoutAnadir.setVideo(doc.getString(CAMPO_VIDEO));
				resultado.add(workoutAnadir);
			}
		}

		db.close();
		return resultado;
	}

	public void nuevoUsuario(Usuario usuario) throws Exception {
		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();

		CollectionReference usus = db.collection(COLECCION_USUARIOS);
		ApiFuture<QuerySnapshot> query = usus.get();
		List<QueryDocumentSnapshot> documentos = query.get().getDocuments();

		int nuevoId = 100;
		for (QueryDocumentSnapshot doc : documentos) {
			int idActual = Integer.parseInt(doc.getId());
			if (idActual >= nuevoId) {
				nuevoId = idActual + 100;
			}
		}

		DocumentReference usuNew = usus.document(String.valueOf(nuevoId));
		Map<String, Object> usuMap = new HashMap<>();
		usuMap.put(CAMPO_NOMBRE, usuario.getNombre());
		usuMap.put(CAMPO_APELLIDO1, usuario.getApellido1());
		usuMap.put(CAMPO_APELLIDO2, usuario.getApellido2());
		usuMap.put(CAMPO_CORREO, usuario.getCorreo());
		usuMap.put(CAMPO_CONTRASENA, usuario.getContraseña());
		usuMap.put(CAMPO_NIVEL, usuario.getNivel());
		usuMap.put(CAMPO_FECHA_NAC, usuario.getFechaNac());

		usuNew.set(usuMap);
		db.close();
	}

	public boolean correoExiste(String correo) throws Exception {
		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();

		CollectionReference usus = db.collection(COLECCION_USUARIOS);
		ApiFuture<QuerySnapshot> query = usus.whereEqualTo(CAMPO_CORREO, correo).get();

		boolean existe = !query.get().isEmpty();
		db.close();
		return existe;
	}

	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception {
		ArrayList<Ejercicio> ejercicios = new ArrayList<>();

		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

		Firestore db = firestoreOptions.getService();

		ApiFuture<QuerySnapshot> future = db.collection(COLECCION_WORKOUTS).document(idEjercicio)
				.collection(SUBCOLECCION_EJERCICIOS).get();

		QuerySnapshot querySnapshot = future.get();
		List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();

		for (QueryDocumentSnapshot doc : documentos) {
			Ejercicio ejercicioAnadir = new Ejercicio();
			ejercicioAnadir.setId(Integer.parseInt(doc.getId()));
			ejercicioAnadir.setNombre(doc.getString(CAMPO_NOMBRE));
			ejercicioAnadir.setNumSeries(doc.getDouble(CAMPO_NUM_SERIES).intValue());
			ejercicioAnadir.setDescanso(doc.getDouble(CAMPO_DESCANSO).intValue());

			ejercicios.add(ejercicioAnadir);
		}

		db.close();
		return ejercicios;
	}

	public void modificarUsuario(Usuario usuario) throws Exception {
		FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setProjectId(FIREBASE_PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();

		CollectionReference usus = db.collection(COLECCION_USUARIOS);
		String correoBuscado = usuario.getCorreo();
		if (correoBuscado == null || correoBuscado.isEmpty()) {
			correoBuscado = datos.getCorreo();
		}
		if (correoBuscado == null || correoBuscado.isEmpty()) {
			db.close();
			return;
		}

		ApiFuture<QuerySnapshot> queryFuture = usus.whereEqualTo(CAMPO_CORREO, correoBuscado).get();
		List<QueryDocumentSnapshot> encontrados = queryFuture.get().getDocuments();
		if (encontrados.isEmpty()) {
			db.close();
			return;
		}

		for (QueryDocumentSnapshot doc : encontrados) {
			DocumentReference docRef = usus.document(doc.getId());
			Map<String, Object> updates = new HashMap<>();

			if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
				updates.put(CAMPO_NOMBRE, usuario.getNombre());
			}
			if (usuario.getApellido1() != null && !usuario.getApellido1().isEmpty()) {
				updates.put(CAMPO_APELLIDO1, usuario.getApellido1());
			}
			if (usuario.getApellido2() != null && !usuario.getApellido2().isEmpty()) {
				updates.put(CAMPO_APELLIDO2, usuario.getApellido2());
			}
			if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
				updates.put(CAMPO_CONTRASENA, usuario.getContraseña());
			}
			if (usuario.getFechaNac() != null) {
				updates.put(CAMPO_FECHA_NAC, usuario.getFechaNac());
			}

			if (!updates.isEmpty()) {
				ApiFuture<WriteResult> writeResult = docRef.update(updates);
				writeResult.get();

				if (updates.containsKey(CAMPO_NOMBRE)) {
					datos.setNombre((String) updates.get(CAMPO_NOMBRE));
				}
				if (updates.containsKey(CAMPO_APELLIDO1)) {
					datos.setApellido1((String) updates.get(CAMPO_APELLIDO1));
				}
				if (updates.containsKey(CAMPO_APELLIDO2)) {
					datos.setApellido2((String) updates.get(CAMPO_APELLIDO2));
				}
				if (updates.containsKey(CAMPO_CONTRASENA)) {
					datos.setContraseña((String) updates.get(CAMPO_CONTRASENA));
				}
				if (updates.containsKey(CAMPO_FECHA_NAC)) {
					Timestamp timestamp = (Timestamp) updates.get(CAMPO_FECHA_NAC);
					Date fecha = timestamp.toDate();
					datos.setFechaNac(fecha);
				}
			}
		}

		db.close();
	}

	public boolean exportarDatos() {
		try {
			ProcessBuilder builder = new ProcessBuilder("cmd", "/C", "java -jar backups.jar");

			Process process = builder.start();
			InputStream stream = process.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			int exitCode = process.waitFor();
			return exitCode == 0;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean soloTexto(String text) {
		if (text == null || text.isBlank())
			return false;
		for (char c : text.toCharArray()) {
			if (!Character.isLetter(c) && c != ' ')
				return false;
		}
		return true;
	}

	public boolean contraseñaValida(String contraseña) {
		if (contraseña == null || contraseña.isEmpty())
			return false;
		boolean hasUpper = false, hasDigit = false;
		for (char c : contraseña.toCharArray()) {
			if (Character.isUpperCase(c))
				hasUpper = true;
			if (Character.isDigit(c))
				hasDigit = true;
			if (hasUpper && hasDigit)
				return true;
		}
		return hasUpper && hasDigit;
	}

	 public void setPlaceholder(JTextField field, String placeholder, Color color) {
	        field.setForeground(color);
	        field.setText(placeholder);

	        field.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (field.getText().equals(placeholder)) {
	                    field.setText("");
	                    field.setForeground(Color.BLACK);
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (field.getText().isEmpty()) {
	                    field.setForeground(Color.GRAY);
	                    field.setText(placeholder);
	                }
	            }
	        });
	    }

	 public boolean fechaValida(String fecha) {
			
			  DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		        try {
		            LocalDate.parse(fecha, formato);
		            return true; 
		        } catch (DateTimeParseException e) {
		            return false; 
		        }
		}

	 public boolean correoValido(String email) {
			if (email == null || email.isBlank())
				return false;
			int atIndex = email.indexOf('@');
			int lastAtIndex = email.lastIndexOf('@');
			if (atIndex <= 0 || atIndex != lastAtIndex)
				return false;
			String localPart = email.substring(0, atIndex);
			String domainPart = email.substring(atIndex + 1);
			if (localPart.isEmpty() || domainPart.isEmpty())
				return false;
			if (!domainPart.contains(".") || domainPart.startsWith(".") || domainPart.endsWith("."))
				return false;
			if (email.contains(" "))
				return false;
			return true;
		}

	 public  boolean formatoFechaValido(String fecha) {
		    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    try {
		        LocalDate.parse(fecha, formato);
		        return true;
		    } catch (DateTimeParseException e) {
		        return false;
		    }
		}

	 public  boolean fechaNoFutura(String fecha) {
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate fechaNacimiento = LocalDate.parse(fecha, formato);
	    return !fechaNacimiento.isAfter(LocalDate.now());
	}

}

// Metodo que servira en un futuro para las series de los ejercicios

/*
 * public ArrayList<Series> listarSeries( String idEjercicio) throws Exception {
 * ArrayList<Series> series = new ArrayList<>();
 * 
 * 
 * FileInputStream serviceAccount = new FileInputStream("fitUp.json");
 * FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
 * .toBuilder() .setProjectId("fitup-8e726")
 * .setCredentials(GoogleCredentials.fromStream(serviceAccount)) .build();
 * 
 * Firestore db = firestoreOptions.getService();
 * 
 * ApiFuture<QuerySnapshot> future = db.collection("workouts")
 * .document(idEjercicio)
 * .collection("ejercicios").document().collection("series") .get();
 * 
 * QuerySnapshot querySnapshot = future.get(); List<QueryDocumentSnapshot>
 * documentos = querySnapshot.getDocuments();
 * 
 * for (QueryDocumentSnapshot doc : documentos) { Series serieAnadir = new
 * Series(); serieAnadir.setId(doc.getId());
 * serieAnadir.setDuracion(Integer.parseInt(doc.getString("duracion")));
 * serieAnadir.setRepeticiones(doc.getDouble("repeticiones").intValue());
 * 
 * series.add(serieAnadir); System.out.println(series); }
 * 
 * db.close(); return series;
 * 
 * }
 */
