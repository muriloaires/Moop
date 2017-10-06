package mobi.moop.model.entities;

import java.util.Date;

/**
 * Created by murilo aires on 04/10/2017.
 */

public class DiaBemComum {

    public static final String DIA_NAO_DISPONIVEL = "SD";
    public static final String DIA_LOTADO = "ND";
    public static final String DISPONIVEL = "D";


    private Integer dia;
    private Integer mes;
    private Date data;
    private String status;

    public Integer getDia() {
        return dia;
    }

    public Integer getMes() {
        return mes;
    }

    public Date getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
