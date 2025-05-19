package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public interface IBusManager extends  Remote  {

	
	//Aqui irian las funciones que accede el cliente al servidor
	
	// DB
	ArrayList<String> dumpTerminal() throws RemoteException;
	void testConnection(String message) throws RemoteException;
	
	// Cliente - ventas 
	float consultarVentas();
	
	// Cliente - viajes
	boolean crearNuevoViaje(Viaje viaje,int idRuta);
	Viaje obtenerViaje(int idViaje);
	boolean eliminarViaje(int idViaje);
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen,String Destino);
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen, String Destino,LocalDate fecha);
	//boolean modificarViaje(Viaje viaje,String matricula) la idea seria quizas cambiar el bus.
	
	
	//Cliente rutas
	
	ArrayList<Ruta> obtenerRutasDisp();
	
	
	
	// Punto intermedio
	PuntoIntermedio consultarPuntoIntermedio(int idPunto);

	
	// Cliente - pasajes
	boolean crearPasaje(int idViaje, int idPasajero, String origen, String destino, LocalDateTime fechaCompra, float precio, int asiento);
	boolean eliminarPasaje(int idPasaje);
	Pasaje consultarPasaje(int idPasaje);
	
	// Cliente - buses
	boolean crearBus(String matricula, String modelo, int capacidad);
	boolean eliminarBus(String matricula);
	boolean modificarBus(Bus bus);
	
	// Cliente - pasajero
	boolean crearPasajero(int idPasajero, String nombre, String correo);
	boolean eliminarPasajero(int idPasajero);
	boolean modificarPasajero(Pasajero pasajero);
}
