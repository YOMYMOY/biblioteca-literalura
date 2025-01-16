package com.yomymoy.biblioteca_literalura.repository;

import com.yomymoy.biblioteca_literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE (:anio BETWEEN a.fechaDeNacimiento AND COALESCE(a.fechaDeFallecimiento, :anio))")
    List<Autor> findByAnio(int anio);
}
