package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import server.ApiManager;

public class ClientRun {
	
	public static void main(String args[]) throws IOException
	{
		
		Scanner scan = new Scanner(System.in);		
		ClientImpl client;
		ClientView view;
		
		try {
				client = new ClientImpl();
				view = new ClientView();
				System.out.println("Client is up!");
				
				view.showMenu();
				
				
				String buffer= "";
				
				System.out.println("Escribe algo para enviar al servidor\nEscribe quit para salir.");
				while(buffer.compareTo("quit") != 0) {
					
					
					buffer = scan.nextLine();
					
					client.testConnection(buffer);
					
					if (buffer.compareTo("names") == 0)
					{
						client.getNamesTerminal();
					}
					
					
				} 
				
				System.out.println("Quitting..");
				
						
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
