package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import common.PuntoIntermedio;

public class PuntoIntermedioDAO {

	private final Connection conn;
	
	public PuntoIntermedioDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public PuntoIntermedio getPuntoIntermedio(int idPunto) throws SQLException
	{
		String sql = "SELECT * FROM puntosintermedios WHERE puntosintermedios.punto_id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPunto);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	        	LocalDateTime llegada= rs.getTimestamp("hora_llegada").toLocalDateTime();
	        	LocalDateTime salida= rs.getTimestamp("hora_salida").toLocalDateTime();
	            int idRuta = rs.getInt("ruta_id");
	            String nombre = rs.getString("nombre_punto");
	            float lat = rs.getFloat("lat");
        		float lon = rs.getFloat("long");
        		int orden = rs.getInt("orden");
	            return new PuntoIntermedio(salida, llegada,idPunto,idRuta,lat,lon,orden,nombre);
	        } else {
	            return null; // No se encontro ningun bus con esa matricula
	        }
		}
	}
	
	
	
	public Boolean insert(PuntoIntermedio punto) throws SQLException {
	    String sql = "INSERT INTO puntosintermedios (punto_id, ruta_id, nombre_punto, lat, long, orden, hora_salida, hora_llegada) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, punto.getIdPunto());
	        stmt.setInt(2, punto.getIdRuta());
	        stmt.setString(3, punto.getNombre());
	        stmt.setFloat(4, punto.getLat());
	        stmt.setFloat(5, punto.getLon());  
	        stmt.setInt(6, punto.getOrden());
	        stmt.setTimestamp(7, Timestamp.valueOf(punto.getHoraSalida()));
	        stmt.setTimestamp(8, Timestamp.valueOf(punto.getHoraLlegada()));

	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}	
	
	public boolean delete(int idPunto) throws SQLException {
	    String sql = "DELETE FROM puntosintermedios WHERE punto_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPunto);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}

}
