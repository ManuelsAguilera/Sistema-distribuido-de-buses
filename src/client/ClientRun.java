package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bit.datacron.linkedIn.tms.map.Location;
import client.View.ClientView;
import server.ApiManager;

public class ClientRun {
	
	public static void main(String args[]) throws IOException
	{
		
		System.out.println("AAAAAAAAAA");
		
		Scanner scan = new Scanner(System.in);		
		ClientImpl client;
		ClientView view;
		
		try {
				client = new ClientImpl();
				view = new ClientView();
				System.out.println("Client is up!");
				
				view.displayData();
				
				
				String buffer= "";
				
				System.out.println("Escribe algo para enviar al servidor\nEscribe quit para salir.");
				while(buffer.compareTo("quit") != 0) {
					
					
					buffer = scan.nextLine();
					
					client.testConnection(buffer);
					
					
					
					
				} 
				
				System.out.println("Quitting..");
				
						
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
