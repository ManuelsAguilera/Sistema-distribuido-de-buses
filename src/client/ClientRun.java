package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRun {
	
	public static void main(String args[])
	{
		
		Scanner scan = new Scanner(System.in);		
		ClientImpl client;
		
		try {
				client = new ClientImpl();
				System.out.println("Client is up!");
				
				
				
				String buffer= "";
				
				System.out.println("Escribe algo para enviar al servidor\nEscribe quit para salir.");
				while(buffer.compareTo("quit") != 1) {
					
					
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
