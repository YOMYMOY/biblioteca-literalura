package com.yomymoy.biblioteca_literalura.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private List<String> idiomas;
    private Double cantidadDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas();
        this.cantidadDescargas = datosLibro.cantidadDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    @Override
    public String toString() {
        return "\n----- LIBRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNombre() : "-") +
                "\nIdioma: " + idiomas +
                "\nCantidad de descargas: " + cantidadDescargas +
                "\n-----------------\n";
    }
}
