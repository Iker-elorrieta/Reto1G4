package Controlador;

import Modelo.Gestor;
import Modelo.usuario;

public class Controlador {

	public static boolean inicioSesion(usuario usuario) throws Exception {

			return Gestor.inicioSesion(usuario);
			

}
}
