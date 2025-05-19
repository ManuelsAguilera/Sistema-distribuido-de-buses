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
	public float consultarVentas()  {
			try {
				return pasajeDAO.consultaVentas();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		
	}


	@Override
	public boolean crearNuevoViaje(Viaje viaje, int idRuta) {
		
		try {
			Viaje viajeNuevo = viajeDAO.getViaje(viaje.getidViaje());
			puntoViajeDAO.fillPointsFromViaje(idRuta);
			viajeDAO.insert(viajeNuevo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}


	@Override
	public Viaje obtenerViaje(int idViaje) {
		try {
			return viajeDAO.getViaje(idViaje);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public boolean eliminarViaje(int idViaje) {
		try {
			return viajeDAO.delete(idViaje);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino) {
		try {
			return viajeDAO.getViajePorOrigen(origen,destino);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public ArrayList<Viaje> obtenerViajePorOrigen(String origen, String destino, LocalDate fecha) {
		try {
			return viajeDAO.getViajePorOrigen(origen,destino,fecha);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public ArrayList<Ruta> obtenerRutasDisp() {
		try {
			return rutaDAO.getAllRutas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
	public boolean eliminarPasaje(int idPasaje) {
		try {
			return pasajeDAO.delete(idPasaje);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public Pasaje consultarPasaje(int idPasaje) {
		try {
			return pasajeDAO.getPasaje(idPasaje);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean crearBus(String matricula, String modelo, int capacidad) {
		try {
			return busDAO.insert(new Bus(matricula,modelo,capacidad));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean eliminarBus(String matricula) {
		try {
			return busDAO.delete(matricula);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean modificarBus(Bus bus) {
		try {
			return busDAO.update(bus.getMatricula(), bus);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean crearPasajero(int idPasajero, String nombre, String correo) {
		try {
			return pasajeroDAO.insert(new Pasajero(idPasajero,nombre,correo));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean eliminarPasajero(int idPasajero) {
		try {
			return pasajeroDAO.delete(idPasajero);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean modificarPasajero(Pasajero pasajero) {
		try {
			return pasajeroDAO.update(pasajero.getIdPasajero(),pasajero);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}



	
}
