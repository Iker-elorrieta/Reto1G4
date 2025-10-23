package Modelo;

import java.io.Serializable;

public class Series  implements Serializable{

	/**
	 * 
	 */
	private String id;
	private int duracion;
	private int repeticiones;
	
	public Series() {
	}
	
	
	
	public Series(String id, int duracion, int repeticiones) {
		super();
		this.id = id;
		this.duracion = duracion;
		this.repeticiones = repeticiones;
	}


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public int getRepeticiones() {
		return repeticiones;
	}
	
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	
	
	@Override
	public String toString() {
		return "Series [duracion=" + duracion + ", repeticiones=" + repeticiones + "]";
	}
	
	
	
	
	
	
}
