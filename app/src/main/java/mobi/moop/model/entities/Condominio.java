package mobi.moop.model.entities;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by murilo aires on 26/07/2017.
 */

@Entity
public class Condominio implements Serializable{
    private static final long serialVersionUID = 29022898;
    @Id
    private Long id;

    private String nome;

    private String cep;

    private String logradouro;

    private boolean isHorizontal;

    private boolean isLiberado;

    private boolean isSindico;

    @Transient
    private boolean selected;

    @Generated(hash = 24430744)
    public Condominio(Long id, String nome, String cep, String logradouro,
            boolean isHorizontal, boolean isLiberado, boolean isSindico) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
        this.logradouro = logradouro;
        this.isHorizontal = isHorizontal;
        this.isLiberado = isLiberado;
        this.isSindico = isSindico;
    }

    @Generated(hash = 736672542)
    public Condominio() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String Logradouro) {
        this.logradouro = Logradouro;
    }

    public boolean getIsHorizontal() {
        return this.isHorizontal;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public boolean getIsLiberado() {
        return this.isLiberado;
    }

    public void setIsLiberado(boolean isLiberado) {
        this.isLiberado = isLiberado;
    }

    public boolean getIsSindico() {
        return this.isSindico;
    }

    public void setIsSindico(boolean isSindico) {
        this.isSindico = isSindico;
    }
}
