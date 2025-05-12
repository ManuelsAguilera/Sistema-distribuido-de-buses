package common;

import java.util.ArrayList;

public class Bus {

	private String patente;
	private int asientosTotales;
	private ArrayList<Boolean> asientosLista;
	private ArrayList<Terminal> terminalLista;
	
	
	public Bus(String patente, int asientosTotales, ArrayList<Boolean> asientosLista, ArrayList<Terminal> terminalLista) {
		super();
		this.patente = patente;
		this.asientosTotales = asientosTotales;
		this.asientosLista = asientosLista;
		this.terminalLista = terminalLista;
	}
	
	
	
	
}
