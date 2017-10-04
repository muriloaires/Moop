package mobi.moop.model.entities;

import java.text.NumberFormat;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BemComum {

    private Long id;
    private String nome;
    private String avatar;
    private Double valor;
    private String termosDeUso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTermosDeUso() {
        return termosDeUso;
    }

    public void setTermosDeUso(String termosDeUso) {
        this.termosDeUso = termosDeUso;
    }

    public String getValorCurrency() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        if (valor == null) {
            return format.format(0d);
        }

        return format.format(valor);
    }
}
