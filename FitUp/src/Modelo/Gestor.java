package Modelo;

import java.awt.Color;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.*;
import java.util.*;

import javax.swing.JTextField;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;

public class Gestor {
	private static final String FIREBASE_JSON = "fitUp.json";
	private static final String FIREBASE_PROJECT_ID = "fitup-8e726";

	private static final String COLECCION_USUARIOS = "usuarios";
	private static final String COLECCION_WORKOUTS = "workouts";
	private static final String COLECCION_HISTORICO = "historicoWorkouts";
	private static final String SUBCOLECCION_EJERCICIOS = "ejercicios";
	private static final String COLECCION_SERIES = "series";


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
	private static final String CAMPO_COMPLETADO = "completado";
	private static final String CAMPO_FECHA_HIST = "fecha";
	private static final String CAMPO_TIEMPO_TOTAL = "tiempoTotal";
	private static final String CAMPO_DURACION = "duracion";




	Usuario datos = new Usuario();
	ArrayList<Workout> workouts = new ArrayList<>();
	Workout workoutAnadir = new Workout();
	ArrayList<Ejercicio> ejercicios = new ArrayList<>();
	ArrayList<Historico> historicos = new ArrayList<>();
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

	public ArrayList<Historico> listarHistorico(int idUsuario) throws Exception {
	    ArrayList<Historico> historicos = new ArrayList<>(); // Reiniciamos la lista

	    FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId(FIREBASE_PROJECT_ID)
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();
	    Firestore db = firestoreOptions.getService();

	    // Referencia al usuario
	    DocumentReference usuarioRef = db.collection(COLECCION_USUARIOS)
	                                     .document(String.valueOf(idUsuario));

	    // Solo traemos historicos de este usuario
	    ApiFuture<QuerySnapshot> futureHistorico = db.collection(COLECCION_HISTORICO)
	            .whereEqualTo("usuario", usuarioRef)
	            .get();

	    QuerySnapshot historicoSnapshot = futureHistorico.get();

	    for (QueryDocumentSnapshot historicoDoc : historicoSnapshot.getDocuments()) {
	        Historico historico = new Historico();
	        historico.setId(Integer.parseInt(historicoDoc.getId()));

	        // Completado
	        int completadoRaw = historicoDoc.contains(CAMPO_COMPLETADO) ? historicoDoc.getDouble(CAMPO_COMPLETADO).intValue() : 0;
	        historico.setCompletado(switch (completadoRaw) {
	            case 1 -> 33;
	            case 2 -> 66;
	            case 3 -> 100;
	            default -> 0;
	        });

	        // Fecha
	        Timestamp fechaTimestamp = historicoDoc.getTimestamp(CAMPO_FECHA_HIST);
	        historico.setFecha(fechaTimestamp != null ? fechaTimestamp.toDate() : null);

	        // Tiempo total
	        historico.setTiempoTotal(historicoDoc.getDouble(CAMPO_TIEMPO_TOTAL).intValue());

	        // Usuario
	        historico.setUsuario(datos); // Usuario actual en sesión

	        // Workout asociado
	        DocumentReference workoutRef = historicoDoc.get("workout", DocumentReference.class);
	        if (workoutRef != null) {
	            DocumentSnapshot workoutDoc = workoutRef.get().get();
	            if (workoutDoc.exists()) {
	                Workout workout = new Workout();
	                workout.setId(workoutDoc.getId());
	                workout.setNombre(workoutDoc.getString(CAMPO_NOMBRE));
	                workout.setNivel(workoutDoc.contains(CAMPO_NIVEL) ? workoutDoc.getDouble(CAMPO_NIVEL).intValue() : 0);
	                historico.setWorkout(workout);
	            }
	        }

	        historicos.add(historico);
	    }

	    db.close();
	    return historicos;
	}


	// Devuelve el tiempo total en segundos
	public int conseguirTiempoPrevisto(String idWorkout) throws Exception {
	    FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId(FIREBASE_PROJECT_ID)
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();
	    Firestore db = firestoreOptions.getService();

	    int tiempoPrevisto = 0;

	    ApiFuture<QuerySnapshot> futureEjercicios = db.collection(COLECCION_WORKOUTS)
	            .document(idWorkout)
	            .collection(SUBCOLECCION_EJERCICIOS)
	            .get();
	    QuerySnapshot ejerciciosSnapshot = futureEjercicios.get();

	    for (QueryDocumentSnapshot ejercicioDoc : ejerciciosSnapshot.getDocuments()) {
	        int descanso = ejercicioDoc.contains(CAMPO_DESCANSO) ? ejercicioDoc.getDouble(CAMPO_DESCANSO).intValue() : 0;
	        tiempoPrevisto += descanso;

	        ApiFuture<QuerySnapshot> futureSeries = ejercicioDoc.getReference().collection(COLECCION_SERIES).get();
	        QuerySnapshot seriesSnapshot = futureSeries.get();

	        for (QueryDocumentSnapshot serieDoc : seriesSnapshot.getDocuments()) {
	            int duracion = serieDoc.contains(CAMPO_DURACION) ? serieDoc.getDouble(CAMPO_DURACION).intValue() : 0;
	            tiempoPrevisto += duracion;
	        }
	    }

	    db.close();
	    return tiempoPrevisto;
	}



	public Ejercicio obtenerEjercicioConSeries(String idWorkout, String idEjercicio) throws Exception {
	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId("fitup-8e726")
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();
	    Firestore db = firestoreOptions.getService();

	    DocumentReference ejercicioRef = db.collection("workouts")
	            .document(idWorkout)
	            .collection("ejercicios")
	            .document(idEjercicio);

	    DocumentSnapshot doc = ejercicioRef.get().get();

	    Ejercicio ejercicio = new Ejercicio();
	    if (doc.exists()) {
	        ejercicio.setId(Integer.parseInt(doc.getId()));
	        ejercicio.setNombre(doc.getString("nombre"));
	        ejercicio.setDescanso(doc.getDouble("descanso").intValue());
	        ejercicio.setNumSeries(doc.getDouble("num_series").intValue());
	        ejercicio.setFoto(doc.contains("foto") ? doc.getString("foto") : "");

	        // Cargar las series
	        ArrayList<Series> listaSeries = new ArrayList<>();
	        ApiFuture<QuerySnapshot> futureSeries = ejercicioRef.collection("series").get();
	        QuerySnapshot seriesSnapshot = futureSeries.get();
	        for (QueryDocumentSnapshot serieDoc : seriesSnapshot.getDocuments()) {
	            Series serie = new Series();
	            serie.setId(serieDoc.getId());
	            serie.setDuracion(serieDoc.getDouble("duracion").intValue());
	            serie.setRepeticiones(serieDoc.getDouble("repeticiones").intValue());
	            listaSeries.add(serie);
	        }
	        ejercicio.setSeries(listaSeries);
	    }

	    db.close();
	    return ejercicio;
	}

	public ArrayList<Series> listarSeries(String idWorkout, String idEjercicio) throws Exception {
	    ArrayList<Series> series = new ArrayList<>();

	    FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON);
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId(FIREBASE_PROJECT_ID)
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();
	    Firestore db = firestoreOptions.getService();

	    CollectionReference colecSeries = db.collection(COLECCION_WORKOUTS)
	            .document(idWorkout)
	            .collection(SUBCOLECCION_EJERCICIOS)
	            .document(idEjercicio)
	            .collection(COLECCION_SERIES);

	    ApiFuture<QuerySnapshot> future = colecSeries.get();
	    QuerySnapshot snapshot = future.get();
	    List<QueryDocumentSnapshot> docs = snapshot.getDocuments();

	    for (QueryDocumentSnapshot d : docs) {
	        Series s = new Series();
	        if (d.contains(CAMPO_DURACION))
	            s.setDuracion(d.getDouble(CAMPO_DURACION).intValue());
	        else if (d.contains("duracion"))
	            s.setDuracion(d.getDouble("duracion").intValue());

	        if (d.contains("repeticiones"))
	            s.setRepeticiones(d.getDouble("repeticiones").intValue());

	        if (d.contains("foto")) {
	            s.setId(d.getId());
	        } else {
	            s.setId(d.getId());
	        }

	        series.add(s);
	    }

	    db.close();
	    return series;
	}

	
	public void escribirHistorico(Historico historico) throws Exception {
	    try (FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON)) {
	        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId(FIREBASE_PROJECT_ID)
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();

	        Firestore db = firestoreOptions.getService();

	        CollectionReference historicoCol = db.collection(COLECCION_HISTORICO);

	        //Obtener todos los IDs existentes de documentos
	        ApiFuture<QuerySnapshot> futureDocs = historicoCol.get();
	        List<QueryDocumentSnapshot> documentos = futureDocs.get().getDocuments();

	        int nuevoId = 100;
	        for (QueryDocumentSnapshot doc : documentos) {
	            try {
	                int idExistente = Integer.parseInt(doc.getId());
	                if (idExistente >= nuevoId) {
	                    nuevoId = idExistente + 100;
	                }
	            } catch (NumberFormatException e) {
	                
	            }
	        }

	        //Referencias
	        DocumentReference workoutRef = db.collection(COLECCION_WORKOUTS)
	                                         .document(historico.getWorkout().getId());
	        DocumentReference usuarioRef = db.collection(COLECCION_USUARIOS)
	                                         .document(String.valueOf(historico.getUsuario().getId()));

	        Map<String, Object> histMap = new HashMap<>();
	        histMap.put("id", nuevoId);
	        histMap.put("completado", historico.getCompletado());
	        histMap.put("fecha", historico.getFecha());
	        histMap.put("tiempoTotal", historico.getTiempoTotal());
	        histMap.put("workout", workoutRef);
	        histMap.put("usuario", usuarioRef);

	        //Guardar con ID único
	        historicoCol.document(String.valueOf(nuevoId)).set(histMap).get();

	        db.close();
	    }
	}


	public String cambiarNivel() throws Exception {
	    StringBuilder mensaje = new StringBuilder();
	    try (FileInputStream serviceAccount = new FileInputStream(FIREBASE_JSON)) {
	        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	                .setProjectId(FIREBASE_PROJECT_ID)
	                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                .build();
	        Firestore db = firestoreOptions.getService();

	        int nivelActual = datos.getNivel();
	        DocumentReference usuarioRef = db.collection(COLECCION_USUARIOS)
	                .document(String.valueOf(datos.getId()));

	        ApiFuture<QuerySnapshot> workoutsFuture = db.collection(COLECCION_WORKOUTS)
	                .whereEqualTo(CAMPO_NIVEL, nivelActual)
	                .get();
	        List<QueryDocumentSnapshot> workoutsNivel = workoutsFuture.get().getDocuments();
	        int totalWorkoutsNivel = workoutsNivel.size();

	        ApiFuture<QuerySnapshot> historicoFuture = db.collection(COLECCION_HISTORICO)
	                .whereEqualTo("usuario", usuarioRef)
	                .get();
	        List<QueryDocumentSnapshot> historicosUsuario = historicoFuture.get().getDocuments();

	        int workoutsCompletos = 0;

	        for (QueryDocumentSnapshot histDoc : historicosUsuario) {
	            Number completado = histDoc.getDouble("completado");
	            if (completado == null || completado.intValue() != 3) continue;

	            DocumentReference workoutRef = histDoc.get("workout", DocumentReference.class);
	            if (workoutRef == null) continue;

	            DocumentSnapshot workoutSnap = workoutRef.get().get();
	            if (!workoutSnap.exists()) continue;

	            Number nivelWorkout = workoutSnap.getDouble(CAMPO_NIVEL);
	            if (nivelWorkout != null && nivelWorkout.intValue() == nivelActual) {
	                workoutsCompletos++;
	            }
	        }

	        mensaje.append("Workouts completados del nivel ").append(nivelActual)
	                .append(": ").append(workoutsCompletos)
	                .append(" / ").append(totalWorkoutsNivel).append("\n");

	        if (workoutsCompletos >= totalWorkoutsNivel) {
	            int nuevoNivel = nivelActual + 1;
	            usuarioRef.update(CAMPO_NIVEL, nuevoNivel).get();
	            datos.setNivel(nuevoNivel);

	            mensaje.append("¡Felicidades! Has subido al nivel ").append(nuevoNivel);
	        } else {
	            mensaje.append("Aún te faltan workouts por completar para subir de nivel.");
	            return mensaje.toString();
	        }

	        db.close();
	    }

	    return mensaje.toString();
	}




	
///////////////Validaciones de campos y exportar datos/////////////////////////////////////////////////////////
	public boolean exportarDatos() {
		// Si no hay conexión, no intentamos exportar (evita intentos sobre la BD cuando estamos offline)
		if (!checkInternetConnection()) {
			System.out.println("No hay conexión a Internet: se omite exportarDatos().");
			return false;
		}
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


	 
	 
	 
	 //////////////////////////////////FUNCIONALIDAD OFFLINE/////////////////////////////////////////////////
	 
	 
	 public boolean checkInternetConnection() {
		    boolean status = false;
		    Socket sock = new Socket();
		    InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
		    try {
		        sock.connect(address, 3000); // Tiempo de espera de 3 segundos
		        if (sock.isConnected()) {
		            status = true;
		        }
		    } catch (Exception e) {
		        status = false;
		    } finally {
		        try {
		            sock.close();
		        } catch (Exception e) {
		            // Ignorar errores al cerrar
		        }
		    }
		    return status;
		}   
	 
	 
	 public boolean inicioSesionOffline(Usuario usuario) throws Exception {
        ArrayList<Usuario> listaUsuarios = readListFromFile(getUsuariosPath(), Usuario.class);
        for (Usuario usu : listaUsuarios) {
            if (usuario.getCorreo().equals(usu.getCorreo()) && usuario.getContraseña().equals(usu.getContraseña())) {
                datos = usu;
                return true;
            }
        }
        return false;
    }


    public ArrayList<Workout> listarworkoutsOffline() throws Exception {
        ArrayList<Workout> resultado = new ArrayList<>();
        ArrayList<Workout> listaWorkouts = readListFromFile(getWorkoutsPath(), Workout.class);
        for (Workout w : listaWorkouts) {
            if (w != null && w.getNivel() <= datos.getNivel()) resultado.add(w);
        }
        return resultado;
    }

    public void nuevoUsuarioOffline(Usuario usuario) throws Exception {
        ArrayList<Usuario> listaUsuarios = readListFromFile(getUsuariosPath(), Usuario.class);

        int nuevoId = 100;
        for (Usuario u : listaUsuarios) {
            if (u.getId() >= nuevoId) nuevoId = u.getId() + 100;
        }

        usuario.setId(nuevoId);
        listaUsuarios.add(usuario);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUsuariosPath()))) {
            oos.writeObject(listaUsuarios);
        }
    }

    public boolean correoExisteOffline(String correo) throws Exception {
        ArrayList<Usuario> listaUsuarios = readListFromFile(getUsuariosPath(), Usuario.class);
        for (Usuario usu : listaUsuarios) {
            if (usu.getCorreo() != null && usu.getCorreo().equalsIgnoreCase(correo)) return true;
        }
        return false;
    }

    public ArrayList<Historico> listarHistoricoOffline(int idUsuario) throws Exception {
        ArrayList<Historico> historicos = new ArrayList<>();

        File xmlFile = new File(getHistoricoPath());
        if (!xmlFile.exists()) return historicos;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList lista = doc.getElementsByTagName("historico");

        ArrayList<Usuario> usuariosList = readListFromFile(getUsuariosPath(), Usuario.class);
        ArrayList<Workout> workoutsList = readListFromFile(getWorkoutsPath(), Workout.class);

        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodo;

                String usuarioIdStr = null;
                if (e.getElementsByTagName("usuarioId").getLength() > 0)
                    usuarioIdStr = e.getElementsByTagName("usuarioId").item(0).getTextContent();
                else if (e.getElementsByTagName("usuario").getLength() > 0)
                    usuarioIdStr = e.getElementsByTagName("usuario").item(0).getTextContent();

                int usuarioId = -1;
                if (usuarioIdStr != null) {
                    try { usuarioId = Integer.parseInt(usuarioIdStr); }
                    catch (NumberFormatException nfe) {
                        for (Usuario u : usuariosList) {
                            if (usuarioIdStr.equalsIgnoreCase(u.getNombre()) || usuarioIdStr.equalsIgnoreCase(u.getCorreo())) { usuarioId = u.getId(); break; }
                        }
                        if (usuarioId == -1 && datos != null && usuarioIdStr.equalsIgnoreCase(datos.getNombre())) usuarioId = datos.getId();
                    }
                }

                if (usuarioId != idUsuario) continue;

                Historico h = new Historico();

                if (e.getAttribute("id") != null && !e.getAttribute("id").isEmpty()) {
                    try { h.setId(Integer.parseInt(e.getAttribute("id"))); } catch (NumberFormatException ex) { }
                } else {
                    h.setId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
                }

                if (e.getElementsByTagName("completado").getLength() > 0) {
                    try { h.setCompletado(Integer.parseInt(e.getElementsByTagName("completado").item(0).getTextContent())); } catch (Exception ex) { h.setCompletado(0); }
                }

                if (e.getElementsByTagName("tiempoTotal").getLength() > 0) {
                    try { h.setTiempoTotal(Integer.parseInt(e.getElementsByTagName("tiempoTotal").item(0).getTextContent())); } catch (Exception ex) { h.setTiempoTotal(0); }
                }

                if (e.getElementsByTagName("fecha").getLength() > 0) {
                    try { h.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(e.getElementsByTagName("fecha").item(0).getTextContent())); } catch (Exception ex) { h.setFecha(null); }
                }

                String workoutIdStr = null;
                if (e.getElementsByTagName("workoutId").getLength() > 0) workoutIdStr = e.getElementsByTagName("workoutId").item(0).getTextContent();
                else if (e.getElementsByTagName("workout").getLength() > 0) workoutIdStr = e.getElementsByTagName("workout").item(0).getTextContent();

                Workout w = new Workout();
                if (workoutIdStr != null) {
                    boolean assigned = false;
                    for (Workout ww : workoutsList) {
                        if (workoutIdStr.equals(ww.getId()) || workoutIdStr.equalsIgnoreCase(ww.getNombre())) { w.setId(ww.getId()); w.setNombre(ww.getNombre()); w.setNivel(ww.getNivel()); assigned = true; break; }
                    }
                    if (!assigned) w.setId(workoutIdStr);
                }
                h.setWorkout(w);

                Usuario uobj = new Usuario(); uobj.setId(idUsuario); h.setUsuario(uobj);
                historicos.add(h);
            }
        }

        return historicos;
    }

    public ArrayList<Ejercicio> listarEjerciciosOffline(String idEjercicio) {
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();
        ArrayList<Workout> listaWorkouts = readListFromFile(getWorkoutsPath(), Workout.class);
        for (Workout w : listaWorkouts) {
            if (w != null && w.getId() != null && w.getId().equals(idEjercicio)) {
                return w.getEjercicio() != null ? new ArrayList<>(w.getEjercicio()) : new ArrayList<>();
            }
        }
        return ejercicios;
    }

    public int conseguirTiempoPrevistoOffline(String idWorkout) {
        int tiempoPrevisto = 0;
        ArrayList<Workout> listaWorkouts = readListFromFile(getWorkoutsPath(), Workout.class);
        for (Workout w : listaWorkouts) {
            if (w != null && w.getId() != null && w.getId().equals(idWorkout)) {
                if (w.getEjercicio() == null) break;
                for (Ejercicio e : w.getEjercicio()) {
                    tiempoPrevisto += e.getDescanso();
                    if (e.getSeries() == null) continue;
                    for (Series s : e.getSeries()) tiempoPrevisto += s.getDuracion();
                }
                break;
            }
        }
        return tiempoPrevisto;
    }

    public ArrayList<Series> listarSeriesOffline(String idWorkout, String idEjercicio) {
        ArrayList<Series> resultado = new ArrayList<>();
        ArrayList<Workout> listaWorkouts = readListFromFile(getWorkoutsPath(), Workout.class);
        for (Workout w : listaWorkouts) {
            if (w != null && w.getId() != null && w.getId().equals(idWorkout)) {
                if (w.getEjercicio() == null) break;
                for (Ejercicio e : w.getEjercicio()) {
                    if (String.valueOf(e.getId()).equals(idEjercicio)) return e.getSeries() != null ? new ArrayList<>(e.getSeries()) : new ArrayList<>();
                }
            }
        }
        return resultado;
    }

    public void modificarUsuariOffline(Usuario usuario) throws Exception {
        ArrayList<Usuario> listaUsuarios = readListFromFile(getUsuariosPath(), Usuario.class);
        for (Usuario u : listaUsuarios) {
            if (u.getCorreo() != null && datos.getCorreo() != null && u.getCorreo().equalsIgnoreCase(datos.getCorreo())) {
                if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) u.setNombre(usuario.getNombre());
                if (usuario.getApellido1() != null && !usuario.getApellido1().isEmpty()) u.setApellido1(usuario.getApellido1());
                if (usuario.getApellido2() != null && !usuario.getApellido2().isEmpty()) u.setApellido2(usuario.getApellido2());
                if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) u.setContraseña(usuario.getContraseña());
                if (usuario.getFechaNac() != null) u.setFechaNac(usuario.getFechaNac());
                break;
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUsuariosPath()))) { oos.writeObject(listaUsuarios); }
    }

    public String cambiarNivelOffline() throws Exception {
        StringBuilder mensaje = new StringBuilder();
        ArrayList<Workout> listaWorkouts = readListFromFile(getWorkoutsPath(), Workout.class);
        ArrayList<Historico> listaHistoricos = listarHistoricoOffline(datos.getId());

        int nivelActual = datos.getNivel();
        int totalWorkoutsNivel = 0;
        int workoutsCompletos = 0;
        for (Workout w : listaWorkouts) {
            if (w.getNivel() == nivelActual) {
                totalWorkoutsNivel++;
                for (Historico h : listaHistoricos) {
                    if (h.getWorkout() != null && h.getWorkout().getId() != null && h.getWorkout().getId().equals(w.getId()) && h.getCompletado() == 3) {
                        workoutsCompletos++; break;
                    }
                }
            }
        }

        mensaje.append("Workouts completados del nivel ").append(nivelActual).append(": ").append(workoutsCompletos).append(" / ").append(totalWorkoutsNivel).append("\n");

        if (totalWorkoutsNivel > 0 && workoutsCompletos >= totalWorkoutsNivel) {
            int nuevoNivel = nivelActual + 1;
            try {
                ArrayList<Usuario> listaUsuarios = readListFromFile(getUsuariosPath(), Usuario.class);
                for (Usuario u : listaUsuarios) { if (u.getId() == datos.getId()) { u.setNivel(nuevoNivel); break; } }
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUsuariosPath()))) { oos.writeObject(listaUsuarios); }
            } catch (Exception ex) { ex.printStackTrace(); }
            datos.setNivel(nuevoNivel);
            mensaje.append("¡Felicidades! Has subido al nivel ").append(nuevoNivel);
        } else {
            mensaje.append("Aún te faltan workouts por completar para subir de nivel.");
        }

        return mensaje.toString();
    }

    // Overloads to ensure compatibility with different call sites
    public ArrayList<Historico> listarHistoricoOffline() throws Exception {
        if (datos != null) return listarHistoricoOffline(datos.getId());
        return new ArrayList<>();
    }

    public ArrayList<Historico> listarHistoricoOffline(Integer idUsuario) throws Exception {
        if (idUsuario == null) return new ArrayList<>();
        return listarHistoricoOffline(idUsuario.intValue());
    }

    // Helper methods to prefer files in backups/ when available
    private String getUsuariosPath() {
        File f = new File("backups/usuarios.dat");
        if (f.exists()) return f.getPath();
        return "usuarios.dat";
    }

    private String getWorkoutsPath() {
        File f = new File("backups/workouts.dat");
        if (f.exists()) return f.getPath();
        // also check backups folder without extension
        f = new File("workouts.dat");
        if (f.exists()) return f.getPath();
        return "workouts.dat"; // fallback
    }

    private String getHistoricoPath() {
        File f = new File("backups/historicoWorkouts.xml");
        if (f.exists()) return f.getPath();
        f = new File("historico.xml");
        if (f.exists()) return f.getPath();
        return "historico.xml";
    }

    // Generic safe reader for ArrayList<T> from a file using ObjectInputStream.
    // It checks runtime types and only returns items that are instances of the requested class.
    private <T> ArrayList<T> readListFromFile(String path, Class<T> cls) {
        ArrayList<T> result = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return result;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                for (Object item : (ArrayList<?>) obj) {
                    if (cls.isInstance(item)) {
                        result.add(cls.cast(item));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Log and return empty list — caller handles absence
            e.printStackTrace();
        }
        return result;
    }

    public void escribirHistoricoOffline(Historico historico) throws Exception {
        File xmlFile = new File(getHistoricoPath());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;

        if (xmlFile.exists()) {
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        } else {
            doc = dBuilder.newDocument();
            Element root = doc.createElement("historicos");
            doc.appendChild(root);
        }

        Element root = doc.getDocumentElement();
        String rootName = root.getNodeName();

        Element nuevoHist = doc.createElement("historico");

        if ("historicoWorkouts".equals(rootName)) {
            // legacy format: usuario, workout, fecha, tiempoTotal, completado
            Element usuarioElem = doc.createElement("usuario");
            Usuario u = historico.getUsuario();
            String usuarioText = (u != null && u.getNombre() != null && !u.getNombre().isEmpty()) ? u.getNombre()
                    : (u != null ? String.valueOf(u.getId()) : "");
            usuarioElem.setTextContent(usuarioText);
            nuevoHist.appendChild(usuarioElem);

            Element workoutElem = doc.createElement("workout");
            String workoutText = (historico.getWorkout() != null && historico.getWorkout().getNombre() != null && !historico.getWorkout().getNombre().isEmpty())
                    ? historico.getWorkout().getNombre() : (historico.getWorkout() != null ? historico.getWorkout().getId() : "");
            workoutElem.setTextContent(workoutText);
            nuevoHist.appendChild(workoutElem);

            Element fechaElem = doc.createElement("fecha");
            fechaElem.setTextContent(new SimpleDateFormat("dd/MM/yyyy").format(historico.getFecha()));
            nuevoHist.appendChild(fechaElem);

            Element tiempoElem = doc.createElement("tiempoTotal");
            tiempoElem.setTextContent(String.valueOf(historico.getTiempoTotal()));
            nuevoHist.appendChild(tiempoElem);

            Element completadoElem = doc.createElement("completado");
            completadoElem.setTextContent(String.valueOf(historico.getCompletado()));
            nuevoHist.appendChild(completadoElem);

            root.appendChild(nuevoHist);
        } else {
            // structured format with ids
            nuevoHist.setAttribute("id", String.valueOf(historico.getId()));

            Element usuarioId = doc.createElement("usuarioId");
            usuarioId.setTextContent(String.valueOf(historico.getUsuario().getId()));
            nuevoHist.appendChild(usuarioId);

            Element workoutId = doc.createElement("workoutId");
            workoutId.setTextContent(historico.getWorkout().getId());
            nuevoHist.appendChild(workoutId);

            Element completado = doc.createElement("completado");
            completado.setTextContent(String.valueOf(historico.getCompletado()));
            nuevoHist.appendChild(completado);

            Element tiempoTotal = doc.createElement("tiempoTotal");
            tiempoTotal.setTextContent(String.valueOf(historico.getTiempoTotal()));
            nuevoHist.appendChild(tiempoTotal);

            Element fecha = doc.createElement("fecha");
            fecha.setTextContent(new SimpleDateFormat("dd/MM/yyyy").format(historico.getFecha()));
            nuevoHist.appendChild(fecha);

            root.appendChild(nuevoHist);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }
}
