package Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;





public class Gestor {
	 Usuario datos = new Usuario();
	ArrayList<Workout> workouts = new ArrayList<>();
	Workout workoutAnadir = new Workout();
	ArrayList<Ejercicio> ejercicios = new ArrayList<>();
	
	// Devolver el usuario actualmente logueado (puede ser vacío si no hay sesión)
	public Usuario getDatos() {
		return datos;
	}
	
	public boolean inicioSesion(Usuario usuario) throws Exception {
		FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("fitup-8e726")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();
	
		ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
		QuerySnapshot querySnapShot = query.get();
        List<QueryDocumentSnapshot> usuarios = querySnapShot.getDocuments();
        
        for (QueryDocumentSnapshot usu : usuarios) {
			   	 
				if(usuario.getCorreo().equals(usu.getString("correo")) && usuario.getContraseña().equals(usu.getString("contraseña"))) {
					
					datos.setNombre(usu.getString("nombre"));
					datos.setContraseña(usu.getString("contraseña"));
					datos.setId(Integer.parseInt(usu.getId()));
					datos.setApellido1(usu.getString("apellido1"));
					datos.setApellido2(usu.getString("apellido2"));
					datos.setCorreo(usu.getString("correo"));
					datos.setNivel(usu.getDouble("nivel").intValue());

			
					return true;
				}
			}
	        
			 db.close();
			 return false;
			 
	}
	
	

	public ArrayList<Workout> listarworkouts() throws Exception {

	    // Crear una lista local para evitar acumular duplicados en la lista de instancia
	    ArrayList<Workout> resultado = new ArrayList<>();

	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
	        .toBuilder()
	        .setProjectId("fitup-8e726")
	        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	        .build();
	    Firestore db = firestoreOptions.getService();

	    ApiFuture<QuerySnapshot> query = db.collection("workouts")
	        .whereEqualTo("nivel", datos.getNivel())
	        .get();

	    QuerySnapshot querySnapShot = query.get();
	    List<QueryDocumentSnapshot> workouts1 = querySnapShot.getDocuments();

	    for (QueryDocumentSnapshot worko : workouts1) {
	        Workout workoutAnadir = new Workout();


	            
	            workoutAnadir.setId(worko.getId());
	            workoutAnadir.setNivel(worko.getDouble("nivel").intValue());
	            workoutAnadir.setNombre(worko.getString("nombre"));
	            workoutAnadir.setNumEjercicios(worko.getDouble("num_ejercicios").intValue());
	            workoutAnadir.setURL(worko.getString("video"));

	            resultado.add(workoutAnadir);
	    }

	    db.close();

	    return resultado;
	}

	
	public void nuevoUsuario(Usuario usuario) throws Exception {
	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId("fitup-8e726")
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
	    Firestore db = firestoreOptions.getService();

	    CollectionReference usus = db.collection("usuarios");
	    
	    ApiFuture<QuerySnapshot> query = usus.get();
	    List<QueryDocumentSnapshot> documentos = query.get().getDocuments();
	    
        	// Obtener el documento con mayor ID
    	    int nuevoId = 100; 
    	    for (QueryDocumentSnapshot doc : documentos) {
    	        int idActual = Integer.parseInt(doc.getId());
    	        if (idActual >= nuevoId) {
    	            nuevoId = idActual + 100; 
    	        }
    	    }

    	    
    	    DocumentReference usuNew = usus.document(String.valueOf(nuevoId));

    	    Map<String, Object> usuMap = new HashMap<>();
    	    usuMap.put("nombre", usuario.getNombre());
    	    usuMap.put("apellido1", usuario.getApellido1());
    	    usuMap.put("apellido2", usuario.getApellido2());
    		usuMap.put("correo", usuario.getCorreo());
    	    usuMap.put("contraseña", usuario.getContraseña());
    	    usuMap.put("nivel", usuario.getNivel());
    	    usuMap.put("fechaNac", usuario.getFechaNac());

    	    usuNew.set(usuMap); 
    	    db.close();

	}
	
	public boolean correoExiste(String correo) throws Exception {
	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId("fitup-8e726")
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
	    Firestore db = firestoreOptions.getService();

	    CollectionReference usus = db.collection("usuarios");
	    ApiFuture<QuerySnapshot> query = usus.whereEqualTo("correo", correo).get();

	    boolean existe = !query.get().isEmpty();
	    db.close();
	    return existe;
	}





	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception {
	    ArrayList<Ejercicio> ejercicios = new ArrayList<>();

	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
	        .toBuilder()
	        .setProjectId("fitup-8e726")
	        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	        .build();

	    Firestore db = firestoreOptions.getService();

	    ApiFuture<QuerySnapshot> future = db.collection("workouts")
	        .document(idEjercicio)
	        .collection("ejercicios")
	        .get();

	    QuerySnapshot querySnapshot = future.get();
	    List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();

	    for (QueryDocumentSnapshot doc : documentos) {
	        Ejercicio ejercicioAnadir = new Ejercicio();
	        ejercicioAnadir.setId(Integer.parseInt(doc.getId()));
	        ejercicioAnadir.setNombre(doc.getString("nombre"));
	        ejercicioAnadir.setNumSeries(doc.getDouble("num_series").intValue());
	        ejercicioAnadir.setDescanso(doc.getDouble("descanso").intValue());


	        ejercicios.add(ejercicioAnadir);
	    }

	    db.close();
	    return ejercicios;
	}



	//MOdificar usuario
	public void modificarUsuario(Usuario usuario) throws Exception {
		 FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
		            .setProjectId("fitup-8e726")
		            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		    Firestore db = firestoreOptions.getService();

		    CollectionReference usus = db.collection("usuarios");
		    // Determinar correo para buscar el documento: preferir el correo proporcionado en el objeto
		    String correoBuscado = usuario.getCorreo();
		    if (correoBuscado == null || correoBuscado.isEmpty()) {
		        // si no se proporciona correo, intentar usar el correo del usuario logueado en este gestor
		        correoBuscado = datos.getCorreo();
		    }
		    if (correoBuscado == null || correoBuscado.isEmpty()) {
		        // Nada que hacer si no hay correo para identificar el documento
		        db.close();
		        return;
		    }

		    ApiFuture<QuerySnapshot> queryFuture = usus.whereEqualTo("correo", correoBuscado).get();
		    List<QueryDocumentSnapshot> encontrados = queryFuture.get().getDocuments();
		    if (encontrados.isEmpty()) {
		        // No existe el usuario; cerrar y salir
		        db.close();
		        return;
		    }

		    for (QueryDocumentSnapshot doc : encontrados) {
		        DocumentReference docRef = usus.document(doc.getId());
		        Map<String, Object> updates = new HashMap<>();
		        // Solo añadir claves si vienen con valor no vacío / no nulo
		        if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
		            updates.put("nombre", usuario.getNombre());
		        }
		        if (usuario.getApellido1() != null && !usuario.getApellido1().isEmpty()) {
		            updates.put("apellido1", usuario.getApellido1());
		        }
		        if (usuario.getApellido2() != null && !usuario.getApellido2().isEmpty()) {
		            updates.put("apellido2", usuario.getApellido2());
		        }
		        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
		            updates.put("contraseña", usuario.getContraseña());
		        }
		        if (usuario.getFechaNac() != null && !usuario.getFechaNac().isEmpty()) {
		            updates.put("fechaNac", usuario.getFechaNac());
		        }
		        

		        if (!updates.isEmpty()) {
                    ApiFuture<com.google.cloud.firestore.WriteResult> writeResult = docRef.update(updates);
                    writeResult.get();

                    if (updates.containsKey("nombre")) {
                        datos.setNombre((String) updates.get("nombre"));
                    }
                    if (updates.containsKey("apellido1")) {
                        datos.setApellido1((String) updates.get("apellido1"));
                    }
                    if (updates.containsKey("apellido2")) {
                        datos.setApellido2((String) updates.get("apellido2"));
                    }
                    if (updates.containsKey("contraseña")) {
                        datos.setContraseña((String) updates.get("contraseña"));
                    }
                    if (updates.containsKey("fechaNac")) {
                        datos.setFechaNac((String) updates.get("fechaNac"));
                    }
                   
                        
                    
                }
		    }

		    db.close();
	}
	
	public ArrayList<Series> listarSeries( String idEjercicio) throws Exception {
	    ArrayList<Series> series = new ArrayList<>();

		
	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
	        .toBuilder()
	        .setProjectId("fitup-8e726")
	        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	        .build();

	    Firestore db = firestoreOptions.getService();

	    ApiFuture<QuerySnapshot> future = db.collection("workouts")
	        .document(idEjercicio)
	        .collection("ejercicios").document().collection("series")
	        .get();
	    
	    QuerySnapshot querySnapshot = future.get();
	    List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();

	    for (QueryDocumentSnapshot doc : documentos) {
	        Series serieAnadir = new Series();
	        serieAnadir.setId(doc.getId());
	        serieAnadir.setDuracion(Integer.parseInt(doc.getString("duracion")));
	        serieAnadir.setRepeticiones(doc.getDouble("repeticiones").intValue());

	        series.add(serieAnadir);
	        System.out.println(series);
	    }

	    db.close();
	    return series;
		    
	}
	


	
	

}