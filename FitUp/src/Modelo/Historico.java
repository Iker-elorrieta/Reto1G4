package Modelo;

public class Historico {

	private int id;
	private int completado; 
	private String fecha;
	private int tiempoTotal;
	private Usuario usuario;
	private Workout workout;
	
	
	public Historico() {}
	
	public Historico(int id, int completado, String fecha, int tiempoTotal, Usuario usuario, Workout workout) {
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
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
