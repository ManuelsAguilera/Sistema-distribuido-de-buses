package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.sql.Connection;

import persistance.dao.*;


public class BusManagerImpl extends UnicastRemoteObject  implements IBusManager {

	
	private static final long serialVersionUID = 1L;
	private ViajeDAO viajeDAO;
	private BusDAO busDAO;
	private RutaDAO rutaDAO;
	private PasajeroDAO pasajeroDAO;
	private PasajeDAO pasajeDAO;
	private PuntoIntermedioDAO puntoIntermedioDAO;
	
	
	public BusManagerImpl(Connection conn) throws RemoteException {
	    super();
	    this.viajeDAO = new ViajeDAO(conn);
	    this.busDAO = new BusDAO(conn);
	    this.rutaDAO = new RutaDAO(conn);
	    this.pasajeroDAO = new PasajeroDAO(conn);
	    this.pasajeDAO = new PasajeDAO(conn);
	    this.puntoIntermedioDAO = new PuntoIntermedioDAO(conn);
	}

	@Override
	public void testConnection(String message)  throws RemoteException{
		System.out.println("Client is connected!\n"+message);
	}
	
	@Override
	public float consultarVentas() throws SQLException {
			return pasajeDAO.consultaVentas();
		
	}


	@Override
	public boolean crearNuevoViaje(Viaje viaje, int idRuta) {
		return false;
	}


	@Override
	public Viaje obtenerViaje(int idViaje) throws SQLException {
		return viajeDAO.getViaje(idViaje);
	}


	@Override
	public boolean eliminarViaje(int idViaje) throws SQLException {
		return viajeDAO.delete(idViaje);
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino) throws SQLException {
		return viajeDAO.getViajePorOrigen(origen,destino);
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino, LocalDate fecha) throws SQLException {
		return viajeDAO.getViajePorOrigen(origen,destino,fecha);
	}


	@Override
	public ArrayList<Ruta> obtenerRutasDisp() throws SQLException {
		return rutaDAO.getAllRutas();
	}


	@Override
	public PuntoIntermedio consultarPuntoIntermedio(int idPunto) {
		try {
			return puntoIntermedioDAO.getPuntoIntermedio(idPunto);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public boolean crearPasaje(int idViaje, int idPasajero, int idOrigen, String destino, LocalDateTime fechaCompra,
			float precio, int asiento) {
		
		try {
			
			Pasaje pasajeNuevo = new Pasaje(idPasajero, idOrigen, asiento, fechaCompra, precio, asiento, asiento);
			pasajeDAO.insert(pasajeNuevo);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean eliminarPasaje(int idPasaje) throws SQLException {
		return pasajeDAO.delete(idPasaje);
	}


	@Override
	public Pasaje consultarPasaje(int idPasaje) throws SQLException {
		return pasajeDAO.getPasaje(idPasaje);
	}


	@Override
	public boolean crearBus(String matricula, String modelo, int capacidad) throws SQLException {
		return busDAO.insert(new Bus(matricula,modelo,capacidad));
	}


	@Override
	public boolean eliminarBus(String matricula) throws SQLException {
		return busDAO.delete(matricula);
	}


	@Override
	public boolean modificarBus(Bus bus) throws SQLException {
		return busDAO.update(bus.getMatricula(), bus);
	}


	@Override
	public boolean crearPasajero(int idPasajero, String nombre, String correo) throws SQLException {
		return pasajeroDAO.insert(new Pasajero(idPasajero,nombre,correo));
	}


	@Override
	public boolean eliminarPasajero(int idPasajero) throws SQLException {
		return pasajeroDAO.delete(idPasajero);
	}


	@Override
	public boolean modificarPasajero(Pasajero pasajero) throws SQLException {
		return pasajeroDAO.update(pasajero.getIdPasajero(),pasajero);
	}



	
}
