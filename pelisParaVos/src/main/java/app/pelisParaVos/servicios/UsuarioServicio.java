package app.pelisParaVos.servicios;

import app.pelisParaVos.entidades.Imagen;
import app.pelisParaVos.entidades.Usuario;
import app.pelisParaVos.errores.ErrorServicio;
import app.pelisParaVos.repositorios.UsuarioRepositorio;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio urepo;
    
    @Autowired
    private ImagenServicio iservicio;
    
    public Usuario save(MultipartFile archivo, String nombre, String apellido, LocalDate fechaNacimiento, String email, String password1, String password2) throws ErrorServicio{
        
        validator(nombre,apellido,fechaNacimiento, email, password1, password2);
        Imagen foto = iservicio.save(archivo);
        Usuario u = new Usuario(nombre, apellido, fechaNacimiento, foto, email, password1);
        return urepo.save(u);
    }
    
    public void validator(String nombre, String apellido, LocalDate fechaNacimiento, String email, String password1, String password2) throws ErrorServicio{
     
        if (nombre == null || nombre.trim().isEmpty()){
            throw new ErrorServicio ("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()){
            throw new ErrorServicio ("El apellido no puede ser nulo o vacío");
        }
        if (fechaNacimiento == null){
            throw new ErrorServicio ("La fecha de nacimiento no puede ser nula");
        }
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        if ( periodo.getYears()< 16){
            throw new ErrorServicio ("El usuario no puede ser menor de 16 años");
        }
        if(password1 == null || password1.trim().isEmpty()){
            throw new ErrorServicio("La contraseña no puede ser nula o estar vacía");
        }
        if(password1.length() < 6){
            throw new ErrorServicio("La contraseña no puede ser menor a 6 carácteres");
        }
        if(!password1.equals(password2)){
            throw new ErrorServicio("Las contraseñas no coinciden");
        }
        if(email == null || email.isEmpty()){
            throw new ErrorServicio ("El email no puede ser nulo o estar vacío");
        }
        if(urepo.buscarPorEmail(email) != null){
            throw new ErrorServicio("El email ingresado ya está registrado en la base de datos");
        }
    }
}
