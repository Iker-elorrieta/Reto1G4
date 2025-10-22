package Backups;

import Modelo.Ejercicio;

public class DatosWorkout extends Ejercicio implements Runnable{
	private String nombre;
	
	
	
	public DatosWorkout() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DatosWorkout(String nombre, int numEjercicios, int nivel, String uRL, String nombreEjer, int numSeries,
			int descanso, String foto) {
		super(nombre, numEjercicios, nivel, uRL, nombreEjer, numSeries, descanso, foto);
	}

	@Override
	public String toString() {
		return "DatosWorkout [nombre=" + getNombre() + ", numSeries=" + getNumSeries() +  ", foto="
				+ getFoto() + ", id=" + getId() + ", numEjercicios=" + getNumEjercicios() + ", nivel=" + getNivel() + ", URL=" + getURL() + "]";
	}

	@Override
	public void run() {
		
		
			
	}


}


