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

    public Usuario insert(@NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        if (usuarioDAO.existsEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }
        else{
            if (nome.isBlank() || email.isBlank() || senha.isBlank() || permissao.isBlank()){
                throw new IllegalArgumentException("Campos obrigatórios não preenchidos!");
            }
            else{
                if (!(permissao.equals("ADMIN") || permissao.equals("USER"))){
                    throw new IllegalArgumentException("Permissão inválida!");
                }
                else{
                    if (senha.length() < 8){
                        throw new  IllegalArgumentException("Senha deve ter no mínimo 8 caracteres!");
                    }
                    else{
                        String senhaHash = new SenhaHash().getSHA256Hash(senha);

                        if (usuarioDAO.insert(new Usuario(nome, email, senhaHash, permissao))){
                            return usuarioDAO.selectUnique(email, senhaHash);
                        }
                        else{
                            return null;
                        }
                    }
                }
            }
        }
    }
}
