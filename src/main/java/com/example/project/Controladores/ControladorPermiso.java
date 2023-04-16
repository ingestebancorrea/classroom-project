package com.example.project.Controladores;

import com.example.project.Exception.ResourceNotFoundException;
import com.example.project.Modelos.Permiso;
import com.example.project.Modelos.Rol;
import com.example.project.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ControladorPermiso {
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("/permisos")
    public List<Permiso> getAllPermisos(){
        return miRepositorioPermiso.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/permiso")
    public Permiso createPermiso(@RequestBody Permiso infoRol ) {
        return this.miRepositorioPermiso.save(infoRol);
    }

    @GetMapping("/permiso/{id}")
    public ResponseEntity<Permiso> getPermisoById(@PathVariable String id){
        Permiso permiso = this.miRepositorioPermiso.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permiso not exist with id :" + id));
        return ResponseEntity.ok(permiso);
    }

    @PutMapping("/permiso/{id}")
    public Permiso updatePermiso(@PathVariable String id,@RequestBody Permiso infoPermiso){
        Permiso permiso = this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if (permiso!=null){
            permiso.setUrl(infoPermiso.getUrl());
            permiso.setMetodo(infoPermiso.getMetodo());
            return this.miRepositorioPermiso.save(permiso);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/permiso/{id}")
    public void deletePermiso(@PathVariable String id){
        Permiso permiso=this.miRepositorioPermiso
                .findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Permiso not exist with id :" + id));
        if (permiso!=null){
            this.miRepositorioPermiso.delete(permiso);
        }
    }
}
