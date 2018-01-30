package brmobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class Chamado {
    private Long id;
    private Condominio condominio;
    private Usuario perfil;
    private String titulo;
    private String texto;
    private String status;
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Usuario getPerfil() {
        return perfil;
    }

    public void setPerfil(Usuario perfil) {
        this.perfil = perfil;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

}
