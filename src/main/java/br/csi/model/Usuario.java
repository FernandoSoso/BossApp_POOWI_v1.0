package br.csi.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {

    public Usuario(int cod, String cod_externo, String nome, String email, String senha, boolean ativo, String permissao) {
        if (cod <= 0 || cod_externo.isBlank() || cod_externo.length() > 128 || nome.isBlank() || nome.length() > 100 || email.isBlank() || email.length() > 50 || senha.isBlank() || senha.length() > 64 || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }

        this.cod = cod;
        this.cod_externo = cod_externo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.permissao = permissao;
    }

    public Usuario(String nome, String email, String senha, String permissao) {
        if (nome.isBlank() || nome.length() > 100 || email.isBlank() || email.length() > 50 || senha.isBlank() || senha.length() > 64 || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
        this.ativo = true;
    }

    private int cod;
    private String cod_externo;
    private String nome;
    private String email;
    private String senha;
    private String permissao;
    private boolean ativo;

    @Override
    public String toString() {
        return "Usuario{" +
                "cod=" + cod +
                ", cod_externo='" + cod_externo + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", permissao='" + permissao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
