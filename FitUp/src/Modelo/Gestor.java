package Modelo;

import java.io.FileInputStream;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;


public class Gestor {
	static usuario datos = new usuario();
	public static boolean inicioSesion(usuario usuario) throws Exception {
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
					return true;
				}
			}
	        
			 db.close();
			 return false;
			 
	}
	


	@SuppressWarnings("null")
	public static usuario idWorkouts() {
		usuario datos2 = new usuario();
		datos2.setId(datos.getId());
		return datos2;
	}



	public static void listarworkouts ()  throws Exception {
		FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("fitup-8e726")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();
		
		//Tomar nivel del usuario
		ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
		QuerySnapshot querySnapShot = query.get();
        List<QueryDocumentSnapshot> usuarios = querySnapShot.getDocuments();
        
        for (QueryDocumentSnapshot usu : usuarios) {
		   	 
			
		}
        
        

	}



	
	
}
