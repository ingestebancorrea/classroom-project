package com.example.project.Controladores;

import com.example.project.Exception.ResourceNotFoundException;
import com.example.project.Modelos.Usuario;
import com.example.project.Repositorios.RepositorioRol;
import com.example.project.Repositorios.RepositorioUsuario;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;
    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("/users")
    public List<Usuario> getAllUsers(){
        return miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/rol/{rolId}/user") //create a new Rol for a User
    public ResponseEntity<Usuario> createUser(@PathVariable(value = "rolId") String rolId, @RequestBody Usuario infoUsuario) {
        Usuario user = miRepositorioRol.findById(rolId).map(rol -> {
            infoUsuario.setRol(rol);
            return miRepositorioUsuario.save(infoUsuario);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + rolId));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable String id){
        Usuario usuario = this.miRepositorioUsuario.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") String id, @RequestBody Usuario infoUsuario) {
        Usuario usuario = miRepositorioUsuario.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId " + id + "not found"));
        usuario.setCorreo(infoUsuario.getCorreo());
        //usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        usuario.setTipoIdentificacion(infoUsuario.getTipoIdentificacion());
        usuario.setNoDocumento(infoUsuario.getNoDocumento());
        usuario.setGenero(infoUsuario.getGenero());
        usuario.setDireccion(infoUsuario.getDireccion());
        usuario.setTelefono(infoUsuario.getTelefono());
        return new ResponseEntity<>(miRepositorioUsuario.save(usuario), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id){
        Usuario usuario=this.miRepositorioUsuario
                .findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        if (usuario!=null){
            this.miRepositorioUsuario.delete(usuario);
        }
    }

    @DeleteMapping("/rol/{rolId}/user")
    public ResponseEntity<List<Usuario>> deleteAllUsersOfRol(@PathVariable(value = "rolId") String rolId) {
        if (!miRepositorioRol.existsById(rolId)) {
            throw new ResourceNotFoundException("Not found Rol with id = " + rolId);
        }

        miRepositorioUsuario.deleteById(rolId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
