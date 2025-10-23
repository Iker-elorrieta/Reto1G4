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
	int numEjercicios;
	int nivel;
	String URL;
	ArrayList<Ejercicio> ejercicio;
	
	
	public Workout(String id, String nombre, int numEjercicios, int nivel, String uRL, ArrayList<Ejercicio> ejercicio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numEjercicios = numEjercicios;
		this.nivel = nivel;
		URL = uRL;
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
	public int getNumEjercicios() {
		return numEjercicios;
	}
	public void setNumEjercicios(int numEjercicios) {
		this.numEjercicios = numEjercicios;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
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
	
	
	@Override
	public String toString() {
		return "Workout [id=" + id + ", nombre=" + nombre + ", numEjercicios=" + numEjercicios + ", nivel=" + nivel
				+ ", URL=" + URL + ", ejercicio=" + ejercicio + "]";
	}

	
	
	
	
}
