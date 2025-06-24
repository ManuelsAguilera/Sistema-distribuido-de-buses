package persistance.dao;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

import common.Pasajero;
import server.DB;

public class PasajeroDAO {

    private final Connection conn;

    private static final ReentrantLock lock = new ReentrantLock();

    public PasajeroDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(Pasajero pasajero) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT PASAJERO).");
            return -1;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT PASAJERO");
            Thread.sleep(8000);

            String sql = "INSERT INTO pasajeros (nombre, correo) VALUES (?, ?)";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, pasajero.getNombre());
                stmt.setString(2, pasajero.getCorreo());
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new SQLException("No se insertó ningún pasajero.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción de pasajero completada.");
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID generado.");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT PASAJERO");
            lock.unlock();
        }
    }

    public boolean update(int idPasajero, Pasajero pasajero) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE PASAJERO).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE PASAJERO");
            Thread.sleep(8000);

            String sql = "UPDATE pasajeros SET nombre = ?, correo = ? WHERE pasajero_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pasajero.getNombre());
                stmt.setString(2, pasajero.getCorreo());
                stmt.setInt(3, idPasajero);
                boolean res = stmt.executeUpdate() > 0;
                System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización de pasajero completada.");
                return res;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE PASAJERO");
            lock.unlock();
        }
    }

    public boolean delete(int idPasajero) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE PASAJERO).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE PASAJERO");
            Thread.sleep(8000);

            String sql = "DELETE FROM pasajeros WHERE pasajero_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPasajero);
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new SQLException("No se eliminó ningún pasajero.");
                }

                System.out.println(Thread.currentThread().getName() + ": [DONE] Eliminación de pasajero completada.");
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para DELETE PASAJERO");
            lock.unlock();
        }
    }

    public Pasajero getPasajero(int idPasajero) throws SQLException {
        String sql = "SELECT * FROM pasajeros WHERE pasajero_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPasajero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                return new Pasajero(idPasajero, nombre, correo);
            } else {
                return null;
            }
        }
    }

    public Pasajero getPasajero(String nombre, String correo) throws SQLException {
        String sql = "SELECT * FROM pasajeros WHERE nombre = ? AND correo= ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idPasajero = rs.getInt("pasajero_id");
                return new Pasajero(idPasajero, nombre, correo);
            } else {
                return null;
            }
        }
    }

    public boolean inTable(int idPasajero) throws SQLException {
        String sql = "SELECT * FROM pasajeros WHERE pasajero_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPasajero);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
