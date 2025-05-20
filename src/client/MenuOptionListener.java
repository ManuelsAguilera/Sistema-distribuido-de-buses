package client;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.Viaje;

public interface MenuOptionListener {
	void onMenuOptionSelected(int option); 
	void onCrearPasajero (String nombre, String correo);
	public ArrayList<Viaje> obtenerViaje(String origen, String destino, LocalDate fecha);
}
