package ua.kpi.pm_system.connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getPostgresConnection() {
        Connection connection = null;

        try {
            connection = PostgresConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


}
