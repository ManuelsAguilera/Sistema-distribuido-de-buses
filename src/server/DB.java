package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DB {
	  
		
	private static HikariDataSource dataSource;
	
	static {
		HikariConfig config = new HikariConfig();
	    config.setJdbcUrl("jdbc:postgresql://localhost:5432/busdb");
	    config.setUsername("bus_admin");
	    config.setPassword("passwd");
	    config.setMaximumPoolSize(10); // puedes ajustar según concurrencia esperada
	    config.setConnectionTimeout(30000); // 30 segundos

	    // Opcional: niveles de aislamiento
	    config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");

	    dataSource = new HikariDataSource(config);
	}
	
	
    /*public static Connection connect() throws SQLException {
    	try {
    		 return connectLocal();
    	}
    	catch( SQLException e)
    	{
    		System.out.println("Conexion local no encontrada");
    		e.printStackTrace();
    		throw new SQLException();
    	}
    }*/
    
    public static Connection connect() throws SQLException{
    	return dataSource.getConnection();
    }
    
    private static Connection connectRemote() throws SQLException {
    	String url = "jdbc:postgresql://ep-delicate-violet-a44uyb4f-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require";
        String user = "neondb_owner";
        String password = "npg_4L1UhyYFoItR";
        return DriverManager.getConnection(url, user, password);
    }
    
    private static Connection connectLocal() throws SQLException {
    	String url = "jdbc:postgresql://localhost:5432/busdb";
        String user = "bus_admin";
        String password = "passwd";
        return DriverManager.getConnection(url, user, password);
    }
    

}