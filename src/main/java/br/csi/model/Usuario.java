package br.csi.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {

    public Usuario() {
    }

    private String cod;
    private String nome;
    private String email;
    private String senha;
    private String permissao;

    public Usuario(String cod, String nome, String email, String permissao) {
        this.nome = nome;
        this.email = email;
        this.cod = cod;
        this.permissao = permissao;
    }

    public Usuario(String cod, String nome, String email, String senhaHash, String permissao) {
        this.cod = cod;
        this.nome = nome;
        this.email = email;
        this.senha = senhaHash;
        this.permissao = permissao;
    }

    public Usuario(String cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", cod_externo='" + cod + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", permissao='" + permissao + '\'' +
                '}';
    }
}
