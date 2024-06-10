package br.csi.dao;

import br.csi.model.Usuario;
import br.csi.util.ConectaDB;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {
    public ArrayList<Usuario> selectAll() {
        ConectaDB db = new ConectaDB();
        ArrayList<Usuario> todosUsuarios = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM usuario";

            stmt = db.getConexao().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Usuario usuario =
                        new Usuario(
                                rs.getInt("cod"),
                                rs.getString("cod_externo"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha"),
                                rs.getBoolean("ativo"),
                                rs.getString("permissao"));
                todosUsuarios.add(usuario);
            }
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
        return todosUsuarios;
    }

    public Usuario selectUnique(String email, String senha) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM usuario where email = ? AND senha = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return new Usuario(
                            rs.getInt("cod"),
                            rs.getString("cod_externo"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getBoolean("ativo"),
                            rs.getString(("permissao")));

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

        return null;
    }

    public boolean insert(@NotNull Usuario usuario) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuario (nome, email, senha, ativo) VALUES (?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, usuario.isAtivo());

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

    public boolean update(@NotNull Usuario usuario) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE usuario SET cod_externo = ?, nome = ?, email = ?, senha = ?, ativo = ?, permissao = ? WHERE cod = ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, usuario.getCod_externo());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setBoolean(5, usuario.isAtivo());
            stmt.setString(6, usuario.getPermissao());
            stmt.setInt(7, usuario.getCod());

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
            String query = "DELETE FROM usuario WHERE cod = ?";

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
