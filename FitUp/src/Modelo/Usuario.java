package Modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 int id;
	String nombre; 
	String apellido1;
	String apellido2;
	String correo;
	String contraseña;
	String fechaNac;
	int nivel;
	
	public Usuario(int id, String nombre, String apellido1, String apellido2, String correo, String contraseña, String fechaNac) {
		this.id=id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.correo = correo;
		this.contraseña = contraseña;
		this.fechaNac = fechaNac;
	}

	public Usuario() {
		this.id = 0;
		
	}

	public int getNivel() {
		return nivel;
	}
	 public void setId(int id) {
			this.id = id;
		}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getContraseña() {
		return contraseña;
	}


	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}


	public String getFechaNac() {
		return fechaNac;
	}


	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	@Override
	public String toString() {
		return "usuario [id=" + id + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", correo=" + correo + ", contraseña=" + contraseña + ", fechaNac=" + fechaNac + ", nivel=" + nivel
				+ "]";
	}


	
	
	

}
