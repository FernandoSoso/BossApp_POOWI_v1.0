package br.csi.dao;

import br.csi.model.Despesa;
import br.csi.util.ConectaDB;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DespesaDAO {

    public ArrayList<Despesa> selectAll(int cod_frete, int offset) {
        ConectaDB db = new ConectaDB();
        ArrayList<Despesa> todasDespesas = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM despesa WHERE cod_frete = ? LIMIT 15 OFFSET ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod_frete);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Despesa despesa =
                        new Despesa(
                                rs.getInt("cod"),
                                rs.getString("tipo"),
                                rs.getDouble("valor"),
                                rs.getInt("cod_frete"),
                                rs.getDate("data_insercao")
                        );
                todasDespesas.add(despesa);
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
        return todasDespesas;
    }

    public ArrayList<Despesa> selectAllValores(int cod_frete) {
        ConectaDB db = new ConectaDB();
        ArrayList<Despesa> todasDespesas = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT valor FROM (" +
                    "SELECT * FROM despesa WHERE cod_frete = ?" +
                    ") as subquery";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod_frete);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Despesa despesa =
                        new Despesa(
                                0,
                                 null,
                                rs.getDouble("valor"),
                                0,
                                null
                        );
                todasDespesas.add(despesa);
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

        return todasDespesas;
    }

    public int insert(@NotNull Despesa despesa) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "INSERT INTO despesa (tipo, valor, cod_frete, data_insercao) VALUES (?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, despesa.getTipo());
            stmt.setDouble(2, despesa.getValor());
            stmt.setInt(3, despesa.getCod_frete());
            stmt.setDate(4, despesa.getData_insercao());

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

    public boolean update(@NotNull Despesa despesa) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "UPDATE despesa SET tipo = ?, valor = ?, cod_frete = ?, data_insercao = ? WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, despesa.getTipo());
            stmt.setDouble(2, despesa.getValor());
            stmt.setInt(3, despesa.getCod_frete());
            stmt.setDate(4, despesa.getData_insercao());
            stmt.setInt(5, despesa.getCod());

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
            String query = "DELETE FROM despesa WHERE cod = ?";

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
