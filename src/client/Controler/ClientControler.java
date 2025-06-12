package client.Controler;

import client.MenuOptionListener;
import client.View.ClientView;
import common.Bus;
import common.IBusManager;
import common.Viaje;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public class ClientControler implements MenuOptionListener {
    private ClientView view;
    private IBusManager server; // Modelo
    

    public ClientControler(ClientView view, IBusManager server) throws Exception {
        this.view = view;
        this.view.setMenuOptionListener(this);
        this.server = server;
        Registry registry = LocateRegistry.getRegistry(2002);
		server =(IBusManager) registry.lookup("CentralBusManager");
    }
    
    public void testConnection(String message) throws RemoteException { this.server.testConnection(message);}
	
	@Override
	public void onMenuOptionSelected(int opcion) {
		switch (opcion) {
			case 1:
				onCrearPasajero(null, null);
		}
		
	}
	
	@Override
	public void onCrearPasajero (String nombre, String correo) {
		try {
			server.crearPasajero(nombre, correo);
		}  catch (RemoteException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public ArrayList<Viaje> obtenerViaje(String origen, String destino, LocalDate fecha) {
	    try {
			return this.server.obtenerViajePorOrigen(origen, destino, fecha);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
