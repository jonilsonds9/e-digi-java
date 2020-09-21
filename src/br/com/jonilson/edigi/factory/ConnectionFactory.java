package br.com.jonilson.edigi.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost/edigi?useTimezone=true&serverTimezone=UTC",
                            "admin", "admin");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao estabelecer conexão com banco de dados");
        }

        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            if (null != connection) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
