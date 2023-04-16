package com.example.project.Repositorios;

import com.example.project.Modelos.PermisosRol;
import com.example.project.Modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPermisosRol extends JpaRepository<PermisosRol, String> {}
