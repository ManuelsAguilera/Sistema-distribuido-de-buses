package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Bus;
import domain.Terminal;
import persistance.dao.BusDAO;

public class ConexionNeonPostgres {
    public static void main(String[] args) {
    	
    	try {
			Connection conn = DB.connect();
			BusDAO dao = new BusDAO((Connection) conn);
			
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