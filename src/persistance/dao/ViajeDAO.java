package persistance.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import common.Viaje;
import server.DB;

public class ViajeDAO {

    private final Connection conn;

    private static final ReentrantLock lock = new ReentrantLock();

    public ViajeDAO(Connection conn) {
        this.conn = conn;
    }

    public int getAsientosDispViaje(int idViaje) throws SQLException {
        String sql = "SELECT b.capacidad - COUNT(pas.pasaje_id) AS asientos_disponibles "
                + "FROM viajes v "
                + "JOIN buses b ON v.matricula = b.matricula "
                + "LEFT JOIN pasajes pas ON pas.viaje_id = v.viaje_id "
                + "WHERE v.viaje_id = ? "
                + "GROUP BY b.capacidad";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idViaje);
            var rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt("asientos_disponibles");
            else
                return -1;
        }
    }

    public Viaje getViaje(int idViaje) throws SQLException {
        String sql = "SELECT * FROM viajes WHERE viaje_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idViaje);
            var rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void insert(Viaje viaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (INSERT VIAJE).");
            return;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para INSERT VIAJE");
            Thread.sleep(8000);

            String sql = "INSERT INTO viajes (ruta_id, matricula, fecha, hora_salida, hora_salida_estimada) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, viaje.getIdRuta());
                stmt.setString(2, viaje.getMatricula());
                stmt.setDate(3, Date.valueOf(viaje.getFecha()));
                stmt.setTime(4, Time.valueOf(viaje.getSalida()));
                stmt.setTime(5, Time.valueOf(viaje.getSalidaEstimada()));
                stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Inserción de viaje completada.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para INSERT VIAJE");
            lock.unlock();
        }
    }

    public Boolean delete(int idViaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (DELETE VIAJE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para DELETE VIAJE");
            Thread.sleep(8000);

            String sql = "DELETE FROM viajes WHERE viaje_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idViaje);
                int filas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Eliminación de viaje completada.");
                return filas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para DELETE VIAJE");
            lock.unlock();
        }
    }

    public boolean update(int idViaje, Viaje viaje) throws SQLException {
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + ": [USED] Recurso ocupado. No puedes acceder al recurso en este momento (UPDATE VIAJE).");
            return false;
        }
        try {
            System.out.println(Thread.currentThread().getName() + ": [LOCKED] Bloqueo adquirido para UPDATE VIAJE");
            Thread.sleep(8000);

            String sql = "UPDATE viajes SET ruta_id = ?, matricula = ?, fecha = ?, hora_salida = ?, hora_salida_estimada = ? "
                    + "WHERE viaje_id = ?";

            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, viaje.getIdRuta());
                stmt.setString(2, viaje.getMatricula());
                stmt.setDate(3, Date.valueOf(viaje.getFecha()));
                stmt.setTime(4, Time.valueOf(viaje.getSalida()));
                stmt.setTime(5, Time.valueOf(viaje.getSalidaEstimada()));
                stmt.setInt(6, idViaje);

                int filas = stmt.executeUpdate();
                System.out.println(Thread.currentThread().getName() + ": [DONE] Actualización de viaje completada.");
                return filas > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println(Thread.currentThread().getName() + ": [FREE] Bloqueo liberado para UPDATE VIAJE");
            lock.unlock();
        }
    }

    public ArrayList<Viaje> getViajePorOrigen(String Origen, String Destino) throws SQLException {
        String sql = "SELECT DISTINCT v.viaje_id, v.ruta_id, v.matricula, v.fecha, v.hora_salida, v.hora_salida_estimada "
                + "FROM viajes v "
                + "JOIN buses b ON v.matricula = b.matricula "
                + "JOIN puntosintermedios po ON po.ruta_id = v.ruta_id AND po.nombre_punto = ? "
                + "JOIN puntosintermedios pd ON pd.ruta_id = v.ruta_id AND pd.nombre_punto = ? "
                + "WHERE po.orden < pd.orden "
                + "ORDER BY v.fecha";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Origen);
            stmt.setString(2, Destino);
            var rs = stmt.executeQuery();

            ArrayList<Viaje> lista = new ArrayList<>();

            while (rs.next()) {
                int viajeId = rs.getInt("viaje_id");
                int rutaId = rs.getInt("ruta_id");
                String matricula = rs.getString("matricula");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                LocalTime salida = rs.getTime("hora_salida").toLocalTime();
                Time salidaEstimadaSQL = rs.getTime("hora_salida_estimada");
                LocalTime salidaEstimada = (salidaEstimadaSQL != null) ? salidaEstimadaSQL.toLocalTime() : null;
                lista.add(new Viaje(viajeId, rutaId, matricula, fecha, salida, salidaEstimada));
            }
            return lista;
        }
    }

    public ArrayList<Viaje> getViajePorOrigen(String Origen, String Destino, LocalDate fecha) throws SQLException {
        String sql = "SELECT DISTINCT v.viaje_id, v.ruta_id, v.matricula, v.fecha, v.hora_salida, v.hora_salida_estimada "
                + "FROM viajes v "
                + "JOIN buses b ON v.matricula = b.matricula "
                + "JOIN puntosintermedios po ON po.ruta_id = v.ruta_id AND po.nombre_punto = ? "
                + "JOIN puntosintermedios pd ON pd.ruta_id = v.ruta_id AND pd.nombre_punto = ? "
                + "WHERE po.orden < pd.orden AND v.fecha = ? "
                + "ORDER BY v.fecha";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Origen);
            stmt.setString(2, Destino);
            stmt.setDate(3, Date.valueOf(fecha));
            var rs = stmt.executeQuery();

            ArrayList<Viaje> lista = new ArrayList<>();

            while (rs.next()) {
                int viajeId = rs.getInt("viaje_id");
                int rutaId = rs.getInt("ruta_id");
                String matricula = rs.getString("matricula");
                LocalDate fechaViaje = rs.getDate("fecha").toLocalDate();
                LocalTime salida = rs.getTime("hora_salida").toLocalTime();
                Time salidaEstimadaSQL = rs.getTime("hora_salida_estimada");
                LocalTime salidaEstimada = (salidaEstimadaSQL != null) ? salidaEstimadaSQL.toLocalTime() : null;
                lista.add(new Viaje(viajeId, rutaId, matricula, fechaViaje, salida, salidaEstimada));
            }
            return lista;
        }
    }
}
