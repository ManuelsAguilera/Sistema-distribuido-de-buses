package persistance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import common.Viaje;
import server.DB;

public class ViajeDAO {

	private final Connection conn;
	
	public ViajeDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public int getAsientosDispViaje(int idViaje) throws SQLException
	{
		String sql = "SELECT\n"
				+ "    v.viaje_id,\n"
				+ "    b.matricula,\n"
				+ "    b.modelo,\n"
				+ "    b.capacidad,\n"
				+ "    v.fecha,\n"
				+ "\n"
				+ "    b.capacidad - COUNT(pas.pasaje_id) AS asientos_disponibles\n"
				+ "FROM viajes v\n"
				+ "JOIN buses b ON v.matricula = b.matricula\n"
				+ "JOIN puntosintermedios po ON po.ruta_id = v.ruta_id AND po.nombre_punto ILIKE 'Salamanca'\n"
				+ "JOIN puntosintermedios pd ON pd.ruta_id = v.ruta_id AND pd.nombre_punto ILIKE 'Illapel'\n"
				+ "LEFT JOIN pasajes pas ON pas.viaje_id = v.viaje_id\n"
				+ "    AND pas.punto_origen_id IN (\n"
				+ "        SELECT punto_id FROM puntosintermedios\n"
				+ "        WHERE ruta_id = v.ruta_id AND orden <= po.orden\n"
				+ "    )\n"
				+ "    AND pas.punto_destino_id IN (\n"
				+ "        SELECT punto_id FROM puntosintermedios\n"
				+ "        WHERE ruta_id = v.ruta_id AND orden >= pd.orden\n"
				+ "    )\n"
				+ "WHERE po.orden < pd.orden AND v.viaje_id = ?\n"
				+ "GROUP BY v.viaje_id, b.matricula, b.modelo, b.capacidad, v.fecha\n"
				+ "ORDER BY v.fecha;";

		
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        var rs = stmt.executeQuery();
	        
	        return rs.getInt("asientos_disponibles");
	    }
		
	}
	
	public Viaje getViaje(int idViaje) throws SQLException {
	    String sql = "SELECT * FROM viajes WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	        	int viajeId = rs.getInt("viaje_id");
	            int rutaId = rs.getInt("ruta_id");
	            String matricula = rs.getString("matricula");
	            LocalDate fecha = rs.getDate("fecha").toLocalDate();
	            LocalTime salida = rs.getTime("hora_salida").toLocalTime();
	            LocalTime salidaEstimada = rs.getTime("hora_salida_estimada").toLocalTime();
	            return new Viaje(viajeId, rutaId, matricula, fecha, salida, salidaEstimada);
	        } else {
	            return null;
	        }
	    }
	}
	
	public boolean inTable(int idViaje) throws SQLException {
	    String sql = "SELECT * FROM viajes WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            return true;
	        } else {
	            return false;
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
	
	

	
	public Boolean delete(int idViaje) throws SQLException {
	    String sql = "DELETE FROM viajes WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idViaje);
	        int filas = stmt.executeUpdate();
	        return filas > 0;
	    }
	}
	
	
	public void update(int idViaje, Viaje viaje) throws SQLException {
	    String sql = "UPDATE viajes SET ruta_id = ?, matricula = ?, fecha = ?, hora_salida = ?, hora_salida_estimada = ? " +
	                 "WHERE viaje_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, viaje.getIdRuta());
	        stmt.setString(2, viaje.getMatricula());
	        stmt.setDate(3, Date.valueOf(viaje.getFecha()));
	        stmt.setTime(4, Time.valueOf(viaje.getSalida()));
	        stmt.setTime(5, Time.valueOf(viaje.getSalidaEstimada()));
	        stmt.setInt(6, idViaje);

	        stmt.executeUpdate();
	    }
	}
	
	public ArrayList<Viaje> getViajePorOrigen(String Origen, String Destino) throws SQLException
	{
		String sql = "SELECT DISTINCT\n"
				+"		v.viaje_id,\n"
				+"		v.ruta_id,\n"
				+ "    v.matricula,\n"
				+ "    v.fecha,\n"
				+ "    v.hora_salida,\n"
				+ "    v.hora_salida_estimada"
				+ "\n"
				+ "FROM viajes v\n"
				+ "JOIN buses b ON v.matricula = b.matricula\n"
				+ "JOIN puntosintermedios po ON po.ruta_id = v.ruta_id AND po.nombre_punto = ?\n"
				+ "JOIN puntosintermedios pd ON pd.ruta_id = v.ruta_id AND pd.nombre_punto = ?\n"
				+ "WHERE\n"
				+ "    po.orden < pd.orden\n"
				+ "ORDER BY v.fecha;";
		
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, Origen);
	        stmt.setString(2,Destino);
	        var rs = stmt.executeQuery();

	        ArrayList<Viaje> lista = new ArrayList<Viaje>();
	        
	        while (rs.next()) {
	        	int viajeId = rs.getInt("viaje_id");
	            int rutaId = rs.getInt("ruta_id");
	            String matricula = rs.getString("matricula");
	            LocalDate fecha = rs.getDate("fecha").toLocalDate();
	            LocalTime salida = rs.getTime("hora_salida").toLocalTime();
	            
	            // Este podria ser null :/
	            // Concuerdo
	            Time horaSalidaEstimadaSQL = rs.getTime("hora_salida_estimada");
	            LocalTime salidaEstimada = (horaSalidaEstimadaSQL != null) ? horaSalidaEstimadaSQL.toLocalTime() : null;
	            
	            lista.add(new Viaje(viajeId,rutaId,matricula,fecha,salida,salidaEstimada));
	        }
	        
	        return lista;
	    	}
	    }
		
	/*
	 public ArrayList<Viaje> getViajePorOrigen(String Origen, String Destino, LocalDate fecha) throws SQLException {
    String sql = "SELECT DISTINCT ...";
    try (Connection conn = DB.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, Origen);
        stmt.setString(2, Destino);
        stmt.setDate(3, Date.valueOf(fecha));

        var rs = stmt.executeQuery();
        ArrayList<Viaje> lista = new ArrayList<>();
        while (rs.next()) {
            // construir objeto viaje
        }
        return lista;
    }
}
	 */	
	public ArrayList<Viaje> getViajePorOrigen(String Origen, String Destino,LocalDate fecha) throws SQLException
	{
		String sql = "SELECT DISTINCT\n"
				+"		v.viaje_id,\n"
				+"		v.ruta_id,\n"
				+ "    v.matricula,\n"
				+ "    v.fecha,\n"
				+ "    v.hora_salida,\n"
				+ "    v.hora_salida_estimada"
				+ "\n"
				+ "FROM viajes v\n"
				+ "JOIN buses b ON v.matricula = b.matricula\n"
				+ "JOIN puntosintermedios po ON po.ruta_id = v.ruta_id AND po.nombre_punto = ?\n"
				+ "JOIN puntosintermedios pd ON pd.ruta_id = v.ruta_id AND pd.nombre_punto = ?\n"
				+ "WHERE\n"
				+ "    po.orden < pd.orden AND v.fecha = ?\n"
				+ "ORDER BY v.fecha;";
		
	    try (Connection conn = DB.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, Origen);
	        stmt.setString(2,Destino);
	        stmt.setDate(3, Date.valueOf(fecha));
	        var rs = stmt.executeQuery();

	        ArrayList<Viaje> lista = new ArrayList<Viaje>();
	        
	        while (rs.next()) {
	        	int viajeId = rs.getInt("viaje_id");
	            int rutaId = rs.getInt("ruta_id");
	            String matricula = rs.getString("matricula");
	            LocalDate fechaViaje = rs.getDate("fecha").toLocalDate();
	            LocalTime salida = rs.getTime("hora_salida").toLocalTime();
	            //En caso de null comprobar
	            Time horaSalidaEstimadaSQL = rs.getTime("hora_salida_estimada");
	            LocalTime salidaEstimada = (horaSalidaEstimadaSQL != null) ? horaSalidaEstimadaSQL.toLocalTime() : null;
	            
	            lista.add(new Viaje(viajeId, rutaId, matricula, fechaViaje, salida, salidaEstimada));
	        }
	        
	        return lista;
	    }
	}


	
	
}
