package Backup;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    // CONSTANTES FIRESTORE
    public static final String CAMPO_COMPLETADO = "completado";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_TIEMPO_TOTAL = "tiempoTotal";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_WORKOUT = "workout";

    // CONSTANTES XML
    public static final String XML_RAIZ = "historicoWorkouts";
    public static final String XML_HISTORICO = "historico";
    public static final String XML_USUARIO = "usuario";
    public static final String XML_WORKOUT = "workout";
    public static final String XML_FECHA = "fecha";
    public static final String XML_TIEMPO_TOTAL = "tiempoTotal";
    public static final String XML_COMPLETADO = "completado";

    // CONSTANTES TEXTO
    public static final String TEXTO_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String TEXTO_WORKOUT_NO_ENCONTRADO = "Workout no encontrado";
    public static final String TEXTO_SIN_FECHA = "Sin fecha";

    // CONSTANTES FORMATO Y RUTA
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final String RUTA_BACKUP = "backups/historicoWorkouts.xml";

    // CONSTANTES FIREBASE
    public static final String CREDENCIALES_FIREBASE = "fitUp.json";
    public static final String ID_PROYECTO_FIREBASE = "fitup-8e726";

    public void generarXML() throws Exception {

        FileInputStream archivoCredenciales = new FileInputStream(CREDENCIALES_FIREBASE);
        FirestoreOptions opcionesFirestore = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(ID_PROYECTO_FIREBASE)
                .setCredentials(GoogleCredentials.fromStream(archivoCredenciales))
                .build();
        Firestore baseDatos = opcionesFirestore.getService();

        List<Historico> listaHistoricos = new ArrayList<>();

        ApiFuture<QuerySnapshot> consultaHistorico = baseDatos.collection(XML_RAIZ).get();
        List<QueryDocumentSnapshot> documentosHistorico = consultaHistorico.get().getDocuments();

        for (QueryDocumentSnapshot documento : documentosHistorico) {
            Historico historico = new Historico();

            historico.setId(documento.getId() != null ? Integer.parseInt(documento.getId()) : 0);
            historico.setCompletado(documento.getLong(CAMPO_COMPLETADO) != null ? documento.getLong(CAMPO_COMPLETADO).intValue() : 0);
            historico.setTiempoTotal(documento.getLong(CAMPO_TIEMPO_TOTAL) != null ? documento.getLong(CAMPO_TIEMPO_TOTAL).intValue() : 0);

            Timestamp marcaTiempo = documento.getTimestamp(CAMPO_FECHA);
            historico.setFecha(marcaTiempo != null ? marcaTiempo.toDate() : null);

            DocumentReference referenciaUsuario = documento.get(CAMPO_USUARIO, DocumentReference.class);
            if (referenciaUsuario != null) {
                DocumentSnapshot documentoUsuario = referenciaUsuario.get().get();
                if (documentoUsuario.exists()) {
                    Usuario usuario = documentoUsuario.toObject(Usuario.class);
                    historico.setUsuario(usuario);
                }
            }

            DocumentReference referenciaWorkout = documento.get(CAMPO_WORKOUT, DocumentReference.class);
            if (referenciaWorkout != null) {
                DocumentSnapshot documentoWorkout = referenciaWorkout.get().get();
                if (documentoWorkout.exists()) {
                    Workout workout = documentoWorkout.toObject(Workout.class);
                    historico.setWorkout(workout);
                }
            }

            listaHistoricos.add(historico);
        }

        baseDatos.close();

        DocumentBuilderFactory fabricaDocumentos = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructorDocumentos = fabricaDocumentos.newDocumentBuilder();
        Document documentoXml = constructorDocumentos.newDocument();

        Element raiz = documentoXml.createElement(XML_RAIZ);
        documentoXml.appendChild(raiz);

        for (Historico historico : listaHistoricos) {
            Element elementoHistorico = documentoXml.createElement(XML_HISTORICO);

            Element elementoUsuario = documentoXml.createElement(XML_USUARIO);
            elementoUsuario.appendChild(documentoXml.createTextNode(
                historico.getUsuario() != null ? historico.getUsuario().getNombre() : TEXTO_USUARIO_NO_ENCONTRADO
            ));
            elementoHistorico.appendChild(elementoUsuario);

            Element elementoWorkout = documentoXml.createElement(XML_WORKOUT);
            elementoWorkout.appendChild(documentoXml.createTextNode(
                historico.getWorkout() != null ? historico.getWorkout().getNombre() : TEXTO_WORKOUT_NO_ENCONTRADO
            ));
            elementoHistorico.appendChild(elementoWorkout);

            Element elementoFecha = documentoXml.createElement(XML_FECHA);
            SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
            String fechaTexto = historico.getFecha() != null ? formato.format(historico.getFecha()) : TEXTO_SIN_FECHA;
            elementoFecha.appendChild(documentoXml.createTextNode(fechaTexto));
            elementoHistorico.appendChild(elementoFecha);

            Element elementoTiempo = documentoXml.createElement(XML_TIEMPO_TOTAL);
            elementoTiempo.appendChild(documentoXml.createTextNode(String.valueOf(historico.getTiempoTotal())));
            elementoHistorico.appendChild(elementoTiempo);

            Element elementoCompletado = documentoXml.createElement(XML_COMPLETADO);
            elementoCompletado.appendChild(documentoXml.createTextNode(String.valueOf(historico.getCompletado())));
            elementoHistorico.appendChild(elementoCompletado);

            raiz.appendChild(elementoHistorico);
        }

        TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
        Transformer transformador = fabricaTransformador.newTransformer();
        transformador.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource fuente = new DOMSource(documentoXml);
        StreamResult resultado = new StreamResult(new File(RUTA_BACKUP));
        transformador.transform(fuente, resultado);
    }
}