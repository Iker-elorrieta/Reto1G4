package Controlador;

import java.util.ArrayList;

import Backup.*;
import Modelo.*;

public class Controlador {
	Gestor gestor1 = new Gestor();
	ExportarDatos backup = new ExportarDatos();

	public boolean inicioSesion(Usuario usuario) throws Exception {

		return gestor1.inicioSesion(usuario);

	}

	// Devolver el usuario actualmente en sesión (puede ser vacío si no hay sesión)
	public Usuario getUsuarioActual() {
		return gestor1.getDatos();
	}

	public ArrayList<Workout> listarWorkouts() throws Exception {
		return gestor1.listarworkouts();
	}
	
	public void nuevoUsuario(Usuario usuario) throws Exception {
			gestor1.nuevoUsuario(usuario);
		
	}


	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception {
		return gestor1.listarEjercicios(idEjercicio);
	}

	public ArrayList<Series> listarSeries(String idEjercicio) throws Exception {
		return gestor1.listarSeries(idEjercicio);
	}
	public void modificarUsuario(Usuario usuario) throws Exception {
			gestor1.modificarUsuario(usuario);		
	}

	public boolean ejecutarExportacion() {
	    try {
	        String javaHome = System.getProperty("java.home") + "\\bin\\java.exe";
	        String classpath = System.getProperty("java.class.path");
	        String clase = "Backup.ExportarDatos"; 
	        ProcessBuilder pb = new ProcessBuilder(
	            "cmd.exe", "/c", javaHome, "-cp", classpath, clase
	        );
	        Process proceso = pb.start();
	        int exitCode = proceso.waitFor();

	        if (exitCode == 0) {
	        	return true;
	        } else {
	        	return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}


	
}