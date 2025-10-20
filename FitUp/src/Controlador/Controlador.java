package Controlador;

import Modelo.Gestor;
import Modelo.usuario;

public class Controlador {
	Gestor gestor1 = new Gestor();

	public boolean inicioSesion(usuario usuario) throws Exception {

		return gestor1.inicioSesion(usuario);

	}

	public usuario workoutsId() {
		return gestor1.idWorkouts();
	}

}
