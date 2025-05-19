package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import common.PuntoViaje;

public class PuntoViajeDao {

	private final Connection conn;

	public PuntoViajeDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	
	public void insert(PuntoViaje puntoViaje) throws SQLException {
	    String sql = "INSERT INTO puntosintermedios_viaje (viaje_id, punto_id, hora_llegada, hora_salida, hora_llegada_estimada) \n" +
	                 "VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, puntoViaje.getIdViaje());
	        stmt.setInt(2, puntoViaje.getIdPunto());
	        stmt.setTimestamp(3, puntoViaje.getHoraLlegada() != null ? Timestamp.valueOf(puntoViaje.getHoraLlegada()) : null);
	        stmt.setTimestamp(4, puntoViaje.getHoraSalida() != null ? Timestamp.valueOf(puntoViaje.getHoraSalida()) : null);
	        stmt.setTime(5, puntoViaje.getHoraLlegadaEstimada() != null ? Time.valueOf(puntoViaje.getHoraLlegadaEstimada().toLocalTime()) : null);

	        stmt.executeUpdate();
	    }
	}
	
	public void fillPointsFromViaje(int idViaje) throws SQLException {
		String sql = "INSERT INTO puntosintermedios_viaje (viaje_id, punto_id, hora_llegada, hora_salida, hora_llegada_estimada)\n"
				+ "SELECT\n"
				+ "    v.viaje_id,\n"
				+ "    pi.punto_id,\n"
				+ "    NULL::timestamp,\n"
				+ "    NULL::timestamp,\n"
				+ "    NULL::time\n"
				+ "FROM viajes v\n"
				+ "JOIN puntosintermedios pi ON pi.ruta_id = v.ruta_id\n"
				+ "WHERE v.viaje_id = ?";
			
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, idViaje);
				stmt.executeUpdate();
			} 
	}
	
	public void update(PuntoViaje puntoViaje) throws SQLException {
	    String sql = "UPDATE puntosintermedios_viaje SET hora_llegada = ?, hora_salida = ?, hora_llegada_estimada = ? \n" +
	                 "WHERE viaje_id = ? AND punto_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setTimestamp(1, puntoViaje.getHoraLlegada() != null ? Timestamp.valueOf(puntoViaje.getHoraLlegada()) : null);
	        stmt.setTimestamp(2, puntoViaje.getHoraSalida() != null ? Timestamp.valueOf(puntoViaje.getHoraSalida()) : null);
	        stmt.setTime(3, puntoViaje.getHoraLlegadaEstimada() != null ? Time.valueOf(puntoViaje.getHoraLlegadaEstimada().toLocalTime()) : null);
	        stmt.setInt(4, puntoViaje.getIdViaje());
	        stmt.setInt(5, puntoViaje.getIdPunto());

	        stmt.executeUpdate();
	    }
	}
	public void delete(int idViaje, int idPunto) throws SQLException {
	    String sql = "DELETE FROM puntosintermedios_viaje WHERE viaje_id = ? AND punto_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        stmt.setInt(2, idPunto);

	        stmt.executeUpdate();
	    }
	}
	
}
