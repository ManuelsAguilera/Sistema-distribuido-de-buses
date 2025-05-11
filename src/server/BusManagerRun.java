package server;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BusManagerRun {
	public static void main(String[] args) 
	{
		try
		{
			BusManagerImpl server = new BusManagerImpl();
			Registry registry = LocateRegistry.createRegistry(2002);
			registry.bind("CentralBusManager", server);
			System.out.println("Server up!");
			
			
		}
		catch (RemoteException RemoteE)
		{
			RemoteE.printStackTrace();
		}
		
		catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
