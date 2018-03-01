package brmobi.moop.data.network.model;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class BemComum implements Serializable {

    private Long id;
    private String nome;
    private String avatar;
    private Double valor;
    private String termoDeUso;

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

    public String getTermoDeUso() {
        if (termoDeUso == null) return "";
        return termoDeUso;
    }

    public void setTermoDeUso(String termosDeUso) {
        this.termoDeUso = termosDeUso;
    }

    public String getValorCurrency() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        if (valor == null) {
            return format.format(0d);
        }

        return format.format(valor);
    }
}
