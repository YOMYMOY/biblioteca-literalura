package com.yomymoy.biblioteca_literalura.principal;

import com.yomymoy.biblioteca_literalura.model.*;
import com.yomymoy.biblioteca_literalura.repository.AutorRepository;
import com.yomymoy.biblioteca_literalura.repository.LibroRepository;
import com.yomymoy.biblioteca_literalura.service.ConsumoAPI;
import com.yomymoy.biblioteca_literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    *******************************
                    \tBIBLIOTECA LITERALURA
                    *******************************
                    1 - Buscar y agregar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    0 - Salir
                    """;
            System.out.println(menu);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarYAgregrarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Finalizando aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Escriba el titulo del libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        return datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
    }

    private void buscarYAgregrarLibro() {
        Optional<DatosLibro> datos = getDatosLibro();
        if (datos.isPresent()) {
            DatosLibro datosLibro = datos.get();
            //Buscar si el libro ya está registrado
            if (libroRepository.existsByTitulo(datosLibro.titulo())) {
                Libro libro = libroRepository.findByTitulo(datosLibro.titulo());
                System.out.println("El libro ya se encuentra registrado en la base de datos.");
                System.out.println(libro);
                return;
            }
            //Agregar al autor si no está registrado
            Autor autor;
            if (!datosLibro.autor().isEmpty()) {
                DatosAutor datosAutor = datosLibro.autor().get(0);
                autor = autorRepository.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> {
                            Autor nuevoAutor = new Autor(datosAutor);
                            return autorRepository.save(nuevoAutor);
                        });
            } else {
                autor = null;
            }


            //Registrar libro
            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);
            libroRepository.save(libro);
            System.out.println("Libro agregado a la base de datos:");
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresPorAnio() {
        System.out.println("Ingrese el año en el que desea buscar a los autores vivos: ");
        var anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autoresVivos = autorRepository.findByAnio(anio);
        if (!autoresVivos.isEmpty()) {
            autoresVivos.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        }
    }

    private void listarLibrosPorIdiomas() {
        System.out.println("""
                Elija una opción ingresando las dos letras correspondientes:
                es - Español
                en - Inglés
                pt - Portugués
                fr - Francés
                """);
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findLibrosByIdioma(idioma.toLowerCase());
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        }
    }
}
