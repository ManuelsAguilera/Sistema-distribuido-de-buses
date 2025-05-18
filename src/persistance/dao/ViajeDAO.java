package persistance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import common.Viaje;

public class ViajeDAO {

	private final Connection conn;
	
	public ViajeDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public Viaje getViaje(int idViaje) throws SQLException {
	    String sql = "SELECT * FROM viajes WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            int rutaId = rs.getInt("ruta_id");
	            String matricula = rs.getString("matricula");
	            LocalDate fecha = rs.getDate("fecha").toLocalDate();
	            LocalTime salida = rs.getTime("hora_salida").toLocalTime();
	            LocalTime salidaEstimada = rs.getTime("hora_salida_estimada").toLocalTime();

	            return new Viaje(rutaId, matricula, fecha, salida, salidaEstimada);
	        } else {
	            return null;
	        }
	    }
	}
	
	public void insert(Viaje viaje) throws SQLException {
	    String sql = "INSERT INTO viajes (ruta_id, matricula, fecha, hora_salida, hora_salida_estimada) " +
	                 "VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, viaje.getIdRuta());
	        stmt.setString(2, viaje.getMatricula());
	        stmt.setDate(3, Date.valueOf(viaje.getFecha()));               
	        stmt.setTime(4, Time.valueOf(viaje.getSalida()));              
	        stmt.setTime(5, Time.valueOf(viaje.getSalidaEstimada()));     
	        stmt.executeUpdate();
	    }
	}

	
	public boolean delete(int idViaje) throws SQLException {
	    String sql = "DELETE FROM viajes WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        int filas = stmt.executeUpdate();
	        return filas > 0;
	    }
	}

	
}
