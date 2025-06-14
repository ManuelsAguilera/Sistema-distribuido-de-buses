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
	private PuntoViajeDao puntoViajeDAO;
	
	public BusManagerImpl(Connection conn) throws RemoteException {
	    super();
	    this.viajeDAO = new ViajeDAO(conn);
	    this.busDAO = new BusDAO(conn);
	    this.rutaDAO = new RutaDAO(conn);
	    this.pasajeroDAO = new PasajeroDAO(conn);
	    this.pasajeDAO = new PasajeDAO(conn);
	    this.puntoIntermedioDAO = new PuntoIntermedioDAO(conn);
	    this.puntoViajeDAO = new PuntoViajeDao(conn);
	}

	@Override
	public void testConnection(String message)  throws RemoteException{
		System.out.println("Client is connected!\n"+message);
	}
	
	@Override
	public float consultarVentas() throws RemoteException  {
			try {
				return pasajeDAO.consultaVentas();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RemoteException("Error consulta ventas", e);
			}

		
	}
	
	
	/****
	 * 
	 * Viajes
	 * crearNuevoViaje(Viaje,idRuta)
	 * obtenerViaje(idViaje)
	 * eliminarViaje(idViaje)
	 * modificarViaje(Viaje)
	 * 
	 */
	@Override
	public boolean crearNuevoViaje(Viaje viaje, int idRuta) throws RemoteException {
		try {
			Viaje viajeNuevo = viajeDAO.getViaje(viaje.getidViaje());
			puntoViajeDAO.fillPointsFromViaje(idRuta);
			viajeDAO.insert(viajeNuevo);
			return true;
		} catch (SQLException e) {
			throw new RemoteException("Error al crear nuevo viaje", e);
		}
	}

	@Override
	public Viaje obtenerViaje(int idViaje) throws RemoteException {
		try {
			return viajeDAO.getViaje(idViaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al obtener el viaje", e);
		}
	}

	@Override
	public boolean eliminarViaje(int idViaje) throws RemoteException {
		try {
			return viajeDAO.delete(idViaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al eliminar el viaje", e);
		}
	}
	
	@Override
	public boolean modificarViaje(Viaje viaje) throws RemoteException {
		try
		{
			return viajeDAO.update(viaje.getidViaje(),viaje);
		} catch(SQLException e)
		{
			throw new RemoteException("Error al modificar viaje", e);

		}
	}
	
	
	
	///**
	///Obtener viajes

	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino) throws RemoteException {
		try {
			System.out.println("Obteniendo viaje DAO");
			return viajeDAO.getViajePorOrigen(origen, destino);
		} catch (SQLException e) {
			throw new RemoteException("Error al obtener viaje por origen y destino", e);
		}
	}

	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino, LocalDate fecha) throws RemoteException {
		try {
			return viajeDAO.getViajePorOrigen(origen, destino, fecha);
		} catch (SQLException e) {
			throw new RemoteException("Error al obtener viaje por origen, destino y fecha", e);
		}
	}
	
	
	
	//***************************
	//Rutas

	@Override
	public ArrayList<Ruta> obtenerRutasDisp() throws RemoteException {
		try {
			return rutaDAO.getAllRutas();
		} catch (SQLException e) {
			throw new RemoteException("Error al obtener rutas disponibles", e);
		}
	}

	@Override
	public PuntoIntermedio consultarPuntoIntermedio(int idPunto) throws RemoteException {
		try {
			return puntoIntermedioDAO.getPuntoIntermedio(idPunto);
		} catch (SQLException e) {
			throw new RemoteException("Error al consultar punto intermedio", e);
		}
	}
	
	
	
	//*********************
	//Pasajes
	
	@Override
	public boolean crearPasaje(Pasaje pasaje) throws RemoteException {
		try {
			pasajeDAO.insert(pasaje);
			return true;
		} catch (SQLException e) {
			throw new RemoteException("Error al crear pasaje", e);
		}
	}

	@Override
	public boolean eliminarPasaje(int idPasaje) throws RemoteException {
		try {
			return pasajeDAO.delete(idPasaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al eliminar pasaje", e);
		}
	}

	@Override
	public boolean modificarPasaje(Pasaje pasaje) throws RemoteException
	{
		try {
			return pasajeDAO.update(pasaje.getIdPasaje(),pasaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al eliminar pasaje", e);
		}
	}
	
	
	@Override
	public Pasaje consultarPasaje(int idPasaje) throws RemoteException {
		try {
			return pasajeDAO.getPasaje(idPasaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al consultar pasaje", e);
		}
	}

	
	/****************************
	 * crearBus(Bus)
	 * eliminarBus(idBus)
	 * modificarBus(Bus bus)
	 * getBus(matricula)
	 ****************************/
	
	
	@Override
	public boolean crearBus(Bus bus) throws RemoteException {
		try {
			return busDAO.insert(bus);
		} catch (SQLException e) {
			throw new RemoteException("Error al crear bus", e);
		}
	}
	
	public Bus getBus(String matricula) throws RemoteException {
		
		try {
			return busDAO.getBus(matricula);
		}
		catch (SQLException e)
		{
			throw new RemoteException("Error al obtener bus",e);
		}
	}

	@Override
	public boolean eliminarBus(String matricula) throws RemoteException {
		try {
			return busDAO.delete(matricula);
		} catch (SQLException e) {
			throw new RemoteException("Error al eliminar bus", e);
		}
	}

	@Override
	public boolean modificarBus(Bus bus) throws RemoteException {
		try {
			return busDAO.update(bus.getMatricula(), bus);
		} catch (SQLException e) {
			throw new RemoteException("Error al modificar bus", e);
		}
	}

	
	
	
	/***************************
	 * 
	 * Pasajeros
	 * crearPasajero(Pasajero)
	 * getPasajero(nombre,correo)
	 * getPasajero(idPasajero)
	 * eliminarPasajero(idPasajero)
	 * modificarPasajero(Pasajero) 
	 * 
	 */
	
	@Override
	public int crearPasajero(Pasajero pasajero) throws RemoteException {
		try {
			return pasajeroDAO.insert(pasajero);
		} catch (SQLException e) {
			throw new RemoteException("Error al crear pasajero", e);
		}
	}
	

	public Pasajero getPasajero(String nombre,String correo) throws RemoteException
	{
		try {
			return pasajeroDAO.getPasajero(nombre,correo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RemoteException("Error al buscar pasajero", e);
		}
	}
	
	
	@Override
	public Pasajero getPasajero(int idPasajero) throws RemoteException
	{
		try {
			return pasajeroDAO.getPasajero(idPasajero);
		} catch (SQLException e) {
			throw new RemoteException("Error al buscar pasajero", e);
		}
	}
	

	@Override
	public boolean eliminarPasajero(int idPasajero) throws RemoteException {
		try {
			return pasajeroDAO.delete(idPasajero);
		} catch (SQLException e) {
			throw new RemoteException("Error al eliminar pasajero", e);
		}
	}

	@Override
	public boolean modificarPasajero(Pasajero pasajero) throws RemoteException {
		try {
			return pasajeroDAO.update(pasajero.getIdPasajero(), pasajero);
		} catch (SQLException e) {
			throw new RemoteException("Error al modificar pasajero", e);
		}
	}

	
}
