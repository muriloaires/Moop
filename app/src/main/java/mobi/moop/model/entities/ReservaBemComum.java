package mobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 14/08/2017.
 */

public class ReservaBemComum {

    private Long id;
    private Usuario perfil;
    private DisponibilidadeBem disponibilidadeUsoBemComum;
    private Date dataUso;

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
}
