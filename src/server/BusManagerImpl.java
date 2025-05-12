package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;



public class BusManagerImpl extends UnicastRemoteObject  implements IBusManager {

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
	
	
	public ArrayList<String> dumpTerminal() throws RemoteException {
		
		
		System.out.println("Accediendo base de datos");
		ArrayList<String> names = new ArrayList<String>();
		var sql = " SELECT nombre,ciudad FROM terminal ORDER BY nombre";
		try (var conn =  DB.connect();
	             var stmt = conn.createStatement()) {
	            
	            var rs = stmt.executeQuery(sql);
	            
	            while (rs.next())
	            {
	            	String name = rs.getString("nombre")+ " "+ rs.getString("ciudad");
	            	names.add(name);
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }
		
		return names;
	}
}
