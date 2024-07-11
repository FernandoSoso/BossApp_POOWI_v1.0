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

    public Usuario auth(@NotNull String email, @NotNull String senha) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "SELECT * FROM usuario where email = ? and senha = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("cod_externo"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("permissao"));
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

    public boolean existsEmail(String email) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "SELECT * FROM usuario where email = ?";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

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

    public ArrayList<Usuario> selectAll(int offset, int limit) {
        ConectaDB db = new ConectaDB();
        ArrayList<Usuario> todosUsuarios = new ArrayList<>();
        PreparedStatement stmt = null;

        try{
            String query = "SELECT * FROM usuario LIMIT ? OFFSET ?";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Usuario usuario =
                        new Usuario(
                                rs.getString("cod_externo"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("permissao"));
                todosUsuarios.add(usuario);
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
        return todosUsuarios;
    }

    public Usuario selectUnique(Usuario usuario) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "SELECT * FROM usuario where cod_externo = CAST(? AS UUID)";

            stmt = db.getConexao().prepareStatement(query);
            stmt.setString(1, usuario.getCod());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("cod_externo"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("permissao"));
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

    public boolean insert(@NotNull Usuario usuario) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuario (nome, email, senha, permissao) VALUES (?, ?, ?, ?)";

            stmt = db.getConexao().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPermissao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0){
                ResultSet rs = stmt.getGeneratedKeys();

                return rs.next();
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

        return false;
    }

    public boolean update(@NotNull Usuario usuario) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE usuario SET nome = ?, email = ?, senha = ?, permissao = ? WHERE cod_externo = CAST(? AS UUID)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPermissao());
            stmt.setString(5, usuario.getCod());

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


    public boolean delete(String codExterno) {
        ConectaDB db = new ConectaDB();
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM usuario WHERE cod_externo = CAST(? AS UUID)";

            stmt = db.getConexao().prepareStatement(query);

            stmt.setString(1, codExterno);

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
