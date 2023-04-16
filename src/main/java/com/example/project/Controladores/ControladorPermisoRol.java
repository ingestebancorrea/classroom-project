package com.example.project.Controladores;

import com.example.project.Modelos.Permiso;
import com.example.project.Modelos.PermisosRol;
import com.example.project.Modelos.Rol;
import com.example.project.Repositorios.RepositorioPermiso;
import com.example.project.Repositorios.RepositorioPermisosRol;
import com.example.project.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ControladorPermisosRol {
    @Autowired
    private RepositorioPermisosRol miRepositorioPermisoRol;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("/permisos_rol")
    public List<PermisosRol> index(){
        return this.miRepositorioPermisoRol.findAll();
    }

    /**
     * Asignación rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/permisos_rol/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol create(@PathVariable String id_rol, @PathVariable String id_permiso){

        PermisosRol nuevo = new PermisosRol();
        Rol elRol = this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso = this.miRepositorioPermiso.findById(id_permiso).get();

        if (elRol != null && elPermiso != null){
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.miRepositorioPermisoRol.save(nuevo);
        } else{
            return null;
        }
    }

    @GetMapping("/permisos_rol/{id}")
    public PermisosRol show(@PathVariable String id){
        PermisosRol permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        return permisosRolesActual;
    }

    /**
     * Modificación Rol y Permiso
     * @param id
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @PutMapping("/permisos_rol/{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRol permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();
        if(permisosRolesActual != null && elPermiso != null && elRol != null){
            permisosRolesActual.setPermiso(elPermiso);
            permisosRolesActual.setRol(elRol);
            return this.miRepositorioPermisoRol.save(permisosRolesActual);
        } else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/permisos_rol/{id}")
    public  void delete(@PathVariable String id){
        PermisosRol permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        if (permisosRolesActual!=null){
            this.miRepositorioPermisoRol.delete(permisosRolesActual);
        }
    }

}