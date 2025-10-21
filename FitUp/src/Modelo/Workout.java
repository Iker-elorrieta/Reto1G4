package Modelo;

public class Workout {
	String id;
	String nombre;
	int numEjercicios;
	int nivel;
	String URL;
	
	public Workout(String nombre, int numEjercicios, int nivel, String uRL) {
		super();
		this.nombre = nombre;
		this.numEjercicios = numEjercicios;
		this.nivel = nivel;
		URL = uRL;
	}

	public Workout() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "workout [nombre=" + nombre + ", numEjercicios=" + numEjercicios + ", nivel=" + nivel + ", URL=" + URL
				+ "]";
	}
	
	
}
