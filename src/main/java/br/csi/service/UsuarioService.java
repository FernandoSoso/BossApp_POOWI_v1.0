package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.util.SenhaHash;
import org.jetbrains.annotations.NotNull;

public class UsuarioService {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String email, String senha) {
        String senhaCriptografada = new SenhaHash().getSHA256Hash(senha);

        return usuarioDAO.selectUnique(email, senhaCriptografada) != null;
    }

    public boolean cadastrar(@NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        String senhaHash = new SenhaHash().getSHA256Hash(senha);

        return usuarioDAO.insert(new Usuario(nome, email, senhaHash, permissao));
    }
}
