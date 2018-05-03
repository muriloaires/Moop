package brmobi.moop.data.network.model;

import java.io.Serializable;
import java.util.Date;

import brmobi.moop.data.db.model.Condominio;

/**
 * Created by murilo aires on 27/07/2017.
 */

public class FeedItem implements Serializable {

    private Long id;
    private String titulo;
    private String texto;
    private String imagem;
    private boolean isPrivado;
    private Date createdAt;
    private Date updatedAt;
    private int wImage;
    private int hImage;
    private Date data;
    private boolean curtidaPeloUsuario;
    private Integer comentarios;
    private Integer curtidas;
    private Boolean isFromLoggedUser;

    public Boolean getFromLoggedUser() {
        return isFromLoggedUser;
    }

    public void setFromLoggedUser(Boolean fromLoggedUser) {
        isFromLoggedUser = fromLoggedUser;
    }

    public int getwImage() {
        return wImage;
    }

    public int gethImage() {
        return hImage;
    }

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

    public Date getData() {
        return data;
    }

    public Integer getComentarios() {
        return comentarios;
    }

    public boolean isCurtidaPeloUsuario() {
        return curtidaPeloUsuario;
    }

    public void setCurtidaPeloUsuario(boolean curtidaPeloUsuario) {
        this.curtidaPeloUsuario = curtidaPeloUsuario;
    }

    public Integer getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(Integer curtidas) {
        this.curtidas = curtidas;
    }
}
