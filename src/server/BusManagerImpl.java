package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public class BusManagerImpl extends UnicastRemoteObject  implements IBusManager {

	protected BusManagerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
