package br.csi.dao;

import br.csi.model.Caminhao;
import br.csi.util.ConectaDB;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CaminhaoDAO {

    public ArrayList<Caminhao> selectAll() {
        ConectaDB db = new ConectaDB();
        ArrayList<Caminhao> todosCaminhoes = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM caminhao";

            stmt = db.getConexao().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Caminhao caminhao =
                        new Caminhao(
                                rs.getInt("cod"),
                                rs.getString("placa"),
                                rs.getString("marca"),
                                rs.getString("modelo"),
                                rs.getInt("ano"),
                                rs.getInt("capacidade"),
                                rs.getDouble("percentual_motorista"),
                                rs.getString("status")
                        );
                todosCaminhoes.add(caminhao);
            }

            stmt.close();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }

        return todosCaminhoes;
    }

    public Caminhao selectUnique(int cod) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM caminhao WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return new Caminhao(
                    rs.getInt("cod"),
                    rs.getString("placa"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("ano"),
                    rs.getInt("capacidade"),
                    rs.getDouble("percentualMotorista"),
                    rs.getString("status")
            );
        }catch (SQLException e){
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }

        return null;
    }

    public int insert(@NotNull Caminhao caminhao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "Insert into caminhao " +
                    "(placa, marca, modelo, ano, capacidade, percentual_motorista, status) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getModelo());
            stmt.setInt(4, caminhao.getAno());
            stmt.setInt(5, caminhao.getCapacidade());
            stmt.setDouble(6, caminhao.getPercentualMotorista());
            stmt.setString(7, caminhao.getStatus());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas == 0){
                throw new SQLException("Erro ao inserir caminhão. Nenhuma linha inserida.");
            }

            return stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }

        return -1;
    }

    public boolean update(@NotNull Caminhao caminhao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE caminhao SET placa = ?, marca = ?, modelo = ?, ano = ?, capacidade = ?, percentual_motorista = ?, status = ? WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getModelo());
            stmt.setInt(4, caminhao.getAno());
            stmt.setInt(5, caminhao.getCapacidade());
            stmt.setDouble(6, caminhao.getPercentualMotorista());
            stmt.setString(7, caminhao.getStatus());
            stmt.setInt(8, caminhao.getCod());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }

        return false;
    }

    public boolean delete(int cod) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM caminhao WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }

        return false;
    }
}
