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
		// TODO Auto-generated method stub
		System.out.println("Client is connected!\n"+message);
	}
	
	@Override
	public float consultarVentas() throws SQLException {
			return pasajeDAO.consultaVentas();
		
	}


	@Override
	public boolean crearNuevoViaje(Viaje viaje, int idRuta) {
		// TODO Auto-generated method stub
		return ;
	}


	@Override
	public Viaje obtenerViaje(int idViaje) throws SQLException {
		// TODO Auto-generated method stub
		return viajeDAO.getViaje(idViaje);
	}


	@Override
	public boolean eliminarViaje(int idViaje) throws SQLException {
		// TODO Auto-generated method stub
		return viajeDAO.delete(idViaje);
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino) throws SQLException {
		// TODO Auto-generated method stub
		return viajeDAO.getViajePorOrigen(origen,destino);
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino, LocalDate fecha) throws SQLException {
		// TODO Auto-generated method stub
		return viajeDAO.getViajePorOrigen(origen,destino,fecha);
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
