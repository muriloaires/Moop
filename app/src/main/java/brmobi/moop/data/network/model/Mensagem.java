package brmobi.moop.data.network.model;

import java.util.Date;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class Mensagem {
    private Long id;
    private Usuario dePerfil;
    private Usuario paraPerfil;
    private String mensagem;
    private Date createdAt;
    private Boolean isFromLoggedUser;
    private Usuario otherUser;

    public Usuario getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(Usuario otherUser) {
        this.otherUser = otherUser;
    }

    public Boolean getFromLoggedUser() {
        return isFromLoggedUser;
    }

    public void setFromLoggedUser(Boolean fromLoggedUser) {
        isFromLoggedUser = fromLoggedUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getDePerfil() {
        return dePerfil;
    }

    public void setDePerfil(Usuario dePerfil) {
        this.dePerfil = dePerfil;
    }

    public Usuario getParaPerfil() {
        return paraPerfil;
    }

    public void setParaPerfil(Usuario paraPerfil) {
        this.paraPerfil = paraPerfil;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
