package common;

import java.io.Serializable;
import java.time.LocalDateTime;


public class PuntoIntermedio implements Serializable{

	private LocalDateTime horaLlegada;
	private LocalDateTime horaSalida;
	private String nombre;
	private int lon;
	private int lat;
	private int orden;
	
	
	public PuntoIntermedio(LocalDateTime horaSalida,LocalDateTime horaLlegada, int lon,int lat, int orden, String nombre) {
		super();
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.nombre = nombre;
		this.lat=lat;
		this.lon=lon;
		this.orden=orden;
		
	}
	
	public LocalDateTime getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(LocalDateTime horaSalida) {
		this.horaSalida = horaSalida;
	}
	
	
	
	
	public LocalDateTime getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(LocalDateTime horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	
	
	
}
