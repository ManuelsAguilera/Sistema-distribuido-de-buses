package common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pasaje implements Serializable{
	
	private int idPasajero;
	private int idOrigen;
	private int idDestino;
	private LocalDateTime fechaCompra;
	private int precio;
	private int asiento;
	
	
	
	public Pasaje(int idPasajero, int idOrigen, int idDestino, LocalDateTime fechaCompra, int precio, int asiento,
			int idViaje) {
		super();
		this.idPasajero = idPasajero;
		this.idOrigen = idOrigen;
		this.idDestino = idDestino;
		this.fechaCompra = fechaCompra;
		this.precio = precio;
		this.asiento = asiento;
		this.idViaje = idViaje;
	}
	
	
	
	private int idViaje;
	public int getIdViaje() {
		return idViaje;
	}
	public void setIdViaje(int idViaje) {
		this.idViaje = idViaje;
	}
	public int getIdPasajero() {
		return idPasajero;
	}
	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}
	public int getIdOrigen() {
		return idOrigen;
	}
	public void setIdOrigen(int idOrigen) {
		this.idOrigen = idOrigen;
	}
	public int getIdDestino() {
		return idDestino;
	}
	public void setIdDestino(int idDestino) {
		this.idDestino = idDestino;
	}
	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public int getAsiento() {
		return asiento;
	}
	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}
	

}
