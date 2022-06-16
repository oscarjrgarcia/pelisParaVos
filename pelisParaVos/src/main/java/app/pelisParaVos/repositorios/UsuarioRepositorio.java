package app.pelisParaVos.repositorios;

import app.pelisParaVos.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    @Query("SELECT a FROM Usuario a WHERE a.nombre = :nombre")
    public List<Usuario> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Usuario a WHERE a.apellido = :apellido")
    public List<Usuario> buscarPorApellido(@Param("apellido") String apellido);
    
    @Query("SELECT a FROM Usuario a WHERE a.email = :email")
    public Usuario buscarPorEmail(@Param("email") String mail);
}
