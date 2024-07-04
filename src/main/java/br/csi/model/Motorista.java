package br.csi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Motorista {

    public Motorista() {
    }

    public Motorista(int cod, String nome, String endereco, String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2) {
        this.cod = cod;
        this.nome = nome;
        this.endereco = endereco;
        this.telefonePrincipal = telefonePrincipal;
        this.telefoneAlternativo = telefoneAlternativo;
        this.telefoneAlternativo2 = telefoneAlternativo2;
    }

    private int cod;
    private String nome;
    private String endereco;
    private String telefonePrincipal;
    private String telefoneAlternativo;
    private String telefoneAlternativo2;
    private Caminhao caminhao;
    private Date dataCaminhao;

    @Override
    public String toString() {
        return "Motorista{" +
                "cod=" + cod +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefonePrincipal='" + telefonePrincipal + '\'' +
                ", telefoneAlternativo='" + telefoneAlternativo + '\'' +
                ", telefoneAlternativo2='" + telefoneAlternativo2 + '\'' +
                '}';
    }
}
