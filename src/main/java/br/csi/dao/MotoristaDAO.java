package br.csi.dao;

import br.csi.model.Motorista;
import br.csi.util.ConectaDB;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MotoristaDAO {
    public ArrayList<Motorista> selectAll() {
        ConectaDB db = new ConectaDB();
        ArrayList<Motorista> todosMotoristas = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM motorista";

            stmt = db.getConexao().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Motorista motorista =
                        new Motorista(
                                rs.getInt("cod"),
                                rs.getString("nome"),
                                rs.getString("endereco"),
                                rs.getString("telefone_principal"),
                                rs.getString("telefone_alternativo"),
                                rs.getString("telefone_alternativo2")
                        );
                todosMotoristas.add(motorista);
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
        return todosMotoristas;
    }

    public Motorista selectUnique(int cod) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM motorista WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, cod);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return new Motorista(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            rs.getString("endereco"),
                            rs.getString("telefonePrincipal"),
                            rs.getString("telefoneAlternativo"),
                            rs.getString("telefoneAlternativo2")
                    );

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

    public int insert(@NotNull Motorista motorista)  {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO motorista " +
                    "(nome, endereco, telefone_principal, telefone_alternativo, telefone_alternativo2) " +
                    "VALUES (?, ?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getEndereco());
            stmt.setString(3, motorista.getTelefonePrincipal());
            stmt.setString(4, motorista.getTelefoneAlternativo());
            stmt.setString(5, motorista.getTelefoneAlternativo2());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Erro ao inserir motorista. Nenhuma linha inserida.");
            }

            return stmt.getGeneratedKeys().getInt(1);
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
    public boolean update(@NotNull Motorista motorista) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE motorista SET " +
                    "nome = ?, endereco = ?, telefone_principal = ?, telefone_alternativo = ?, telefone_alternativo2 = ? " +
                    "WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getEndereco());
            stmt.setString(3, motorista.getTelefonePrincipal());
            stmt.setString(4, motorista.getTelefoneAlternativo());
            stmt.setString(5, motorista.getTelefoneAlternativo2());
            stmt.setInt(6, motorista.getCod());

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

        try {
            String query = "DELETE FROM motorista WHERE cod = ?";

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
