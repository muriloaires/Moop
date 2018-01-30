package brmobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 12/08/2017.
 */

public class DisponibilidadeBem {
    private Long id;
    private BemComum bemUsoComum;
    private Integer diaSemana;
    private Date horarioInicial;
    private Date horarioFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BemComum getBemUsoComum() {
        return bemUsoComum;
    }

    public void setBemUsoComum(BemComum bemUsoComum) {
        this.bemUsoComum = bemUsoComum;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Date getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(Date horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public Date getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Date horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
}
