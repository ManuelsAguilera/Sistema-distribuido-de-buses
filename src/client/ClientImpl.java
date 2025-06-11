package client;

import common.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


public class ClientImpl {
	
	IBusManager server;
	
	public ClientImpl() throws RemoteException, NotBoundException
	{
		Registry registry = LocateRegistry.getRegistry(2002);
		server=(IBusManager) registry.lookup("CentralBusManager");
	}
	
	void testConnection(String message) throws RemoteException { this.server.testConnection(message);}
	
	public ArrayList<Viaje> obtenerViaje(String origen, String destino,LocalDate fecha)
	{
		
		try {
			return this.server.obtenerViajePorOrigen(origen, destino,fecha);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int registrarPasajero(String nombre,String correo)
	{
				
		try {
			return this.server.crearPasajero(nombre, correo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Pasajero ya registrado");
			return -1;
		}
		

	}

	public int buscarPasajero(String nombre, String correo) {
		// TODO Auto-generated method stub
		try {
			return server.getPasajero(nombre,correo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
}
