package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import persistance.dao.*;


public class BusManagerImpl extends UnicastRemoteObject  implements IBusManager {

	protected BusManagerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}



	private static final long serialVersionUID = 1L;
	private ViajeDAO viajeDAO;
	private BusDAO busDAO;
	private RutaDAO rutaDAO;
	private PasajeroDAO pasajeroDAO;
	private PasajeDAO pasajeDAO;
	private PuntoIntermedioDAO puntoIntermedioDAO;


	@Override
	public void testConnection(String message)  throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println("Client is connected!\n"+message);
	}
	
	
	public ArrayList<String> dumpTerminal() throws RemoteException {
		
		
		System.out.println("Accediendo base de datos");
		ArrayList<String> names = new ArrayList<String>();
		var sql = " SELECT nombre,ciudad FROM terminal ORDER BY nombre";
		try (var conn =  DB.connect();
	             var stmt = conn.createStatement()) {
	            
	            var rs = stmt.executeQuery(sql);
	            
	            while (rs.next())
	            {
	            	String name = rs.getString("nombre")+ " "+ rs.getString("ciudad");
	            	names.add(name);
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }
		
		return names;
	}



	@Override
	public float consultarVentas() {
		return 0;
	}


	@Override
	public boolean crearNuevoViaje(Viaje viaje, int idRuta) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Viaje obtenerViaje(int idViaje) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean eliminarViaje(int idViaje) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String Origen, String Destino) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String Origen, String Destino, LocalDate fecha) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Ruta> obtenerRutasDisp() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PuntoIntermedio consultarPuntoIntermedio(int idPunto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean crearPasaje(int idViaje, int idPasajero, String origen, String destino, LocalDateTime fechaCompra,
			float precio, int asiento) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean eliminarPasaje(int idPasaje) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Pasaje consultarPasaje(int idPasaje) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean crearBus(String matricula, String modelo, int capacidad) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean eliminarBus(String matricula) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean modificarBus(Bus bus) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean crearPasajero(int idPasajero, String nombre, String correo) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean eliminarPasajero(int idPasajero) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean modificarPasajero(Pasajero pasajero) {
		// TODO Auto-generated method stub
		return false;
	}



	
}
