package Controlador;

import java.util.ArrayList;

import Modelo.*;

public class Controlador {
	Gestor gestor1 = new Gestor();

	public boolean inicioSesion(Usuario usuario) throws Exception {

		return gestor1.inicioSesion(usuario);

	}


	public ArrayList<Workout> listarWorkouts() throws Exception {
		return gestor1.listarworkouts();
	}
	
	public void nuevoUsuario(Usuario usuario) throws Exception {
		Gestor gestor = new Gestor();
		gestor.nuevoUsuario(usuario);
		
	}


	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception {
		return gestor1.listarEjercicios(idEjercicio);
	}

	public ArrayList<Series> listarSeries(String idEjercicio) throws Exception {
		return gestor1.listarSeries(idEjercicio);
	}
	public void modificarUsuario(Usuario usuario) throws Exception {
		Gestor gestor = new Gestor();
		gestor.modificarUsuario(usuario);		
	}

}
