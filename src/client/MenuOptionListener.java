package client;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.Bus;
import common.Pasaje;
import common.Pasajero;
import common.PuntoIntermedio;
import common.Ruta;
import common.Viaje;

public interface MenuOptionListener {
	
	// Pasajeros
	int crearPasajero (Pasajero pasajero);
	boolean eliminarPasajero(int idPasajero);
	boolean modificarPasajero(int idPasajero, Pasajero pasajero);
	
	// Buses
	boolean crearBus(Bus bus);
	Bus getBus(String matricula);
	boolean eliminarBus(String matricula);
	boolean modificarBus(Bus bus);
	
	// Terminales
	PuntoIntermedio consultarPuntoIntermedio(int idPunto);
	
	// Viajes
	void crearViaje(Viaje viaje, Ruta ruta);
	void eliminarViaje(Viaje viaje);
	void modificarViaje(Viaje viaje);
	public ArrayList<Viaje> obtenerViaje(String origen, String destino, LocalDate fecha);
	public ArrayList<Viaje> obtenerViaje(String origen, String destino);

	// Rutas
	ArrayList<Ruta> obtenerRutasDisp();
	
	// Pasajes
	boolean crearPasaje(Pasaje pasaje);
	boolean eliminarPasaje(int idPasaje);
	boolean modificarPasaje(Pasaje pasaje);
	Pasaje consultarPasaje(int idPasaje);
	
}
