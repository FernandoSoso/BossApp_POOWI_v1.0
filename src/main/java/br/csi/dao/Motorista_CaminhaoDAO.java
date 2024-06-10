package br.csi.dao;

import br.csi.model.Motorista_Caminhao;
import br.csi.util.ConectaDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Motorista_CaminhaoDAO {
    public Motorista_Caminhao selectByCod_motorista(int codMotorista) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM motorista_Caminhao " +
                    "where cod_motorista = ?";
            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, codMotorista);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return new Motorista_Caminhao(
                            rs.getInt("cod_motorista"),
                            rs.getInt("cod_caminhao"),
                            rs.getDate("data_inicio")
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

    public Motorista_Caminhao selectByCod_caminhao(int codCaminhao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM motorista_Caminhao " +
                    "where cod_caminhao = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, codCaminhao);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new Motorista_Caminhao(
                        rs.getInt("cod_motorista"),
                        rs.getInt("cod_caminhao"),
                        rs.getDate("data_inicio")
                );
            }
            else {
                return null;
            }
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

    public boolean insert(Motorista_Caminhao relacao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO motorista_caminhao (cod_motorista, cod_caminhao, data_inicio) " +
                    "VALUES (?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, relacao.getCodMotorista());
            stmt.setInt(2, relacao.getCodCaminhao());
            stmt.setDate(3, relacao.getDataInicio());

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

    public boolean update(Motorista_Caminhao relacao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE motorista_caminhao SET data_inicio = ? " +
                    "WHERE cod_motorista = ? AND cod_caminhao = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setDate(1, relacao.getDataInicio());
            stmt.setInt(2, relacao.getCodMotorista());
            stmt.setInt(3, relacao.getCodCaminhao());

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

    public boolean delete(Motorista_Caminhao relacao) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM motorista_caminhao " +
                    "WHERE cod_motorista = ? AND cod_caminhao = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, relacao.getCodMotorista());
            stmt.setInt(2, relacao.getCodCaminhao());

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
