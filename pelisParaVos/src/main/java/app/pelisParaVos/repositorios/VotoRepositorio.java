package app.pelisParaVos.repositorios;

import app.pelisParaVos.entidades.Recomendacion;
import app.pelisParaVos.entidades.Usuario;
import app.pelisParaVos.entidades.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String>{
    
    @Query("SELECT a FROM Voto a WHERE a.votante = :votante")
    public List<Voto> buscarPorVotante(@Param("votante") Usuario votante);
    
    @Query("SELECT a FROM Voto a WHERE a.votada = :votada")
    public List<Voto> buscarPorVotada(@Param("votada") Recomendacion votada);
}
