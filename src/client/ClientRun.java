package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client.Controler.ClientControler;
import client.View.ClientView;
import common.IBusManager;
import server.ApiManager;

public class ClientRun {

	public static void main(String args[]) throws Exception {

		Scanner scan = new Scanner(System.in);
		ClientControler controler;
		ClientView view;
		ApiManager api = new ApiManager();
		Registry registry = LocateRegistry.getRegistry(2002);
		IBusManager server = (IBusManager) registry.lookup("CentralBusManager");

		try {

			view = new ClientView();
			controler = new ClientControler(view,server);
			System.out.println("Client is up!");

			controler.displayView();

			String buffer = "";

			System.out.println("Escribe algo para enviar al servidor\nEscribe quit para salir.");
			while (buffer.compareTo("quit") != 0) {

				buffer = scan.nextLine();

				controler.testConnection(buffer);

				if (buffer.compareTo("names") == 0) {
					// client.getNamesTerminal();
				}

			}

			System.out.println("Quitting..");
						
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

	}

}
