package br.csi.model;

import org.jetbrains.annotations.NotNull;

public class Usuario {
    private int cod;
    private String cod_externo;
    private String nome;
    private String email;
    private String senha;
    private String permissao;
    private boolean ativo;

    public Usuario(int cod, @NotNull String cod_externo, @NotNull String nome, @NotNull String email, @NotNull String senha, boolean ativo, @NotNull String permissao) {
        if (cod <= 0 || cod_externo.isBlank() || cod_externo.length() > 128 || nome.isBlank() || nome.length() > 50 || email.isBlank() || email.length() > 50 || senha.isBlank() || senha.length() > 64 || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }
        else if (!(permissao.equals("ADMIN") || permissao.equals("USER"))){
            throw new IllegalArgumentException("Permissão inválida!");
        }

        this.cod = cod;
        this.cod_externo = cod_externo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.permissao = permissao;
    }

    public Usuario(@NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        if (nome.isBlank() || nome.length() > 50 || email.isBlank() || email.length() > 50 || senha.isBlank() || senha.length() > 64 || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }
        else if (!(permissao.equals("ADMIN") || !(permissao.equals("USER")))){
            throw new IllegalArgumentException("Permissão inválida!");
        }

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
        this.ativo = true;
    }

    public String getCod_externo() {
        return cod_externo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public int getCod() {
        return cod;
    }

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
