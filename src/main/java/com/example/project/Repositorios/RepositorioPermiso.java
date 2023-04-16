package com.example.project.Repositorios;

import com.example.project.Modelos.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPermiso extends JpaRepository<Permiso, String> {}
