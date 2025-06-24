package persistance.dao;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;
import server.DB;
import common.Bus;

public class BusDAO {

    private final Connection conn;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final long TIMEOUT = 5;
    
    public BusDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inTable(String matricula) throws SQLException {
        String sql = "SELECT * FROM buses WHERE buses.matricula = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public Bus getBus(String matricula) throws SQLException {
        String sql = "SELECT * FROM buses WHERE buses.matricula = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String modelo = rs.getString("modelo");
                int capacidad = rs.getInt("capacidad");
                return new Bus(matricula, modelo, capacidad);
            } else {
                return null;
            }
        }
    }

    public boolean insert(Bus bus) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT");
            Thread.sleep(8000); // simula operación lenta

            String checkSql = "SELECT * FROM buses WHERE matricula = ? FOR UPDATE";
            String insertSql = "INSERT INTO buses (matricula, modelo, capacidad) VALUES (?, ?, ?)";

            try (Connection conn = DB.connect()) {
                conn.setAutoCommit(false);

                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, bus.getMatricula());
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next()) {
                        System.out.println(Thread.currentThread().getName() + ": Bus ya existe.");
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setString(1, bus.getMatricula());
                    stmt.setString(2, bus.getModelo());
                    stmt.setInt(3, bus.getAsientosTotales());
                    int filasAfectadas = stmt.executeUpdate();

                    conn.commit();
                    System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción completada, filas afectadas: " + filasAfectadas);
                    return filasAfectadas > 0;
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT");
            lock.unlock();
        }
    }

    public Boolean delete(String matricula) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE");
            Thread.sleep(8000);

            String lockSql = "SELECT * FROM buses WHERE matricula = ? FOR UPDATE";
            String deletePasajes = "DELETE FROM pasajes WHERE viaje_id IN (SELECT viaje_id FROM viajes WHERE matricula = ?)";
            String deleteViajes = "DELETE FROM viajes WHERE matricula = ?";
            String deleteBus = "DELETE FROM buses WHERE matricula = ?";

            try (Connection conn = DB.connect()) {
                conn.setAutoCommit(false);

                try (PreparedStatement lockStmt = conn.prepareStatement(lockSql)) {
                    lockStmt.setString(1, matricula);
                    ResultSet rs = lockStmt.executeQuery();
                    if (!rs.next()) {
                        System.out.println(Thread.currentThread().getName() + ": Bus no existe.");
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement stmtPasajes = conn.prepareStatement(deletePasajes);
                     PreparedStatement stmtViajes = conn.prepareStatement(deleteViajes);
                     PreparedStatement stmtBus = conn.prepareStatement(deleteBus)) {

                    stmtPasajes.setString(1, matricula);
                    stmtPasajes.executeUpdate();

                    stmtViajes.setString(1, matricula);
                    stmtViajes.executeUpdate();

                    stmtBus.setString(1, matricula);
                    int rows = stmtBus.executeUpdate();

                    conn.commit();
                    System.out.println(Thread.currentThread().getName() + ": [DONE] Borrado completado con " + rows + " filas afectadas.");
                    return rows > 0;

                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [UNLOCKED] Bloqueo liberado para DELETE");
            lock.unlock();
        }
    }

    public Boolean update(String matricula, Bus bus) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ":[USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE");
            Thread.sleep(8000);

            String lockSql = "SELECT * FROM buses WHERE matricula = ? FOR UPDATE";
            String updateSql = "UPDATE buses SET capacidad = ?, modelo = ? WHERE matricula = ?";

            try (Connection conn = DB.connect()) {
                conn.setAutoCommit(false);

                try (PreparedStatement lockStmt = conn.prepareStatement(lockSql)) {
                    lockStmt.setString(1, matricula);
                    ResultSet rs = lockStmt.executeQuery();
                    if (!rs.next()) {
                        System.out.println(Thread.currentThread().getName() + ": Bus no existe.");
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setInt(1, bus.getAsientosTotales());
                    stmt.setString(2, bus.getModelo());
                    stmt.setString(3, matricula);
                    int filasAfectadas = stmt.executeUpdate();

                    conn.commit();
                    System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización completada, filas afectadas: " + filasAfectadas);
                    return filasAfectadas > 0;

                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE");
            lock.unlock();
        }
    }
}
