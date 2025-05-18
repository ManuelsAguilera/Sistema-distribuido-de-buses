package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import common.Ruta;

public class RutaDAO {

	private final Connection conn;
	
	public RutaDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	public Ruta getRuta(int idRuta) throws SQLException
	{
		String sql = "SELECT * from rutas WHERE ruta.ruta_id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1,idRuta);
			var rs = stmt.executeQuery();
			
			if (rs.next())
			{
				String origen = rs.getString("origen");
				String destino = rs.getString("destino");
				LocalTime duracionEstimada = rs.getTime("duracion_estimada").toLocalTime();
				return new Ruta(origen, destino, idRuta, duracionEstimada);
			}
			else
			{
				return null;
			}
		}
	}
	
	
	
	public void insert(Ruta ruta) throws SQLException {
	    String sql = "INSERT INTO rutas (origen, destino, duracion_estimada) VALUES (?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, ruta.getNombreDestino());
	        stmt.setString(2, ruta.getNombreOrigen());
	        stmt.setTime(3, Time.valueOf(ruta.getDuracionEstimada())); // LocalTime → java.sql.Time
	        stmt.executeUpdate();
	    }
	}

	
	public boolean delete(int idRuta) throws SQLException {
	    String sql = "DELETE FROM rutas WHERE ruta_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idRuta);
	        int filas = stmt.executeUpdate();
	        return filas > 0;
	    }
	}

}
