package common;

import java.io.Serializable;
import java.time.LocalDateTime;


public class PuntoIntermedio implements Serializable{

	private LocalDateTime horaLlegada;
	private LocalDateTime horaSalida;
	private String nombre;
	private int idRuta;
	private int idPunto;
	private float lon;
	private float lat;
	private int orden;
	
	
	public PuntoIntermedio(LocalDateTime horaSalida, LocalDateTime horaLlegada, int idPunto, int idRuta, float lon, float lat, int orden, String nombre) {
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

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public int getIdPunto() {
		return idPunto;
	}

	public void setIdPunto(int idPunto) {
		this.idPunto = idPunto;
	}

	
	
	
}
