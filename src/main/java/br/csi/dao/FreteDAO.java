package br.csi.dao;

import br.csi.model.Caminhao;
import br.csi.model.Frete;
import br.csi.model.Motorista;
import br.csi.util.ConectaDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FreteDAO {
    public ArrayList<Frete> selectAll(int offset, int limit) {
        ConectaDB db = new ConectaDB();
        ArrayList<Frete> todosFretes = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query;

            if (limit > 0) {
                query = "SELECT * FROM frete LIMIT ? OFFSET ?";
                stmt = db.getConexao().prepareStatement(query);
                stmt.setInt(1, limit);
                stmt.setInt(2, offset);
            } else {
                // Omit LIMIT clause when limit is 0
                query = "SELECT * FROM frete OFFSET ?";
                stmt = db.getConexao().prepareStatement(query);
                stmt.setInt(1, offset);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Frete frete =
                        new Frete(
                                rs.getInt("cod"),
                                rs.getString("origem"),
                                rs.getString("destino"),
                                rs.getDouble("valor_tonelada"),
                                rs.getDouble("peso"),
                                rs.getString("observacao"),
                                rs.getString("estado"),
                                new Motorista(rs.getInt("cod_motorista")),
                                new Caminhao(rs.getInt("cod_caminhao")),
                                rs.getDouble("valor_liquido"),
                                rs.getDouble("parte_motorista")
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
                                rs.getString("destino"),
                                rs.getDouble("valor_tonelada"),
                                rs.getDouble("peso"),
                                rs.getString("observacao"),
                                rs.getString("estado"),
                                new Motorista(rs.getInt("cod_motorista")),
                                new Caminhao(rs.getInt("cod_caminhao")),
                                rs.getDouble("valor_liquido"),
                                rs.getDouble("parte_motorista")
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
            String query = "INSERT INTO frete (origem, destino, valor_tonelada, peso, observacao, estado, cod_motorista, cod_caminhao, parte_motorista, valor_liquido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, frete.getOrigem());
            stmt.setString(2, frete.getDestino());
            stmt.setDouble(3, frete.getValorTonelada());
            stmt.setDouble(4, frete.getPeso());
            stmt.setString(5, frete.getObservacao());
            stmt.setString(6, frete.getEstado());
            stmt.setInt(7, frete.getMotorista().getCod());
            stmt.setInt(8, frete.getCaminhao().getCod());
            stmt.setDouble(9, frete.getParteMotorista());
            stmt.setDouble(10, frete.getValorLiquido());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas == 0){
                throw new SQLException("Erro ao inserir frete. Nenhuma linha inserida.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erro ao inserir frete, nenhum código foi retornado.");
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
            String query = "UPDATE frete SET origem = ?, destino = ?, valor_tonelada = ?, peso = ?, observacao = ?, estado = ?, cod_motorista = ?, cod_caminhao = ? WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, frete.getOrigem());
            stmt.setString(2, frete.getDestino());
            stmt.setDouble(3, frete.getValorTonelada());
            stmt.setDouble(4, frete.getPeso());
            stmt.setString(5, frete.getObservacao());
            stmt.setString(6, frete.getEstado());
            stmt.setInt(7, frete.getMotorista().getCod());
            stmt.setInt(8, frete.getCaminhao().getCod());
            stmt.setInt(9, frete.getCod());

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
