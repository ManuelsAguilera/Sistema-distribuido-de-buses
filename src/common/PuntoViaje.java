package common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PuntoViaje implements Serializable {

	private LocalDateTime horaLlegada;
	private LocalDateTime horaLlegadaEstimada;
	private LocalDateTime horaSalida;
	private int idViaje;
	private int idPunto;
	

	public PuntoViaje(int idViaje, int idPunto) {
		super();
		this.horaLlegada = null;
		this.horaLlegadaEstimada = null;
		this.horaSalida = null;
		this.idViaje = idViaje;
		this.idPunto = idPunto;
	}


	public LocalDateTime getHoraLlegada() {
		return horaLlegada;
	}


	public void setHoraLlegada(LocalDateTime horaLlegada) {
		this.horaLlegada = horaLlegada;
	}


	public LocalDateTime getHoraLlegadaEstimada() {
		return horaLlegadaEstimada;
	}


	public void setHoraLlegadaEstimada(LocalDateTime horaLlegadaEstimada) {
		this.horaLlegadaEstimada = horaLlegadaEstimada;
	}


	public LocalDateTime getHoraSalida() {
		return horaSalida;
	}


	public void setHoraSalida(LocalDateTime horaSalida) {
		this.horaSalida = horaSalida;
	}


	public int getIdViaje() {
		return idViaje;
	}


	public int getIdPunto() {
		return idPunto;
	}
	
	
	
	
	
	
	
	
}
