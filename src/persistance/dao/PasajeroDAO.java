package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Pasajero;

public class PasajeroDAO {
	
	
	private final Connection conn;
	
	public PasajeroDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public void insert(Pasajero pasajero) throws SQLException {
	    String sql = "INSERT INTO pasajeros (nombre, correo) VALUES (?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, pasajero.getNombre());
	        stmt.setString(2, pasajero.getCorreo());
	        stmt.executeUpdate();
	    }
	}

	
	public boolean update(int idPasajero, Pasajero pasajero) throws SQLException {
	    String sql = "UPDATE pasajeros SET nombre = ?, correo = ? WHERE pasajero_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, pasajero.getNombre());
	        stmt.setString(2, pasajero.getCorreo());
	        stmt.setInt(3, idPasajero);
	        return stmt.executeUpdate() > 0;
	    }
	}

	
	public Pasajero getPasajero(int idPasajero) throws SQLException
	{
		String sql = "SELECT * FROM pasajeros WHERE pasajero_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPasajero);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String correo = rs.getString("correo");
	            return new Pasajero(idPasajero, nombre, correo);
	        } else {
	            return null;
	        }
	    }
	}
	
	
	public boolean delete(int idPasajero) throws SQLException {
	    String sql = "DELETE FROM pasajeros WHERE pasajero_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPasajero);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}

	
	
	

}
