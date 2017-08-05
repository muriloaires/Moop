package mobi.moop.model.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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

    private String Logradouro;

    private boolean isHorizontal;

    @Generated(hash = 1246614547)
    public Condominio(Long id, String nome, String cep, String Logradouro,
            boolean isHorizontal) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
        this.Logradouro = Logradouro;
        this.isHorizontal = isHorizontal;
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
        return this.Logradouro;
    }

    public void setLogradouro(String Logradouro) {
        this.Logradouro = Logradouro;
    }

    public boolean getIsHorizontal() {
        return this.isHorizontal;
    }

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
}
