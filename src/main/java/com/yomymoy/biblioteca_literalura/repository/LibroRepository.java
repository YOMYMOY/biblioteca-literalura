package com.yomymoy.biblioteca_literalura.repository;

import com.yomymoy.biblioteca_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

    Libro findByTitulo(String titulo);

    @Query(value = "SELECT * FROM libros l WHERE :idioma = ANY(l.idiomas)", nativeQuery = true)
    List<Libro> findLibrosByIdioma(String idioma);
}
