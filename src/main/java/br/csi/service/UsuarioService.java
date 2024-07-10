package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.util.SenhaHash;
import org.jetbrains.annotations.NotNull;

public class UsuarioService {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario auth(@NotNull String email, @NotNull String senha) {
        String senhaCriptografada = new SenhaHash().getSHA256Hash(senha);

        return usuarioDAO.selectUnique(email, senhaCriptografada);
    }

    public boolean insert(@NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        if (!validarCampos(nome, email, senha, permissao)){
            return false;
        }

        String senhaHash = new SenhaHash().getSHA256Hash(senha);

        return usuarioDAO.insert(new Usuario(nome, email, senhaHash, permissao));
    }

    private boolean validarCampos (String nome, String email, String senha, String permissao){
        if (!(permissao.equals("ADMIN") || permissao.equals("USER"))){
            throw new IllegalArgumentException("Permissão inválida!");
        }
        else if (nome.isBlank() || email.isBlank() || senha.isBlank() || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos!");
        }
        else if (nome.length() > 50 || email.length() > 50 || senha.length() > 64){
            throw new IllegalArgumentException("Tamanho máximo de caracteres ultrapassado em algum(s) campo(s)!");
        }
        else if (senha.length() < 8){
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres!");
        }
        else if (usuarioDAO.existsEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }

        return true;
    }
}
