package common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pasajero implements Serializable{
	private int idPasajero;
	private String nombre;
	private String correo;
	public Pasajero(int idPasajero, String nombre, String correo) {
		super();
		this.idPasajero = idPasajero;
		this.nombre = nombre;
		this.correo = correo;
	}
	public Pasajero(String nombre, String correo) {
		super();
		this.idPasajero = -1;
		this.nombre = nombre;
		this.correo = correo;
	}
	public int getIdPasajero() {
		return idPasajero;
	}
	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
	
}