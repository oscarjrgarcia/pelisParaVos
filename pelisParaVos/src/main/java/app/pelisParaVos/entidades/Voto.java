package app.pelisParaVos.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @ManyToOne
    private Usuario votante;
    
    @ManyToOne
    private Recomendacion votada;

    public Voto() {
    }

    public Voto(Usuario votante, Recomendacion votada) {
        this.votante = votante;
        this.votada = votada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getVotante() {
        return votante;
    }

    public void setVotante(Usuario votante) {
        this.votante = votante;
    }

    public Recomendacion getVotada() {
        return votada;
    }

    public void setVotada(Recomendacion votada) {
        this.votada = votada;
    }
    
    
}
