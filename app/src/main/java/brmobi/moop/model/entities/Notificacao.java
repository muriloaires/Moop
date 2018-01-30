package brmobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 21/12/2017.
 */

public class Notificacao {

    private Long id;
    private String tipo;
    private String titulo;
    private String mensagem;
    private Integer idObj;
    private Condominio condominio;
    private Usuario perfil;
    private Usuario perfilTo;

    private Date createdAt;

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public Usuario getPerfil() {
        return perfil;
    }

    public Usuario getPerfilTo() {
        return perfilTo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getIdObj() {
        return idObj;
    }
}
