package Controlador;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;

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

	public boolean correoExiste(String correo) throws Exception {
	    return gestor1.correoExiste(correo);
	}


	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception {
		return gestor1.listarEjercicios(idEjercicio);
	}

	public void modificarUsuario(Usuario usuario) throws Exception {
			gestor1.modificarUsuario(usuario);		
	}

	public boolean ejecutarExportacion() throws Exception {
		return gestor1.exportarDatos();
	}

	public boolean soloTexto(String nombre) {
		return gestor1.soloTexto(nombre);
	}

	public boolean contraseñaValida(String contraseña) {
		return gestor1.contraseñaValida(contraseña);
	}

	public void setPlaceholder(JTextField textNombre, String string, Color color) {
		 gestor1.setPlaceholder(textNombre, string, color);		
	}

	public boolean fechaValida(String fechaNac) {
		return gestor1.fechaValida(fechaNac);
	}

	public boolean correoValido(String correo) {
		return gestor1.correoValido(correo);
	}

	public boolean formatoFechaValido(String fechaNac) {
		return gestor1.formatoFechaValido(fechaNac);
	}

	public boolean fechaNoFutura(String fechaNac) {
		return gestor1.fechaNoFutura(fechaNac);
	}


	
}