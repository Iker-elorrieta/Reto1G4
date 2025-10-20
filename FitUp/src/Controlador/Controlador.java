package Controlador;

import java.util.ArrayList;

import Modelo.Gestor;
import Modelo.usuario;
import Modelo.workout;

public class Controlador {
	Gestor gestor1 = new Gestor();

	public boolean inicioSesion(usuario usuario) throws Exception {

		return gestor1.inicioSesion(usuario);

	}

	public usuario workoutsId() {
		return gestor1.idWorkouts();
	}

	public ArrayList<workout> listarWorkouts() throws Exception {
		return gestor1.listarworkouts();
	}

}
