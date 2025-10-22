package Modelo;

public class Series extends Ejercicio{

	private int duracion;
	private int repeticiones;
	
	public Series() {
		super();
	}
	
	public Series(String nombre, int numEjercicios, int nivel, String uRL, String nombre2, int numSeries, int descanso,
			String foto, int duracion, int repeticiones) {
		super(nombre, numEjercicios, nivel, uRL, nombre2, numSeries, descanso, foto);
		this.duracion = duracion;
		this.repeticiones = repeticiones;
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
