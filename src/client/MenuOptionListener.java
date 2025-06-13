package client;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.Pasajero;
import common.Ruta;
import common.Viaje;

public interface MenuOptionListener {
	void onMenuOptionSelected(int option); 
	
	// Pasajeros
	boolean onCrearPasajero (String nombre, String correo);
	boolean eliminarPasajero(int idPasajero);
	boolean modificarPasajero(int idPasajero, Pasajero pasajero);
	
	// Viajes
	void crearViaje(Viaje viaje, Ruta ruta);
	void eliminarViaje(Viaje viaje);
	public ArrayList<Viaje> obtenerViaje(String origen, String destino, LocalDate fecha);
	public ArrayList<Viaje> obtenerViaje(String origen, String destino);

	// Rutas
	//boolean 
	
}
