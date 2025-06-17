package server;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class BusManagerRunRespaldo {
	public static void main(String[] args) 
	{
		try {
			
			BusManagerImpl serverRespaldo = new BusManagerImpl(DB.connect());
			Registry registry = LocateRegistry.createRegistry(2001);
			registry.bind("CentralBusManagerRespaldo", serverRespaldo);
			
			
			// Estado del servidor
			System.out.println("Backup Server up!");
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
