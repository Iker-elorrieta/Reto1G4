package Modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class Workout implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String nombre;
	int num_ejercicios;
	int nivel;
	String video;
	ArrayList<Ejercicio> ejercicio;
	
	
	public Workout(String id, String nombre, int num_ejercicios, int nivel, String video, ArrayList<Ejercicio> ejercicio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.num_ejercicios = num_ejercicios;
		this.nivel = nivel;
		this.video = video;
		this.ejercicio = ejercicio;
	}
	
	public Workout() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public ArrayList<Ejercicio> getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(ArrayList<Ejercicio> ejercicio) {
		this.ejercicio = ejercicio;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNum_ejercicios() {
		return num_ejercicios;
	}

	public void setNum_ejercicios(int num_ejercicios) {
		this.num_ejercicios = num_ejercicios;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", nombre=" + nombre + ", num_ejercicios=" + num_ejercicios + ", nivel=" + nivel
				+ ", video=" + video + ", ejercicio=" + ejercicio + "]";
	}
	
	
	

	
	
	
	
}
