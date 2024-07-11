package br.csi.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectaDB {
    private Connection conexao;

    public Connection getConexao() {

        try{
            Class.forName("org.postgresql.Driver");
            this.conexao = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/AppBoss_POOWI",
                    "postgres",
                    "1234");

            return this.conexao;
        }catch (SQLException | ClassNotFoundException e){
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        }

        return null;
    }

    public void closeConexao(){
        try {
            this.conexao.close();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        }
    }
}
