package brmobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 14/08/2017.
 */

public class ReservaBemComum {

    private Long id;
    private Usuario perfil;
    private DisponibilidadeBem disponibilidadeUsoBemComum;
    private BemComum bemUsoComum;
    private Date dataUso;
    private Date createdAt;
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getPerfil() {
        return perfil;
    }

    public void setPerfil(Usuario perfil) {
        this.perfil = perfil;
    }

    public DisponibilidadeBem getDisponibilidadeUsoBemComum() {
        return disponibilidadeUsoBemComum;
    }

    public void setDisponibilidadeUsoBemComum(DisponibilidadeBem disponibilidadeUsoBemComum) {
        this.disponibilidadeUsoBemComum = disponibilidadeUsoBemComum;
    }

    public Date getDataUso() {
        return dataUso;
    }

    public void setDataUso(Date dataUso) {
        this.dataUso = dataUso;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BemComum getBemUsoComum() {
        return bemUsoComum;
    }

    public void setBemUsoComum(BemComum bemUsoComum) {
        this.bemUsoComum = bemUsoComum;
    }
}
