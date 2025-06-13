package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public interface IBusManager extends  Remote  {

	
	//Aqui irian las funciones que accede el cliente al servidor
	
	// DB
	void testConnection(String message) throws RemoteException;
	
	// Cliente - ventas 
	float consultarVentas() throws RemoteException;
	
	// Cliente - viajes
	boolean crearNuevoViaje(Viaje viaje,int idRuta) throws RemoteException;
	Viaje obtenerViaje(int idViaje) throws RemoteException;
	boolean eliminarViaje(int idViaje) throws RemoteException;
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen,String Destino) throws RemoteException;
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen, String Destino,LocalDate fecha) throws RemoteException;
	boolean modificarViaje(Viaje viaje) throws RemoteException;
	
	//Cliente rutas
	ArrayList<Ruta> obtenerRutasDisp() throws RemoteException;
	
	
	
	// Punto intermedio
	PuntoIntermedio consultarPuntoIntermedio(int idPunto) throws RemoteException;

	
	// Cliente - pasajes
	boolean crearPasaje(Pasaje pasaje) throws RemoteException;
	boolean eliminarPasaje(int idPasaje) throws RemoteException;
	boolean modificarPasaje(Pasaje pasaje) throws RemoteException;
	Pasaje consultarPasaje(int idPasaje) throws RemoteException;
	
	// Cliente - buses
	boolean crearBus(Bus bus) throws RemoteException;
	Bus getBus(String matricula) throws RemoteException;
	boolean eliminarBus(String matricula) throws RemoteException;
	boolean modificarBus(Bus bus) throws RemoteException;
	
	// Cliente - pasajero
	int crearPasajero(Pasajero pasajero) throws RemoteException;
	Pasajero getPasajero(String nombre, String correo) throws RemoteException;
	Pasajero getPasajero(int idPasajero) throws RemoteException;
	boolean eliminarPasajero(int idPasajero) throws RemoteException;
	boolean modificarPasajero(Pasajero pasajero)  throws RemoteException;
}
