package common;

import java.io.Serializable;
import java.util.ArrayList;


public class Bus implements Serializable {

	private String patente;
	private String modelo;
	private int asientosTotales;
	private ArrayList<Boolean> asientosLista;
	private ArrayList<Terminal> terminalLista;
	
	
	public Bus(String patente,String modelo, int asientosTotales, ArrayList<Terminal> terminalLista) {
		super();
		this.patente = patente;
		this.modelo = modelo;
		this.asientosTotales = asientosTotales;
		this.asientosLista = new ArrayList<Boolean>(asientosTotales);
		this.terminalLista = terminalLista;
	}
	
	public String getPatente() {
		return patente;
	}


	public void setPatente(String patente) {
		this.patente = patente;
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
