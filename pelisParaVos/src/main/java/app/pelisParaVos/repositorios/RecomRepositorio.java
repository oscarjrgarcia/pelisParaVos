package app.pelisParaVos.repositorios;

import app.pelisParaVos.entidades.Recomendacion;
import app.pelisParaVos.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomRepositorio extends JpaRepository<Recomendacion, String>{
    
    @Query("SELECT a FROM Recomendacion a WHERE a.creador = :creador")
    public List<Recomendacion> buscarPorCreador(@Param("creador") Usuario creador);
    
    @Query("SELECT r FROM Recomendacion r INNER JOIN (SELECT v.votada FROM Voto v GROUP BY (v.votada) ORDER BY COUNT(v.votada) DESC) AS a ON r.id = v.votado.id")
    public List<Recomendacion> buscarPorMasVotada();
    
    @Query("SELECT a FROM Recomendacion a WHERE a.nombrePelicula = :nombrePelicula")
    public List<Recomendacion> buscarPorNombre(@Param("nombrePelicula") String nombrePelicula);
}
