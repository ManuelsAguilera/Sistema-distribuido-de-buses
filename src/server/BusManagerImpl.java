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
	public boolean consultarVentas() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean crearNuevoViaje(int idViaje, int idRuta, String matricula, LocalDate fecha, LocalTime horaSalida, LocalTime horaSalidaEstimada) {
		try {
            Viaje nuevoViaje = new Viaje(idViaje, idRuta, matricula, fecha, horaSalida, horaSalidaEstimada);
            viajeDAO.insert(nuevoViaje);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean obtenerViaje(int idViaje) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean eliminarViaje(int idViaje) {
		try {
            viajeDAO.delete(idViaje);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean crearNuevaRuta(int idRuta, String nombreOrigen, String nombreDestino, LocalTime duracionEstimada) {
		try {
            Ruta nuevaRuta = new Ruta(nombreOrigen, nombreDestino, idRuta, duracionEstimada);
            rutaDAO.insert(nuevaRuta);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean consultarRutas(String origen, String destino) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean consultarRuta(int idRuta) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean modificarRuta(int idRuta) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean eliminarRuta(int idRuta) {
		try {
            rutaDAO.delete(idRuta);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean crearPuntoIntermedio(int idPunto, int idRuta, float longitud, float latitud, String nombrePunto,
			int orden, LocalDateTime horaLlegada, LocalDateTime horaSalida) {
		try {
            PuntoIntermedio nuevoPuntoIntermedio = new PuntoIntermedio(horaSalida, horaLlegada, idPunto, idRuta, longitud, latitud, orden, nombrePunto);
            puntoIntermedioDAO.insert(nuevoPuntoIntermedio);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean consultarPuntoIntermedio(int idPunto) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean eliminarPuntoIntermedio(int idPunto) {
		try {
            puntoIntermedioDAO.delete(idPunto);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean crearPasaje(int idPasaje, int idViaje, int idPasajero, int idPuntoOrigen, int idPuntoDestino,
			LocalDateTime fechaCompra, float precio, int asiento) {
		try {
            Pasaje nuevoPasaje = new Pasaje(idPasajero, idPuntoOrigen, idPuntoDestino, fechaCompra, precio, asiento, idViaje);
            pasajeDAO.insert(nuevoPasaje);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean eliminarPasaje(int idPasaje) {
		try {
            pasajeDAO.delete(idPasaje);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean consultarPasaje(int idPasaje) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean consultarPasajes(String puntoOrigen, String puntoDestino) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean crearBus(String matricula, String modelo, int capacidad) {
		try {
            Bus nuevoBus = new Bus(matricula, modelo, capacidad);
            busDAO.insert(nuevoBus);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean eliminarBus(String matricula) {
		try {
            busDAO.delete(matricula);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean modificarBus(String idBus) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean notificarSalidaDeBus(String matricula) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean notificarLlegadaDeBus(String matricula) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean crearPasajero(int idPasajero, String nombre, String correo) {
		try {
            Pasajero nuevoPasajero = new Pasajero(idPasajero, nombre, correo);
            pasajeroDAO.insert(nuevoPasajero);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean eliminarPasajero(int idPasajero) {
		try {
            pasajeroDAO.delete(idPasajero);
            return true;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
	}



	@Override
	public boolean modificarPasajero(int idPasajero) {
		// TODO Auto-generated method stub
		return false;
	}



	
}
