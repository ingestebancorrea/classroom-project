package com.example.project.Modelos;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "permissions_rol")
public class PermisosRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="rol_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="permiso_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Permiso permiso;

    public PermisosRol() {
    }

    public PermisosRol(int id, Rol rol, Permiso permiso) {
        this.id = id;
        this.rol = rol;
        this.permiso = permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
