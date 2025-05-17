package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common.Bus;
import common.Terminal;
import server.dao.DaoBus;

public class ConexionNeonPostgres {
    public static void main(String[] args) {
    	
    	try {
			Connection conn = DB.connect();
			DaoBus dao = new DaoBus((Connection) conn);
			
			Bus bus = new Bus("BB2","Bus largo",15,new ArrayList<Terminal>());
			
			dao.insert(bus);
			System.out.println("Escribe cualquier numero para eliminar");
			Scanner scan = new Scanner(System.in);
			
			scan.nextInt();
			
			
			dao.delete(bus.getPatente());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
    }
}