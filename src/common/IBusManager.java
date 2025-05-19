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
	boolean consultarVentas();
	
	// Cliente - viajes
	boolean crearNuevoViaje(int  idViaje, int idRuta, String matricula, LocalDate fecha, LocalTime horaSalida, LocalTime horaSalidaEstimada);
	boolean obtenerViaje(int idViaje);
	boolean eliminarViaje(int idViaje);
	
	// Cliente - rutas
	boolean crearNuevaRuta(int idRuta, String nombreOrigen, String nombreDestino, LocalTime duracion); // la hora se calcula mediante la API
	boolean consultarRutas(String origen, String destino); // Conjunto de rutas de ese origen y destino
	boolean consultarRuta(int idRuta);
	boolean modificarRuta(int idRuta);
	boolean eliminarRuta(int idRuta);
	
	// Punto intermedio
	boolean crearPuntoIntermedio(int idPunto, int idRuta, float longitud, float latitud, String nombrePunto,int orden, LocalDateTime horaLlegada, LocalDateTime horaSalida);
	boolean consultarPuntoIntermedio(int idPunto);
	boolean eliminarPuntoIntermedio(int idPunto);
	
	// Cliente - pasajes
	boolean crearPasaje(int idPasaje, int idViaje, int idPasajero, int idPuntoOrigen, int idPuntoDestino, LocalDateTime fechaCompra, float precio, int asiento);
	boolean eliminarPasaje(int idPasaje);
	boolean consultarPasaje(int idPasaje);
	boolean consultarPasajes(String puntoOrigen, String puntoDestino);
	
	// Cliente - buses
	boolean crearBus(String matricula, String modelo, int capacidad);
	boolean eliminarBus(String matricula);
	boolean modificarBus(String matricula);
	boolean notificarSalidaDeBus(String matricula);
	boolean notificarLlegadaDeBus(String matricula);
	
	// Cliente - pasajero
	boolean crearPasajero(int idPasajero, String nombre, String correo);
	boolean eliminarPasajero(int idPasajero);
	boolean modificarPasajero(int idPasajero);
}
