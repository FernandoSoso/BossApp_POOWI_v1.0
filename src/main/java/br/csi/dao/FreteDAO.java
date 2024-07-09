package br.csi.dao;

import br.csi.model.Caminhao;
import br.csi.model.Frete;
import br.csi.model.Motorista;
import br.csi.util.ConectaDB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FreteDAO {
    public ArrayList<Frete> selectAll(int offset) {
        ConectaDB db = new ConectaDB();
        ArrayList<Frete> todosFretes = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM frete LIMIT 16 OFFSET ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setInt(1, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Frete frete =
                        new Frete(
                                rs.getInt("cod"),
                                rs.getString("origem"),
                                rs.getDate("origem_data"),
                                rs.getString("destino"),
                                rs.getDate("destino_data"),
                                rs.getDouble("valor_tonelada"),
                                rs.getDouble("peso"),
                                rs.getString("observacao"),
                                rs.getString("estado"),
                                new Motorista(rs.getInt("cod_motorista"), null, null, null, null, null),
                                new Caminhao(rs.getInt("cod_caminhao"), null, null, null, 0, 0, 0, null)
                        );
                todosFretes.add(frete);
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            db.closeConexao();
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
                }
            }
        }
        return todosFretes;
    }

    public Frete selectUnique(int cod) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM frete WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new  Frete(
                                rs.getInt("cod"),
                                rs.getString("origem"),
                                rs.getDate("origem_data"),
                                rs.getString("destino"),
                                rs.getDate("destino_data"),
                                rs.getDouble("valor_tonelada"),
                                rs.getDouble("peso"),
                                rs.getString("observacao"),
                                rs.getString("estado"),
                                new Motorista(rs.getInt("cod_motorista"), null, null, null, null, null),
                                new Caminhao(rs.getInt("cod_caminhao"), null, null, null, 0, 0, 0, null)
                        );
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            db.closeConexao();
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

    public int insert(Frete frete) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "INSERT INTO frete (origem, origem_data, destino, destino_data, valor_tonelada, peso, observacao, estado, cod_motorista, cod_caminhao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, frete.getOrigem());
            stmt.setDate(2, (Date) frete.getOrigem_data());
            stmt.setString(3, frete.getDestino());
            stmt.setDate(4, (Date) frete.getDestino_data());
            stmt.setDouble(5, frete.getValor_tonelada());
            stmt.setDouble(6, frete.getPeso());
            stmt.setString(7, frete.getObservacao());
            stmt.setString(8, frete.getEstado());
            stmt.setInt(9, frete.getMotorista().getCod());
            stmt.setInt(10, frete.getCaminhao().getCod());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                return -1;
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            db.closeConexao();
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

    public boolean update(Frete frete) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "UPDATE frete SET origem = ?, origem_data = ?, destino = ?, destino_data = ?, valor_tonelada = ?, peso = ?, observacao = ?, estado = ?, cod_motorista = ?, cod_caminhao = ? WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, frete.getOrigem());
            stmt.setDate(2, (Date) frete.getOrigem_data());
            stmt.setString(3, frete.getDestino());
            stmt.setDate(4, (Date) frete.getDestino_data());
            stmt.setDouble(5, frete.getValor_tonelada());
            stmt.setDouble(6, frete.getPeso());
            stmt.setString(7, frete.getObservacao());
            stmt.setString(8, frete.getEstado());
            stmt.setInt(9, frete.getMotorista().getCod());
            stmt.setInt(10, frete.getCaminhao().getCod());
            stmt.setInt(11, frete.getCod());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);

        } finally {
            db.closeConexao();
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

        try{
            String query = "DELETE FROM frete WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Erro ao acessar o banco de dados", e);
        } finally {
            db.closeConexao();
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
