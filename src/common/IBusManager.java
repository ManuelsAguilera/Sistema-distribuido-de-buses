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
	boolean crearNuevoViaje(String matricula, LocalDate fecha, LocalTime horaSalida, LocalTime horaSalidaEstimada);
	boolean obtenerViaje(String idViaje);
	boolean eliminarViaje(String idViaje);
	
	// Cliente - rutas
	boolean crearNuevaRuta(String nombreOrigen, String nombreDestino); // la hora se calcula mediante la API
	boolean consultarRutas(String origen, String destino); // Conjunto de rutas de ese origen y destino
	boolean consultarRuta(String idRuta);
	boolean modificarRuta(String idRuta);
	boolean eliminarRuta(String idRuta);
	
	// Punto intermedio
	boolean crearPuntoIntermedio(String longitud, String latitud, String nombrePunto, String orden, LocalTime horaLlegada, LocalTime horaSalida);
	boolean consultarPuntoIntermedio(String idPunto);
	boolean eliminarPuntoIntermedio(String idPunto);
	
	// Cliente - pasajes
	boolean crearPasaje(String idViaje, String idPasajero, String idPuntoOrigen, String idPuntoDestino,LocalDateTime fechaCompra, float precio, String asiento);
	boolean eliminarPasaje(String idPasaje);
	boolean consultarPasaje(String idPasaje);
	boolean consultarPasajes(String puntoOrigen, String puntoDestino);
	
	// Cliente - buses
	boolean crearBus(String matricula, String modelo, int capacidad);
	boolean eliminarBus(String idBus);
	boolean modificarBus(String idBus);
	boolean notificarSalidaDeBus(String matricula);
	boolean notificarLlegadaDeBus(String matricula);
	
	// Cliente - pasajero
	boolean crearPasajero();
	boolean eliminarBus();
	boolean 
}
