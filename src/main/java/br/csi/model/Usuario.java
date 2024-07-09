package br.csi.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {

    public Usuario() {
    }

    public Usuario(String cod_externo, String nome, String email, boolean ativo, String permissao) {
        this.cod_externo = cod_externo;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.permissao = permissao;
    }

    public Usuario(String nome, String email, String senha, String permissao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
    }

    private String cod_externo;
    private String nome;
    private String email;
    private String senha;
    private String permissao;
    private boolean ativo;

    @Override
    public String toString() {
        return "Usuario{" +
                ", cod_externo='" + cod_externo + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", permissao='" + permissao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
