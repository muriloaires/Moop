package brmobi.moop.data.network.model;

import java.util.Date;

/**
 * Created by murilo aires on 16/10/2017.
 */

public class PerfilHabitacional {
    private Long id;
    private Usuario perfil;
    private Unidade unidadeHabitacional;
    private boolean isMorador;
    private boolean isProprietario;
    private boolean isLiberado;
    private Date createdAt;

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

    public Unidade getUnidadeHabitacional() {
        return unidadeHabitacional;
    }

    public void setUnidadeHabitacional(Unidade unidadeHabitacional) {
        this.unidadeHabitacional = unidadeHabitacional;
    }

    public boolean isMorador() {
        return isMorador;
    }

    public void setMorador(boolean morador) {
        isMorador = morador;
    }

    public boolean isProprietario() {
        return isProprietario;
    }

    public void setProprietario(boolean proprietario) {
        isProprietario = proprietario;
    }

    public boolean isLiberado() {
        return isLiberado;
    }

    public void setLiberado(boolean liberado) {
        isLiberado = liberado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
