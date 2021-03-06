package app.pelisParaVos.servicios;

import app.pelisParaVos.entidades.Imagen;
import app.pelisParaVos.entidades.Usuario;
import app.pelisParaVos.errores.ErrorServicio;
import app.pelisParaVos.repositorios.UsuarioRepositorio;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio urepo;
    
    @Autowired
    private ImagenServicio iservicio;
    
    @Transactional
    public Usuario save(MultipartFile archivo, String nombre, String apellido, LocalDate fechaNacimiento, String email, String password1, String password2) throws ErrorServicio{
        
        validator(nombre,apellido,fechaNacimiento, email, password1, password2);
        Imagen foto = iservicio.save(archivo);
        Usuario u = new Usuario(nombre, apellido, fechaNacimiento, foto, email, password1);
        return urepo.save(u);
    }
    
    @Transactional
    public Usuario edit(String id, MultipartFile archivo, String nombre, String apellido, LocalDate fechaNacimiento, String email, String password1, String password2) throws ErrorServicio{
        Optional<Usuario> resp = urepo.findById(id);
        if(resp.isPresent()){
            validator(nombre,apellido, fechaNacimiento, email, password1, password2);
            Usuario u = resp.get();
            Imagen foto = iservicio.save(archivo);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setFechaNacimiento(fechaNacimiento);
            u.setEmail(email);
            u.setPassword(password2);
            u.setFoto(foto);
            return urepo.save(u);
        }else{
            return null;
        }
    }
    
    public List<Usuario> buscarPorNombre(String nombre){
        return urepo.buscarPorNombre(nombre);
    }
    
    public void validator(String nombre, String apellido, LocalDate fechaNacimiento, String email, String password1, String password2) throws ErrorServicio{
     
        if (nombre == null || nombre.trim().isEmpty()){
            throw new ErrorServicio ("El nombre no puede ser nulo o vac??o");
        }
        if (apellido == null || apellido.trim().isEmpty()){
            throw new ErrorServicio ("El apellido no puede ser nulo o vac??o");
        }
        if (fechaNacimiento == null){
            throw new ErrorServicio ("La fecha de nacimiento no puede ser nula");
        }
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        if ( periodo.getYears()< 16){
            throw new ErrorServicio ("El usuario no puede ser menor de 16 a??os");
        }
        if(password1 == null || password1.trim().isEmpty()){
            throw new ErrorServicio("La contrase??a no puede ser nula o estar vac??a");
        }
        if(password1.length() < 6){
            throw new ErrorServicio("La contrase??a no puede ser menor a 6 car??cteres");
        }
        if(!password1.equals(password2)){
            throw new ErrorServicio("Las contrase??as no coinciden");
        }
        if(email == null || email.isEmpty()){
            throw new ErrorServicio ("El email no puede ser nulo o estar vac??o");
        }
        if(urepo.buscarPorEmail(email) != null){
            throw new ErrorServicio("El email ingresado ya est?? registrado en la base de datos");
        }
    }
}
