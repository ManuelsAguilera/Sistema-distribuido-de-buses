package persistance.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import common.PuntoIntermedio;
import server.DB;

public class PuntoIntermedioDAO {

    private final Connection conn;
    private static final ReentrantLock lock = new ReentrantLock();

    public PuntoIntermedioDAO(Connection conn) {
        this.conn = conn;
    }

    public PuntoIntermedio getPuntoIntermedio(int idPunto) throws SQLException {
        String sql = "SELECT * FROM puntosintermedios WHERE punto_id = ?";

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
                return new PuntoIntermedio(idPunto, idRuta, lat, lon, orden, nombre);
            } else {
                return null;
            }
        }
    }

    public ArrayList<PuntoIntermedio> getPuntoIntermedio() {
        return null;
    }

    public boolean inTable(int idPunto) throws SQLException {
        String sql = "SELECT * FROM puntosintermedios WHERE punto_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPunto);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public Boolean insert(PuntoIntermedio punto) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT PUNTO).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT PUNTO");
            Thread.sleep(8000);

            String sql = "INSERT INTO puntosintermedios (punto_id, ruta_id, nombre_punto, lat, long, orden) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, punto.getIdPunto());
                stmt.setInt(2, punto.getIdRuta());
                stmt.setString(3, punto.getNombre());
                stmt.setFloat(4, punto.getLat());
                stmt.setFloat(5, punto.getLon());
                stmt.setInt(6, punto.getOrden());

                int filasAfectadas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción de punto completada.");
                return filasAfectadas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT PUNTO");
            lock.unlock();
        }
    }

    public boolean update(int idPunto, PuntoIntermedio punto) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE PUNTO).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE PUNTO");
            Thread.sleep(8000);

            String sql = "UPDATE puntosintermedios SET ruta_id = ?, nombre_punto = ?, lat = ?, long = ?, orden = ? WHERE punto_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, punto.getIdRuta());
                stmt.setString(2, punto.getNombre());
                stmt.setFloat(3, punto.getLat());
                stmt.setFloat(4, punto.getLon());
                stmt.setInt(5, punto.getOrden());
                stmt.setInt(6, idPunto);

                int filasAfectadas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización de punto completada.");
                return filasAfectadas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE PUNTO");
            lock.unlock();
        }
    }

    public boolean delete(int idPunto) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE PUNTO).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE PUNTO");
            Thread.sleep(8000);

            String sql = "DELETE FROM puntosintermedios WHERE punto_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPunto);
                int filasAfectadas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Eliminación de punto completada.");
                return filasAfectadas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para DELETE PUNTO");
            lock.unlock();
        }
    }
}
