package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	
	  public static void main(String[] args) {
	        try {
	            String url = "jdbc:postgresql://localhost:5432/busDB";
	            String user = "bus_admin";
	            String password = "passwd";
	            Connection conn = DriverManager.getConnection(url, user, password);
	            System.out.println("¡Conectado exitosamente!");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	
    public static Connection connect() throws SQLException {
    	try {
    		 return connectLocal();
    	}
    	catch( SQLException e)
    	{
    		System.out.println("Conexion local no encontrada");
    		e.printStackTrace();
    		throw new SQLException();
    	}
    }
    
    
    private static Connection connectRemote() throws SQLException {
    	String url = "jdbc:postgresql://ep-delicate-violet-a44uyb4f-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require";
        String user = "neondb_owner";
        String password = "npg_4L1UhyYFoItR";
        return DriverManager.getConnection(url, user, password);
    }
    
    private static Connection connectLocal() throws SQLException {
    	String url = "jdbc:postgresql://localhost:5432/busDB";
        String user = "bus_admin";
        String password = "passwd";
        return DriverManager.getConnection(url, user, password);
    }
    

}