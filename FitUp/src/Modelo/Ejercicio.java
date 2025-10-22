package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio extends Workout implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int numSeries;
    private int descanso;
    private String foto;


    public Ejercicio() {}

    

    public Ejercicio(String nombre, int numEjercicios, int nivel, String URL, String nombreEj, int numSeries, int descanso, String foto) {
		super(nombre, numEjercicios, nivel, URL);
		this.nombre = nombre;
		this.numSeries = numSeries;
		this.descanso = descanso;
		this.foto = foto;
    }



    // Resto de getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getNumSeries() { return numSeries; }
    public void setNumSeries(int numSeries) { this.numSeries = numSeries; }
    public int getDescanso() { return descanso; }
    public void setDescanso(int descanso) { this.descanso = descanso; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
