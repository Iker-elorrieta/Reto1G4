package Modelo;

public class ejercicio extends workout {

	String nombre;
	int numSeries;
	float descanso;
	String foto;
	
	public ejercicio(String nombre, int numEjercicios, int nivel, String uRL, String nombre2, int numSeries,
			float descanso, String foto) {
		super(nombre, numEjercicios, nivel, uRL);
		nombre = nombre2;
		this.numSeries = numSeries;
		this.descanso = descanso;
		this.foto = foto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumSeries() {
		return numSeries;
	}

	public void setNumSeries(int numSeries) {
		this.numSeries = numSeries;
	}

	public float getDescanso() {
		return descanso;
	}

	public void setDescanso(float descanso) {
		this.descanso = descanso;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "ejercicio [nombre=" + nombre + ", numSeries=" + numSeries + ", descanso=" + descanso + ", foto=" + foto
				+ "]";
	}
	
	
}
