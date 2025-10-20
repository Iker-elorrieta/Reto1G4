package Modelo;

import java.io.FileInputStream;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;





public class Gestor {
	
	public static boolean inicioSesion(String nombre, String contraseña) throws Exception {
		
		FileInputStream serviceAccount = new FileInputStream("fitUp.json");
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("fitup-8e726")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore db = firestoreOptions.getService();
	
		ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
		QuerySnapshot querySnapShot = query.get();
        List<QueryDocumentSnapshot> usuarios = querySnapShot.getDocuments();
        
        for (QueryDocumentSnapshot usu : usuarios) {
			   	 
				if(nombre.equals(usu.getString("nombre")) && contraseña.equals(usu.getString("contraseña"))) {
					return true;
				}
			}
	        
			 db.close();
			 return false;
	        
	}
}
