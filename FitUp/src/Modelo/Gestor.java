package Modelo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;



public class Gestor {
	 Usuario datos = new Usuario();
	ArrayList<Workout> workouts = new ArrayList<>();
	Workout workoutAnadir = new Workout();
	ArrayList<Ejercicio> ejercicios = new ArrayList<>();
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

	            workouts.add(workoutAnadir);
	       
	    }

	    db.close();
	    return workouts;
	}

	
	
	public void nuevoUsuario(Usuario usuario) throws Exception {
	    FileInputStream serviceAccount = new FileInputStream("fitUp.json");
	    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
	            .setProjectId("fitup-8e726")
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
	    Firestore db = firestoreOptions.getService();

	    CollectionReference usus = db.collection("usuarios");

	    // Obtener el documento con mayor ID
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
	        ejercicioAnadir.setId(doc.getId());
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
		    Query query = usus.whereEqualTo("correo", datos.correo);
		    ApiFuture<QuerySnapshot> query2 = usus.get();
		    

		    	/* Map<String, Object> usuMap = new HashMap<>();
		    	 if(!usuario.getNombre().isEmpty() && usuario.getCorreo().equals(query)) {
					    usuMap.put("nombre", usuario.getNombre());
		    	 }
		    	 if(!usuario.getApellido1().isEmpty() && usuario.getCorreo().equals(query)) {
					    usuMap.put("apellido1", usuario.getApellido1());
		    	 }
		    	 if(!usuario.getApellido2().isEmpty() && usuario.getCorreo().equals(query)) {
					    usuMap.put("apellido2", usuario.getApellido2());
		    	 } 
		    	 if(!usuario.getContraseña().isEmpty() && usuario.getCorreo().equals(query)) {
					    usuMap.put("contraseña", usuario.getContraseña());
		    	 }
		    	 if(!usuario.getFechaNac().isEmpty() && usuario.getCorreo().equals(query)) {
					    usuMap.put("fechaNac", usuario.getFechaNac());
		    	 }
		    
		   
		    db.close();	*/	
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
	        serieAnadir.setNombre(doc.getString("duracion"));
	        serieAnadir.setNumSeries(doc.getDouble("repeticiones").intValue());

	        series.add(serieAnadir);
	        System.out.println(series);
	    }

	    db.close();
	    return series;
		    
	}


	

}

