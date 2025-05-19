package common;

import java.io.Serializable;
import java.time.LocalDateTime;


public class PuntoIntermedio implements Serializable{

	private String nombre;
	private int idRuta;
	private int idPunto;
	private float lon;
	private float lat;
	private int orden;
	
	
	public PuntoIntermedio(int idPunto, int idRuta, float lon, float lat, int orden, String nombre) {
		super();
		this.idPunto = idPunto;
		this.nombre = nombre;
		this.lat=lat;
		this.lon=lon;
		this.orden=orden;
		
	}
	
	public PuntoIntermedio(int idRuta, float lon, float lat, int orden, String nombre) {
		super();

		this.nombre = nombre;
		this.lat=lat;
		this.lon=lon;
		this.orden=orden;
		
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
