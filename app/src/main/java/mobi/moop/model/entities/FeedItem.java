package mobi.moop.model.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class FeedItem  implements Serializable{
    private Long id;

    private String titulo;

    private String texto;

    private String imagem;

    private boolean isPrivado;

    private Date createdAt;

    private Date updatedAt;

    private Condominio condominio;

    private Usuario perfil;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getImagem() {
        return imagem;
    }

    public boolean isPrivado() {
        return isPrivado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public Usuario getPerfil() {
        return perfil;
    }
}
