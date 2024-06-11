package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.util.SenhaHash;
import org.jetbrains.annotations.NotNull;

public class UsuarioService {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(@NotNull String email,@NotNull String senha) {
        String senhaCriptografada = new SenhaHash().getSHA256Hash(senha);

        return usuarioDAO.selectUnique(email, senhaCriptografada) != null;
    }

    public String cadastrar(@NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        if (usuarioDAO.existsEmail(email)) {
            return "Email já cadastrado!";
        }
        else{
            if (nome.isBlank() || email.isBlank() || senha.isBlank() || permissao.isBlank()){
                return "Campos obrigatórios não preenchidos!";
            }
            else{
                if (!(permissao.equals("ADMIN") || permissao.equals("USER"))){
                    return "Permissão inválida!";
                }
                else{
                    if (senha.length() < 8){
                        return "Senha deve ter no mínimo 8 caracteres!";
                    }
                    else{
                        String senhaHash = new SenhaHash().getSHA256Hash(senha);

                        if (usuarioDAO.insert(new Usuario(nome, email, senhaHash, permissao))){
                            return "1";
                        }
                        else{
                            return "Erro ao cadastrar usuário!";
                        }
                    }
                }
            }
        }
    }
}
