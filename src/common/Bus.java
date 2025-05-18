package common;

import java.io.Serializable;
import java.util.ArrayList;


public class Bus implements Serializable {

	
	private String matricula;
	private String modelo;
	private int asientosTotales;


	
	
	public Bus(String matricula,String modelo, int asientosTotales, ArrayList<PuntoIntermedio> terminalLista) {
		super();
		this.matricula = matricula;
		this.modelo = modelo;
		this.asientosTotales = asientosTotales;
	}
	
	public String getPatente() {
		return matricula;
	}


	public void setPatente(String matricula) {
		this.matricula = matricula;
	}


	public int getAsientosTotales() {
		return asientosTotales;
	}


	public void setAsientosTotales(int asientosTotales) {
		this.asientosTotales = asientosTotales;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	
	
}
