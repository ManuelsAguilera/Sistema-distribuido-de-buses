package persistance.dao;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

import common.PuntoViaje;
import server.DB;

public class PuntoViajeDao {

    private final Connection conn;
    private static final ReentrantLock lock = new ReentrantLock();

    public PuntoViajeDao(Connection conn) {
        super();
        this.conn = conn;
    }

    public void insert(PuntoViaje puntoViaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT PUNTO VIAJE).");
            return;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT PUNTO VIAJE");
            Thread.sleep(8000);

            String sql = "INSERT INTO puntosintermedios_viaje (viaje_id, punto_id, hora_llegada, hora_salida, hora_llegada_estimada) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, puntoViaje.getIdViaje());
                stmt.setInt(2, puntoViaje.getIdPunto());
                stmt.setTimestamp(3, puntoViaje.getHoraLlegada() != null ? Timestamp.valueOf(puntoViaje.getHoraLlegada()) : null);
                stmt.setTimestamp(4, puntoViaje.getHoraSalida() != null ? Timestamp.valueOf(puntoViaje.getHoraSalida()) : null);
                stmt.setTime(5, puntoViaje.getHoraLlegadaEstimada() != null ? Time.valueOf(puntoViaje.getHoraLlegadaEstimada().toLocalTime()) : null);

                stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción punto viaje completada.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT PUNTO VIAJE");
            lock.unlock();
        }
    }

    public void fillPointsFromViaje(int idViaje) throws SQLException {
        String sql = "INSERT INTO puntosintermedios_viaje (viaje_id, punto_id, hora_llegada, hora_salida, hora_llegada_estimada) " +
                "SELECT v.viaje_id, pi.punto_id, NULL::timestamp, NULL::timestamp, NULL::time " +
                "FROM viajes v " +
                "JOIN puntosintermedios pi ON pi.ruta_id = v.ruta_id " +
                "WHERE v.viaje_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idViaje);
            stmt.executeUpdate();
        }
    }

    public void update(PuntoViaje puntoViaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE PUNTO VIAJE).");
            return;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE PUNTO VIAJE");
            Thread.sleep(8000);

            String sql = "UPDATE puntosintermedios_viaje SET hora_llegada = ?, hora_salida = ?, hora_llegada_estimada = ? " +
                    "WHERE viaje_id = ? AND punto_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setTimestamp(1, puntoViaje.getHoraLlegada() != null ? Timestamp.valueOf(puntoViaje.getHoraLlegada()) : null);
                stmt.setTimestamp(2, puntoViaje.getHoraSalida() != null ? Timestamp.valueOf(puntoViaje.getHoraSalida()) : null);
                stmt.setTime(3, puntoViaje.getHoraLlegadaEstimada() != null ? Time.valueOf(puntoViaje.getHoraLlegadaEstimada().toLocalTime()) : null);
                stmt.setInt(4, puntoViaje.getIdViaje());
                stmt.setInt(5, puntoViaje.getIdPunto());

                stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización punto viaje completada.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE PUNTO VIAJE");
            lock.unlock();
        }
    }

    public void delete(int idViaje, int idPunto) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE PUNTO VIAJE).");
            return;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE PUNTO VIAJE");
            Thread.sleep(8000);

            String sql = "DELETE FROM puntosintermedios_viaje WHERE viaje_id = ? AND punto_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idViaje);
                stmt.setInt(2, idPunto);
                stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Eliminación punto viaje completada.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para DELETE PUNTO VIAJE");
            lock.unlock();
        }
    }
}
