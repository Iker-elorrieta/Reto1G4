package Backup;

import java.io.File;
import java.io.FileInputStream;
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

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import Modelo.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearXML {

    public void generarXML() throws Exception {

        // Inicializar Firestore
        FileInputStream serviceAccount = new FileInputStream("fitUp.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId("fitup-8e726")
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        Firestore db = firestoreOptions.getService();

        List<Historico> datosParaGuardar = new ArrayList<>();

        // Obtener todos los históricos
        ApiFuture<QuerySnapshot> queryHistorico = db.collection("historicoWorkouts").get();
        List<QueryDocumentSnapshot> historicoDocs = queryHistorico.get().getDocuments();

        for (QueryDocumentSnapshot doc : historicoDocs) {
            Historico h = new Historico();

            // Campos simples
            h.setId(doc.getId() != null ? Integer.parseInt(doc.getId()) : 0);
            h.setCompletado(doc.getLong("completado") != null ? doc.getLong("completado").intValue() : 0);
            Timestamp timestamp = doc.getTimestamp("fecha");
            if (timestamp != null) {
                h.setFecha(timestamp.toDate());
            }

            h.setTiempoTotal(doc.getLong("tiempoTotal") != null ? doc.getLong("tiempoTotal").intValue() : 0);

            // Obtener usuario como objeto
            DocumentReference usuarioRef = doc.get("usuario", DocumentReference.class);
            if (usuarioRef != null) {
                DocumentSnapshot usuarioDoc = usuarioRef.get().get();
                if (usuarioDoc.exists()) {
                    Usuario usuarioObj = usuarioDoc.toObject(Usuario.class);
                    h.setUsuario(usuarioObj);
                }
            }

            DocumentReference workoutRef = doc.get("workout", DocumentReference.class);
            if (workoutRef != null) {
                DocumentSnapshot workoutDoc = workoutRef.get().get();
                if (workoutDoc.exists()) {
                    Workout workoutObj = workoutDoc.toObject(Workout.class);
                    h.setWorkout(workoutObj);
                }
            }


            datosParaGuardar.add(h);
            db.close();
        }

        // --- Crear XML ---
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dBuilder.newDocument();

        // Elemento raíz
        Element elementoRaiz = xmlDoc.createElement("historicoWorkouts");
        xmlDoc.appendChild(elementoRaiz);

        // Agregar cada histórico al XML
        for (Historico h : datosParaGuardar) {
            Element historicoElem = xmlDoc.createElement("historico");

            // Usuario
            Element usuarioElem = xmlDoc.createElement("usuario");
            if (h.getUsuario() != null) {
                usuarioElem.appendChild(xmlDoc.createTextNode(h.getUsuario().getNombre()));
            } else {
                usuarioElem.appendChild(xmlDoc.createTextNode("Usuario no encontrado"));
            }
            historicoElem.appendChild(usuarioElem);

            // Workout
            Element workoutElem = xmlDoc.createElement("workout");
            if (h.getWorkout() != null) {
                workoutElem.appendChild(xmlDoc.createTextNode(h.getWorkout().getNombre()));
            } else {
                workoutElem.appendChild(xmlDoc.createTextNode("Workout no encontrado"));
            }
            historicoElem.appendChild(workoutElem);

            // Otros campos
            Date fecha = h.getFecha();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaStr = formato.format(fecha);
            Element fechaElem = xmlDoc.createElement("fecha");
            fechaElem.appendChild(xmlDoc.createTextNode(fechaStr));
            historicoElem.appendChild(fechaElem);

            Element tiempoElem = xmlDoc.createElement("tiempoTotal");
            tiempoElem.appendChild(xmlDoc.createTextNode(String.valueOf(h.getTiempoTotal())));
            historicoElem.appendChild(tiempoElem);

            Element completadoElem = xmlDoc.createElement("completado");
            completadoElem.appendChild(xmlDoc.createTextNode(String.valueOf(h.getCompletado())));
            historicoElem.appendChild(completadoElem);

            elementoRaiz.appendChild(historicoElem);
        }

        // Guardar XML a archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult result = new StreamResult(new File("backupHistorico.xml"));
        transformer.transform(source, result);

        System.out.println("XML generado correctamente");
    }
}
