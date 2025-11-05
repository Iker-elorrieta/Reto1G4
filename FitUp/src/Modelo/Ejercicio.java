package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Ejercicio  implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private int numSeries;
    private int descanso;
    private String foto;
    ArrayList<Series> series;


    public Ejercicio() {
        this.series = new ArrayList<>();
    }

	
    public Ejercicio(int id, String nombre, int numSeries, int descanso, String foto, ArrayList<Series> series) {
        this.id = id;
        this.nombre = nombre;
        this.numSeries = numSeries;
        this.descanso = descanso;
        this.foto = foto;
        this.series = (series != null) ? series : new ArrayList<>();
    }




	public int getId() {
		return id;
	}

	public int setId(int id) {
		return this.id = id;
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

	public int getDescanso() {
		return descanso;
	}

	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public ArrayList<Series> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<Series> series) {
		this.series = series;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public String toString() {
		return "Ejercicio [nombre=" + nombre + ", numSeries=" + numSeries + ", descanso=" + descanso + ", foto=" + foto
				+ ", series=" + series + "]";
	}

    

   
}
