package persistance.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import common.Pasaje;
import server.DB;


public class PasajeDAO {

	private final Connection conn;
	
	
	public PasajeDAO(Connection conn)
	{
		this.conn = conn;
	}
	public boolean inTable(int idPasaje) throws SQLException {
		String sql = "SELECT * FROM pasajes WHERE pasaje_id = ?";
		
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPasaje);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return true;
	        } else {
	            return false;
	        }
	    }

	}
	
	public void insert(Pasaje pasaje) throws SQLException {
	    String sql = "INSERT INTO pasajes (viaje_id, pasajero_id, punto_origen_id, punto_destino_id, precio, fecha_compra, asiento) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, pasaje.getIdViaje());
	        stmt.setInt(2, pasaje.getIdPasajero());
	        stmt.setInt(3, pasaje.getIdOrigen());
	        stmt.setInt(4, pasaje.getIdDestino());
	        stmt.setFloat(5, pasaje.getPrecio()); 
	        stmt.setTimestamp(6, Timestamp.valueOf(pasaje.getFechaCompra())); 
	        stmt.setInt(7, pasaje.getAsiento());

	        stmt.executeUpdate();
	    }
	}

	public boolean update(int idPasaje, Pasaje pasaje ) throws SQLException {
	    String sql = "UPDATE pasajes SET viaje_id = ?, pasajero_id = ?, punto_origen_id = ?, " +
	                 "punto_destino_id = ?, precio = ?, fecha_compra = ?, asiento = ? " +
	                 "WHERE pasaje_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, pasaje.getIdViaje());
	        stmt.setInt(2, pasaje.getIdPasajero());
	        stmt.setInt(3, pasaje.getIdOrigen());
	        stmt.setInt(4, pasaje.getIdDestino());
	        stmt.setFloat(5, pasaje.getPrecio());
	        stmt.setTimestamp(6, Timestamp.valueOf(pasaje.getFechaCompra()));
	        stmt.setInt(7, pasaje.getAsiento());
	        stmt.setInt(8, idPasaje);

	        int filas =stmt.executeUpdate();
	        return filas >0;
	    }
	}

	
	public Boolean delete(int idPasaje) throws SQLException {
	    String sql = "DELETE FROM pasajes WHERE pasaje_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPasaje);
	        int filas = stmt.executeUpdate();
	        return filas > 0;
	    }
	}

	
	
	
	
	public Pasaje getPasaje(int idPasaje) throws SQLException {
	    String sql = "SELECT * FROM pasajes WHERE pasaje_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPasaje);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int viajeId = rs.getInt("viaje_id");
	            int pasajeroId = rs.getInt("pasajero_id");
	            int origenId = rs.getInt("punto_origen_id");
	            int destinoId = rs.getInt("punto_destino_id");
	            int precio = rs.getInt("precio"); 
	            LocalDateTime fechaCompra = rs.getTimestamp("fecha_compra").toLocalDateTime();
	            int asiento = rs.getInt("asiento");

	            return new Pasaje(pasajeroId, origenId, destinoId, fechaCompra, precio, asiento, viajeId);
	        } else {
	            return null;
	        }
	    }
	}
	
	
	public float consultaVentas() throws SQLException
	{
		String sql = "SELECT SUM(precio) FROM pasajes ";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        var rs = stmt.executeQuery(); 
	        if (rs.next())
	        	return rs.getFloat(1);
	        else
	        	return 0;
	    }
	}

	
}
