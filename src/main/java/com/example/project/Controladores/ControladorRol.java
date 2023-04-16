package com.example.project.Controladores;

import com.example.project.Exception.ResourceNotFoundException;
import com.example.project.Modelos.Permiso;
import com.example.project.Modelos.Rol;
import com.example.project.Repositorios.RepositorioPermiso;
import com.example.project.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ControladorRol {
    @Autowired
    private RepositorioRol miRepositorioRol;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("/roles")
    public List<Rol> getAllRoles(){
        return miRepositorioRol.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/rol")
    public Rol createRol(@RequestBody Rol infoRol ){
        return this.miRepositorioRol.save(infoRol);
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable String id){
        Rol rol = this.miRepositorioRol.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol not exist with id :" + id));
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/rol/{id}")
    public Rol updateRol(@PathVariable String id,@RequestBody Rol infoRol){
        Rol rol = this.miRepositorioRol
                .findById(id)
                .orElse(null);
        if (rol!=null){
            rol.setNombre(infoRol.getNombre());
            return this.miRepositorioRol.save(rol);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/rol/{id}")
    public void deleteRol(@PathVariable String id){
        Rol rol=this.miRepositorioRol
                .findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Rol not exist with id :" + id));
        if (rol!=null){
            this.miRepositorioRol.delete(rol);
        }
    }

}
