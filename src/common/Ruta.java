package common;

import java.io.Serializable;
import java.time.LocalTime;


public class Ruta implements Serializable{

	private String nombreOrigen;
	private String nombreDestino;
	private int idRuta;
	private LocalTime duracionEstimada; //minutos
	
	
	
	public Ruta(String nombreOrigen, String nombreDestino, int idRuta, LocalTime duracionEstimada) {
		super();
		this.nombreOrigen = nombreOrigen;
		this.nombreDestino = nombreDestino;
		this.idRuta = idRuta;
		this.duracionEstimada = duracionEstimada;
	}
	public String getNombreOrigen() {
		return nombreOrigen;
	}
	public void setNombreOrigen(String nombreOrigen) {
		this.nombreOrigen = nombreOrigen;
	}
	public String getNombreDestino() {
		return nombreDestino;
	}
	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public LocalTime getDuracionEstimada() {
		return duracionEstimada;
	}
	public void setDuracionEstimada(LocalTime duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}
	
	 
}
