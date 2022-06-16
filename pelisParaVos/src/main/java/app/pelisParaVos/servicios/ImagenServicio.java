package app.pelisParaVos.servicios;

import app.pelisParaVos.entidades.Imagen;
import app.pelisParaVos.errores.ErrorServicio;
import app.pelisParaVos.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio irepo;
    
    public Imagen save(MultipartFile archivo) throws ErrorServicio{
        if(archivo != null){
            try{
            Imagen i = new Imagen();
            i.setMime(archivo.getContentType());
            i.setNombre(archivo.getName());
            i.setContenido(archivo.getBytes());
            return irepo.save(i);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
    
    public Imagen edit(String id, MultipartFile archivo) throws ErrorServicio{
        if(archivo != null){
            try{
            Imagen i = new Imagen();
            
            if(id != null){
                Optional<Imagen> resp = irepo.findById(id);
                if(resp.isPresent()){
                    i = resp.get();
                }
            }
            i.setMime(archivo.getContentType());
            i.setNombre(archivo.getName());
            i.setContenido(archivo.getBytes());
            return irepo.save(i);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
