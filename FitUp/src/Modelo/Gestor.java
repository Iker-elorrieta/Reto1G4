package Modelo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;


public class Gestor {
	 usuario datos = new usuario();
	ArrayList<workout> workouts = new ArrayList<>();
	public boolean inicioSesion(usuario usuario) throws Exception {
		FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("fitup-8e726")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();
	
		ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
		QuerySnapshot querySnapShot = query.get();
        List<QueryDocumentSnapshot> usuarios = querySnapShot.getDocuments();
        
        for (QueryDocumentSnapshot usu : usuarios) {
			   	 
				if(usuario.getNombre().equals(usu.getString("nombre")) && usuario.getContraseña().equals(usu.getString("contraseña"))) {
					
					datos.setNombre(usu.getString("nombre"));
					datos.setContraseña(usu.getString("contraseña"));
					datos.setId(Integer.parseInt(usu.getId()));
					datos.setApellido1(usu.getString("apellido1"));
					datos.setApellido2(usu.getString("apellido2"));
					datos.setCorreo(usu.getString("correo"));
					idWorkouts();
					listarworkouts();
					return true;
				}
			}
	        
			 db.close();
			 return false;
			 
	}
	
	public usuario idWorkouts() {
		usuario datos2 = new usuario();
		datos2.setId(datos.getId());
		datos2.setNivel(datos.getNivel());
		return datos2;
	}


	public ArrayList listarworkouts()  throws Exception {
	
		FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("fitup-8e726")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();
		
		//Tomar nivel del usuario
		ApiFuture<QuerySnapshot> query = db.collection("workouts").get();
		QuerySnapshot querySnapShot = query.get();
        List<QueryDocumentSnapshot> workouts1 = querySnapShot.getDocuments();
        
    	usuario w = idWorkouts();

        for (QueryDocumentSnapshot worko : workouts1) {
    		
        	if(w.getNivel()==(worko.getDouble("nivel").intValue())){
			
        		workout workoutAnadir = new workout();
        		workoutAnadir.setNivel(worko.getDouble("nivel").intValue());
        		workoutAnadir.setNombre(worko.getString("nombre"));
        		workoutAnadir.setNumEjercicios(worko.getDouble("num_ejercicios").intValue());
        		workoutAnadir.setURL(worko.getString("video"));
        		workouts.add(workoutAnadir);
        		System.out.println(workouts);
        	}
        }
    	return workouts;
	}
	
	
	public void nuevoUsuario(usuario usuario) throws Exception {
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

	



}
