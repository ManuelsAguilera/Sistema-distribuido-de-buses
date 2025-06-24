package persistance.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

import common.Pasaje;
import server.DB;

public class PasajeDAO {

    private final Connection conn;
    private static final ReentrantLock lock = new ReentrantLock();

    public PasajeDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inTable(int idPasaje) throws SQLException {
        String sql = "SELECT * FROM pasajes WHERE pasaje_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPasaje);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void insert(Pasaje pasaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT PASAJE).");
            return;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT PASAJE");
            Thread.sleep(8000);

            String sql = "INSERT INTO pasajes (viaje_id, pasajero_id, punto_origen_id, punto_destino_id, precio, fecha_compra, asiento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción de pasaje completada.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT PASAJE");
            lock.unlock();
        }
    }

    public boolean update(int idPasaje, Pasaje pasaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE PASAJE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE PASAJE");
            Thread.sleep(8000);

            String sql = "UPDATE pasajes SET viaje_id = ?, pasajero_id = ?, punto_origen_id = ?, "
                    + "punto_destino_id = ?, precio = ?, fecha_compra = ?, asiento = ? "
                    + "WHERE pasaje_id = ?";

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

                int filas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización de pasaje completada.");
                return filas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE PASAJE");
            lock.unlock();
        }
    }

    public Boolean delete(int idPasaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE PASAJE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE PASAJE");
            Thread.sleep(8000);

            String sql = "DELETE FROM pasajes WHERE pasaje_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPasaje);
                int filas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Eliminación de pasaje completada.");
                return filas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para DELETE PASAJE");
            lock.unlock();
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

    public float consultaVentas() throws SQLException {
        String sql = "SELECT SUM(precio) FROM pasajes";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            return rs.next() ? rs.getFloat(1) : 0;
        }
    }
}
