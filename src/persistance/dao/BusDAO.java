package persistance.dao;

import java.sql.*;
import domain.Bus;

public class BusDAO {

	private final Connection conn;
	
	
	public BusDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public void insert(Bus bus) throws SQLException
	{
		String sql = "INSERT INTO buses (matricula,modelo,capacidad)\n"
					+ String.format("VALUES ('%s','%s',%d)",bus.getPatente(),bus.getModelo(),bus.getAsientosTotales());
		
		var stmt= conn.createStatement();
		
		stmt.execute(sql);
		
	}
	
	public void delete(String matricula) throws SQLException
	{
		String sql = "DELETE FROM buses WHERE buses.matricula  = ?";			
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, matricula);
	        stmt.executeUpdate(); 
	    }
		
	}
	
	
}