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
	float consultarVentas() throws SQLException;
	
	// Cliente - viajes
	boolean crearNuevoViaje(Viaje viaje,int idRuta) throws SQLException;
	Viaje obtenerViaje(int idViaje) throws SQLException;
	boolean eliminarViaje(int idViaje) throws SQLException;
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen,String Destino) throws SQLException;
	ArrayList<Viaje> obtenerViajePorOrigen(String Origen, String Destino,LocalDate fecha) throws SQLException;
	//boolean modificarViaje(Viaje viaje,String matricula) la idea seria quizas cambiar el bus.
	
	
	//Cliente rutas
	
	ArrayList<Ruta> obtenerRutasDisp()throws SQLException;
	
	
	
	// Punto intermedio
	PuntoIntermedio consultarPuntoIntermedio(int idPunto) throws SQLException;

	
	// Cliente - pasajes
	boolean crearPasaje(int idViaje, int idPasajero, int idOrigen, String destino, LocalDateTime fechaCompra, float precio, int asiento);
	boolean eliminarPasaje(int idPasaje) throws SQLException;
	Pasaje consultarPasaje(int idPasaje) throws SQLException;
	
	// Cliente - buses
	boolean crearBus(String matricula, String modelo, int capacidad) throws SQLException;
	boolean eliminarBus(String matricula) throws SQLException;
	boolean modificarBus(Bus bus) throws SQLException;
	
	// Cliente - pasajero
	boolean crearPasajero(int idPasajero, String nombre, String correo) throws SQLException;
	boolean eliminarPasajero(int idPasajero) throws SQLException;
	boolean modificarPasajero(Pasajero pasajero) throws SQLException;
}
