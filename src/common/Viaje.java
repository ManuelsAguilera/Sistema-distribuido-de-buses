package common;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Viaje implements Serializable {

	private int idRuta;
	private String matricula;
	private LocalDate fecha;
	private LocalTime salida;
	private LocalTime salidaEstimada;
	public Viaje(int idRuta, String matricula, LocalDate fecha, LocalTime salida, LocalTime salidaEstimada) {
		super();
		this.idRuta = idRuta;
		this.matricula = matricula;
		this.fecha = fecha;
		this.salida = salida;
		this.salidaEstimada = salidaEstimada;
	}
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getSalida() {
		return salida;
	}
	public void setSalida(LocalTime salida) {
		this.salida = salida;
	}
	public LocalTime getSalidaEstimada() {
		return salidaEstimada;
	}
	public void setSalidaEstimada(LocalTime salidaEstimada) {
		this.salidaEstimada = salidaEstimada;
	}

	
}
