package br.csi.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class Motorista {
    private int cod;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefonePrincipal;
    private String telefoneAlternativo;
    private String telefoneAlternativo2;
    private Caminhao caminhao;
    private Date dataCaminhao;


    public Motorista(int cod, @NotNull String nome, String cpf, String endereco, @NotNull String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2) {
        if (nome.isBlank() || telefonePrincipal.isBlank() || telefonePrincipal.length() > 15 || cod <= 0){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }
        else if (endereco.length() > 100 || cpf.length() > 11 || telefoneAlternativo.length() > 15 || telefoneAlternativo2.length() > 15){
            throw new IllegalArgumentException("Campos não obrigatórios inválidos!");
        }

        this.cod = cod;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefonePrincipal = telefonePrincipal;
        this.telefoneAlternativo = telefoneAlternativo;
        this.telefoneAlternativo2 = telefoneAlternativo2;
    }

    public Motorista(@NotNull String nome, String cpf, String endereco, @NotNull String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2) {
        if (nome.isBlank() || telefonePrincipal.isBlank() || telefonePrincipal.length() > 15){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }
        else if (endereco.length() > 100 || cpf.length() > 11 || telefoneAlternativo.length() > 15 || telefoneAlternativo2.length() > 15){
            throw new IllegalArgumentException("Campos não obrigatórios inválidos!");
        }

        this.cod = -1;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefonePrincipal = telefonePrincipal;
        this.telefoneAlternativo = telefoneAlternativo;
        this.telefoneAlternativo2 = telefoneAlternativo2;
    }

    public int getCod() {
        return cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getTelefoneAlternativo2() {
        return telefoneAlternativo2;
    }

    public void setTelefoneAlternativo2(String telefoneAlternativo2) {
        this.telefoneAlternativo2 = telefoneAlternativo2;
    }


    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public Date getDataCaminhao() {
        return dataCaminhao;
    }

    public void setDataCaminhao(Date dataCaminhao) {
        this.dataCaminhao = dataCaminhao;
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "cod=" + cod +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefonePrincipal='" + telefonePrincipal + '\'' +
                ", telefoneAlternativo='" + telefoneAlternativo + '\'' +
                ", telefoneAlternativo2='" + telefoneAlternativo2 + '\'' +
                '}';
    }
}
