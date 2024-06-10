package br.csi.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectaDB {
    public Connection getConexao() {

        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/AppBoss_POOWI",
                    "postgres",
                    "1234");

        }catch (SQLException | ClassNotFoundException e){
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        }

        return null;
    }
}
