package Modelo;

import java.util.Date;

public class Historico {

	private int id;
	private int completado; 
	private Date fecha;
	private int tiempoTotal;
	private Usuario usuario;
	private Workout workout;
	
	
	public Historico() {}
	
	public Historico(int id, int completado, Date fecha, int tiempoTotal, Usuario usuario, Workout workout) {
		this.id = id;
		this.completado = completado;
		this.fecha = fecha;
		this.tiempoTotal = tiempoTotal;
		this.usuario = usuario;
		this.workout = workout;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompletado() {
		return completado;
	}
	public void setCompletado(int completado) {
		this.completado = completado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getTiempoTotal() {
		return tiempoTotal;
	}
	public void setTiempoTotal(int tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Workout getWorkout() {
		return workout;
	}
	public void setWorkout(Workout workout) {
		this.workout = workout;
	}
	
	
}
