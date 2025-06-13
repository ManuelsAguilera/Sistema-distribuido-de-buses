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

	@Override
	public boolean crearPasaje(int idViaje, int idPasajero, int idOrigen, String destino, LocalDateTime fechaCompra,
			float precio, int asiento) throws RemoteException {
		try {
			Pasaje pasajeNuevo = new Pasaje(idPasajero, idOrigen, asiento, fechaCompra, precio, asiento, asiento);
			pasajeDAO.insert(pasajeNuevo);
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
	public Pasaje consultarPasaje(int idPasaje) throws RemoteException {
		try {
			return pasajeDAO.getPasaje(idPasaje);
		} catch (SQLException e) {
			throw new RemoteException("Error al consultar pasaje", e);
		}
	}

	@Override
	public boolean crearBus(String matricula, String modelo, int capacidad) throws RemoteException {
		try {
			return busDAO.insert(new Bus(matricula, modelo, capacidad));
		} catch (SQLException e) {
			throw new RemoteException("Error al crear bus", e);
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

	@Override
	public boolean crearPasajero(String nombre, String correo) throws RemoteException {
		try {
			return pasajeroDAO.insert(new Pasajero(nombre, correo));
		} catch (SQLException e) {
			throw new RemoteException("Error al crear pasajero", e);
		}
		return false;
	}
	
	public boolean getPasajero(String nombre,String correo) throws RemoteException
	{
		try {
			return pasajeroDAO.getPasajero(nombre,correo).getIdPasajero();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RemoteException("Error al buscar pasajero", e);
		}
		return false;
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
