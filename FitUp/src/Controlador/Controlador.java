package Controlador; 
import java.awt.Color; 

import java.util.ArrayList; 
import javax.swing.JTextField;

import Backup.*; 
import Modelo.*;

import java.util.Date;

public class Controlador { 
	Gestor gestor1 = new Gestor(); 
	ExportarDatos backup = new ExportarDatos(); 
	
	
	public boolean inicioSesion(Usuario usuario) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			return gestor1.inicioSesion(usuario); 
			} else {
			return gestor1.inicioSesionOffline(usuario);
			}
		} 
	
	public Usuario getUsuarioActual() { 
		return gestor1.getDatos(); 
		} 
	
	public ArrayList<Workout> listarWorkouts() throws Exception { 
		if(gestor1.checkInternetConnection()) {
			return gestor1.listarworkouts(); 
			} else {
			return gestor1.listarworkoutsOffline();
			}
		} 
	
	public void nuevoUsuario(Usuario usuario) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			gestor1.nuevoUsuario(usuario); 
			} else {
				gestor1.nuevoUsuarioOffline(usuario);;
			}
		}  
	
	public boolean correoExiste(String correo) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			return gestor1.correoExiste(correo); 
			} else {
			return gestor1.correoExisteOffline(correo);
			}
		} 
	
	public ArrayList<Ejercicio> listarEjercicios(String idEjercicio) throws Exception { 
		
		if(gestor1.checkInternetConnection()) {
			return gestor1.listarEjercicios(idEjercicio); 
			} else {
			return gestor1.listarEjerciciosOffline(idEjercicio);
			}
		} 
	
	public void modificarUsuario(Usuario usuario) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			gestor1.modificarUsuario(usuario); 
			} else {
				gestor1.modificarUsuariOffline(usuario);
			}
		} 
	
	public ArrayList<Historico> listarHistorico(int idUsuario) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			return gestor1.listarHistorico(idUsuario); 
			} else {
				return gestor1.listarHistoricoOffline(idUsuario); 
			}
		} 
	
	public int conseguirTiempoPrevisto(String idWorkout) throws Exception { 
		if(gestor1.checkInternetConnection()) {
			return gestor1.conseguirTiempoPrevisto(idWorkout); 
			} else {
			return gestor1.conseguirTiempoPrevistoOffline(idWorkout); 
			}
		
		} 
	
	public ArrayList<Series> listarSeries(String idWorkout, String idEjercicio) throws Exception {
		if(gestor1.checkInternetConnection()) {
			return gestor1.listarSeries(idWorkout, idEjercicio);
		} else {
			return gestor1.listarSeriesOffline(idWorkout, idEjercicio);
		}
	}
	
	public void registrarHistorico(String idWorkout, String nombreWorkout, int tiempoTotal, int completado) throws Exception {
		 Usuario usuarioActual = gestor1.getDatos();
		 
		 Workout workout = new Workout();
		 workout.setId(idWorkout);
		 workout.setNombre(nombreWorkout);

		 Historico historico = new Historico();
		 historico.setUsuario(usuarioActual);
		 historico.setWorkout(workout);
		 historico.setFecha(new Date());
		 historico.setTiempoTotal(tiempoTotal);
		 historico.setCompletado(completado);

		 if(gestor1.checkInternetConnection()) {
			 gestor1.escribirHistorico(historico);
		 }else {
			gestor1.registrarHistoricoOffline(historico);
			}
	}
	
	public String cambiarNivel() throws Exception {
	    return gestor1.cambiarNivel();
	}

	//Exportar datos y validaciones 
	
	public boolean ejecutarExportacion() throws Exception { return gestor1.exportarDatos(); } 
	
	public boolean soloTexto(String nombre) { return gestor1.soloTexto(nombre); } 
	
	public boolean contraseñaValida(String contraseña) { return gestor1.contraseñaValida(contraseña); } 
	
	public void setPlaceholder(JTextField textNombre, String string, Color color) { gestor1.setPlaceholder(textNombre, string, color); } 
	
	public boolean fechaValida(String fechaNac) { return gestor1.fechaValida(fechaNac); } 
	
	public boolean correoValido(String correo) { return gestor1.correoValido(correo); } 
	
	public boolean formatoFechaValido(String fechaNac) { return gestor1.formatoFechaValido(fechaNac); } 
	
	public boolean fechaNoFutura(String fechaNac) { return gestor1.fechaNoFutura(fechaNac); }

	


}
