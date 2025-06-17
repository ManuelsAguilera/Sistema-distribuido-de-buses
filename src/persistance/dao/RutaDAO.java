package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

import common.Ruta;
import server.DB;

public class RutaDAO {

	private final Connection conn;
	
	public RutaDAO(Connection conn)
	{
		this.conn = conn;
	}
	
	public Ruta getRuta(int idRuta) throws SQLException
	{
		String sql = "SELECT * from rutas WHERE ruta.ruta_id = ?";
		
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql))
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
	
	public boolean inTable(int idRuta) throws SQLException
	{
		String sql = "SELECT * from rutas WHERE ruta.ruta_id = ?";
		
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1,idRuta);
			var rs = stmt.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	
	public ArrayList<Ruta> getAllRutas() throws SQLException
	{
		String sql = "SELECT * from rutas ";
				
		try (Connection conn = DB.connect();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			var rs = stmt.executeQuery();
			
			ArrayList<Ruta> lista = new ArrayList<Ruta>();
			
			while (rs.next())
			{
				int idRuta = rs.getInt("ruta_id");
				String origen = rs.getString("origen");
				String destino = rs.getString("destino");
				LocalTime duracionEstimada = rs.getTime("duracion_estimada").toLocalTime();
				lista.add(new Ruta(origen, destino,idRuta, duracionEstimada));
			}
			
			return lista;
		}
	}
	
	
	public void insert(Ruta ruta) throws SQLException {
	    String sql = "INSERT INTO rutas (origen, destino, duracion_estimada) VALUES (?, ?, ?)";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, ruta.getNombreDestino());
	        stmt.setString(2, ruta.getNombreOrigen());
	        stmt.setTime(3, Time.valueOf(ruta.getDuracionEstimada())); 
	        stmt.executeUpdate();
	    }
	}

	
	public void update(int idRuta, Ruta ruta) throws SQLException {
	    String sql = "UPDATE rutas SET origen = ?, destino = ?, duracion_estimada = ? WHERE ruta_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, ruta.getNombreOrigen());
	        stmt.setString(2, ruta.getNombreDestino());
	        stmt.setTime(3, Time.valueOf(ruta.getDuracionEstimada()));
	        stmt.setInt(4, idRuta);

	        stmt.executeUpdate();
	    }
	}

	
	
	public boolean delete(int idRuta) throws SQLException {
	    String sql = "DELETE FROM rutas WHERE ruta_id = ?";

	    try (Connection conn = DB.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idRuta);
	        int filas = stmt.executeUpdate();
	        return filas > 0;
	    }
	}

}
