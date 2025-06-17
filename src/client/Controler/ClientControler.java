package client.Controler;

import client.MenuOptionListener;
import client.View.ClientView;
import common.Bus;
import common.IBusManager;
import common.Pasaje;
import common.Pasajero;
import common.PuntoIntermedio;
import common.Ruta;
import common.Viaje;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public class ClientControler implements MenuOptionListener {
    private ClientView view;
    private IBusManager server;
    private Thread heartbeatThread;
    private Thread watcherThread;

    

    public ClientControler(ClientView view, IBusManager server) throws Exception {
        this.view = view;
        this.view.setMenuOptionListener(this);
        this.server = server;
        iniciarHeartbeat();
    }
    
    public void testConnection(String message) throws RemoteException { this.server.testConnection(message);}
	
	
	@Override
	public void crearViaje(Viaje viaje, Ruta ruta) {
		try {
			server.crearNuevoViaje(viaje, ruta.getIdRuta());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void eliminarViaje(Viaje viaje) {
		try {
			server.eliminarViaje(viaje.getidViaje());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int crearPasajero(Pasajero pasajero) {
		try {
			return server.crearPasajero(pasajero);
		}  catch (RemoteException e) {
	        e.printStackTrace();
	    }
		return -1;
	}
	
	@Override
	public ArrayList<Viaje> obtenerViaje(String origen, String destino, LocalDate fecha) {
	    try {
			return this.server.obtenerViajePorOrigen(origen, destino, fecha);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void displayView() throws IOException {
		view.displayMenu();
	}

	@Override
	public ArrayList<Viaje> obtenerViaje(String origen, String destino) {
		try {
			return this.server.obtenerViajePorOrigen(origen, destino);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean eliminarPasajero(int idPasajero) {
		try {
			return server.eliminarPasajero(idPasajero);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean modificarPasajero(int idPasajero, Pasajero pasajero) {
		try {
			return server.modificarPasajero(pasajero);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Buses
	@Override
	public boolean crearBus(Bus bus) {
		try {
			return server.crearBus(bus);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Bus getBus(String matricula) {
		try {
			return server.getBus(matricula);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean eliminarBus(String matricula) {
		try {
			return server.eliminarBus(matricula);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificarBus(Bus bus) {
		try {
			return server.modificarBus(bus);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Puntos intermedios
	@Override
	public PuntoIntermedio consultarPuntoIntermedio(int idPunto) {
		try {
			return server.consultarPuntoIntermedio(idPunto);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Viajes
	// TODO: cambiar a boolean
	@Override
	public void modificarViaje(Viaje viaje) {
		try {
			server.modificarViaje(viaje);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Ruta> obtenerRutasDisp() {
		try {
			return server.obtenerRutasDisp();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean crearPasaje(Pasaje pasaje) {
		try {
			return server.crearPasaje(pasaje);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminarPasaje(int idPasaje) {
		try {
			return server.eliminarPasaje(idPasaje);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificarPasaje(Pasaje pasaje) {
		try {
			return server.modificarPasaje(pasaje);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Pasaje consultarPasaje(int idPasaje) {
		try {
			return server.consultarPasaje(idPasaje);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public IBusManager getServerActual() {
        return server;
    }

    private void iniciarHeartbeat() {
        StartHeartbeat heartbeat = new StartHeartbeat(this, server);
        heartbeatThread = new Thread(heartbeat);
        heartbeatThread.start();
    }

    public synchronized void cambiarAServerRespaldo() {
        try {
        	if (heartbeatThread != null) {
                heartbeatThread.interrupt();
            }

        	
        	Registry registry = LocateRegistry.getRegistry("localhost", 2001);
            IBusManager respaldo = (IBusManager) registry.lookup("CentralBusManagerRespaldo");
            this.server = respaldo;
            
            iniciarHeartbeat();
            
            StartPrincipalWatcher watcher = new StartPrincipalWatcher(this);
            watcherThread = new Thread(watcher);
            watcherThread.start();
            
            System.out.println("[Controler] Conectado al servidor de respaldo.");
        } catch (Exception e) {
            System.err.println("[Controler] Error al conectar al servidor de respaldo");
            e.printStackTrace();
        }
    }
    
    public synchronized void cambiarAServerPrincipal(IBusManager principal) {
        try {
            this.server = principal;

            System.out.println("[Controler] Reconectado al servidor principal.");

            // Detener watcher y lanzar nuevo heartbeat
            if (watcherThread != null) watcherThread.interrupt();
            iniciarHeartbeat();

        } catch (Exception e) {
            System.err.println("[Controler] Error reconectando al servidor principal: " + e.getMessage());
        }
    }



}
