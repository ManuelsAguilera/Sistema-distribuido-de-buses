package server;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class BusManagerRun {
	public static void main(String[] args) 
	{
		try {
			
			BusManagerImpl serverPrincipal = new BusManagerImpl(DB.connect());
			Registry registry = LocateRegistry.createRegistry(2002);
			registry.bind("CentralBusManager", serverPrincipal);
			
			// Estado del servidor
			System.out.println("Main Server up!");
		}
		catch (RemoteException RemoteE)
		{
			RemoteE.printStackTrace();
		}
		
		catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
