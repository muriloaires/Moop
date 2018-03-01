package brmobi.moop.data.network.model;

/**
 * Created by murilo aires on 26/07/2017.
 */

public class Unidade {
    private Long id;
    private Integer numero;
    private Bloco bloco;

    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }
}
