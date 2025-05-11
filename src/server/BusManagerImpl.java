package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public class BusManagerImpl extends UnicastRemoteObject  implements IBusManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected BusManagerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void testConnection(String message)  throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println("Client is connected!\n"+message);
	}

}
