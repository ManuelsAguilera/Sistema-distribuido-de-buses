package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://ep-delicate-violet-a44uyb4f-pooler.us-east-1.aws.neon.tech/neondb";
        String user = "neondb_owner";
        String password = "npg_4L1UhyYFoItR";
        return DriverManager.getConnection(url + "?sslmode=require", user, password);
    }
}
