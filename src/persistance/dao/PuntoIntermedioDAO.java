package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import common.PuntoIntermedio;
import server.DB;

public class PuntoIntermedioDAO {

	private final Connection conn;
	
	public PuntoIntermedioDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public PuntoIntermedio getPuntoIntermedio(int idPunto) throws SQLException
	{
		String sql = "SELECT * FROM puntosintermedios WHERE puntosintermedios.punto_id = ?";
		
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPunto);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            int idRuta = rs.getInt("ruta_id");
	            String nombre = rs.getString("nombre_punto");
	            float lat = rs.getFloat("lat");
        		float lon = rs.getFloat("long");
        		int orden = rs.getInt("orden");
	            return new PuntoIntermedio(idPunto,idRuta,lat,lon,orden,nombre);
	        } else {
	            return null; // No se encontro ningun bus con esa matricula
	        }
		}
	}
	
	public ArrayList<PuntoIntermedio> getPuntoIntermedio() {
		return null;
		
	}
	
	public boolean inTable(int idPunto) throws SQLException
	{
		String sql = "SELECT * FROM puntosintermedios WHERE puntosintermedios.punto_id = ?";
		
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPunto);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return true;
	        } else {
	            return false; 
	        }
		}
	}
	
	
	
	public Boolean insert(PuntoIntermedio punto) throws SQLException {
	    String sql = "INSERT INTO puntosintermedios (punto_id, ruta_id, nombre_punto, lat, long, orden, hora_salida, hora_llegada) \n" +
	                 "VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, punto.getIdPunto());
	        stmt.setInt(2, punto.getIdRuta());
	        stmt.setString(3, punto.getNombre());
	        stmt.setFloat(4, punto.getLat());
	        stmt.setFloat(5, punto.getLon());  
	        stmt.setInt(6, punto.getOrden());


	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}	
	
	
	public boolean update(int idPunto, PuntoIntermedio punto) throws SQLException {
	    String sql = "UPDATE puntosintermedios SET ruta_id = ?, nombre_punto = ?, lat = ?, long = ?, orden = ?\n" +
	                 "WHERE punto_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, punto.getIdRuta());
	        stmt.setString(2, punto.getNombre());
	        stmt.setFloat(3, punto.getLat());
	        stmt.setFloat(4, punto.getLon());
	        stmt.setInt(5, punto.getOrden());
	        stmt.setInt(8, idPunto);

	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}

	
	public boolean delete(int idPunto) throws SQLException {
	    String sql = "DELETE FROM puntosintermedios WHERE punto_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPunto);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}

}
