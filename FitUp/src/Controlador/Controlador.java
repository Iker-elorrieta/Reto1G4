package Controlador;

import java.util.ArrayList;

import Modelo.Gestor;
import Modelo.ejercicio;
import Modelo.usuario;
import Modelo.workout;

public class Controlador {
	Gestor gestor1 = new Gestor();

	public boolean inicioSesion(usuario usuario) throws Exception {

		return gestor1.inicioSesion(usuario);

	}


	public ArrayList<workout> listarWorkouts() throws Exception {
		return gestor1.listarworkouts();
	}
	
	public void nuevoUsuario(usuario usuario) throws Exception {
		Gestor gestor = new Gestor();
		gestor.nuevoUsuario(usuario);
		
	}


	public ArrayList<ejercicio> listarEjercicios(String idEjercicio) throws Exception {
		return gestor1.listarEjercicios(idEjercicio);
	}


	public void modificarUsuario(usuario usuario) throws Exception {
		Gestor gestor = new Gestor();
		gestor.modificarUsuario(usuario);		
	}

}
