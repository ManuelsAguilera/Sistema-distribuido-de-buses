package client;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bit.datacron.linkedIn.tms.map.Location;
import common.IBusManager;
import server.ApiManager;

public class ClientModel {
    private IBusManager server;
    
    public ClientModel() throws Exception {
    	//Registry registry = LocateRegistry.getRegistry(2002);
		//server =(IBusManager) registry.lookup("CentralBusManager");
    }

    public String crearPasaje(String origen, String destino, String nombrePasajero, String correo) throws Exception {
        return "a"; //servidor.crearPasaje(origen, destino, nombrePasajero);
    }

    public boolean cancelarPasaje(String idPasaje) throws Exception {
        return true; //servidor.cancelarPasaje(idPasaje);
    }
    
    public boolean modificarPasaje(String idPasaje, String asiento) throws Exception {
        return true; //servidor.cancelarPasaje(idPasaje);
    }

    public boolean notificarSalida(String idBus) throws Exception {
        return true; //servidor.notificarSalida(idBus);
    }

    public boolean notificarLlegada(String idBus) throws Exception {
        return true; //servidor.notificarLlegada(idBus);
    }

}
