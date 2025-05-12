package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface IBusManager extends  Remote  {

	
	//Aqui irian las funciones que accede el cliente al servidor
	

	void testConnection(String message) throws RemoteException;
	ArrayList<String> dumpTerminal() throws RemoteException;
}
