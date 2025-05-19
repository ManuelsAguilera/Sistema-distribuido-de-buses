package persistance.dao;

import java.sql.*;

import common.Bus;

public class BusDAO {

	private final Connection conn;
	
	
	public BusDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public Bus getBus(String matricula) throws SQLException
	{
		String sql = "SELECT * FROM buses WHERE buses.matricula = ?";
	
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, matricula);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            String modelo = rs.getString("modelo");
	            int capacidad = rs.getInt("capacidad");
	            return new Bus(matricula, modelo, capacidad); // Constructor de tu clase Bus
	        } else {
	            return null; // No se encontró ningún bus con esa matrícula
	        }
		}
	}
	
	public void insert(Bus bus) throws SQLException
	{
		String sql = "INSERT INTO buses (matricula,modelo,capacidad)\n"
					+"VALUES (?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, bus.getMatricula());
			stmt.setString(2, bus.getModelo());
			stmt.setInt(3, bus.getAsientosTotales());
			stmt.execute(sql);
		}
		
	}
	
	public Boolean delete(String matricula) throws SQLException
	{
		String sql = "DELETE FROM buses WHERE buses.matricula  = ?";			
		
		try (PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, matricula);
	        var rs = stmt.executeUpdate(); 
	        
	        //Filas actualizadas lol
	        return rs > 0;
		}
        
		
	}
	
	public Boolean update(String matricula, Bus bus) throws SQLException {
	    String sql = "UPDATE buses SET capacidad = ?, modelo = ? WHERE matricula = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql))
	    {
	    	stmt.setInt(1, bus.getAsientosTotales());
	        stmt.setString(2, bus.getModelo());
	        stmt.setString(3, matricula);
	        
			var rs = stmt.executeUpdate(); 
	        
	        //Filas actualizadas lol
	        return rs > 0;
	    }
        
	    
	}
	

	
}