package com.literaturalura.challengerAlura.principal;

import com.literaturalura.challengerAlura.model.Datos;
import com.literaturalura.challengerAlura.model.DatosLibros;
import com.literaturalura.challengerAlura.model.Libro;
import com.literaturalura.challengerAlura.repository.LibrosRepository;
import com.literaturalura.challengerAlura.service.ConsumoAPI;
import com.literaturalura.challengerAlura.service.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/" ;
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibros> libros = new ArrayList<>();
    private LibrosRepository repository;
    public Principal(LibrosRepository repository) {
        this.repository = repository;
    }

    public void muestraelMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    |***************************************************|
                    |*****       SELECCIONE UNA OPCION            ******|
                    |***************************************************|
                    
                    1 - Buscar un libro
                    2 - Mostrar libros buscados
                    
                    
                    0 - Salir
                    
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    buscarPorAutor();
                    break;

                case 0:
                    opcion = 0;
                    System.out.println("|********************************|");
                    System.out.println("|    Cerrando aplicacion...      |");
                    System.out.println("|********************************|\n");
                    break;
                default:
                    System.out.println("|*********************|");
                    System.out.println("|  Opción Incorrecta. |");
                    System.out.println("|*********************|\n");
                    System.out.println("Intente con una nueva Opción");
                    break;
            }
        }



    }




    private DatosLibros buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        System.out.println("...Buscando");
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro Encontrado ");
           return libroBuscado.get();
        } else {
            System.out.println("Libro no encontrado");
            return null;
        }
    }

    private void mostrarLibrosBuscados() {
//        List<Libro> librosBuscados = new ArrayList<>();
//        librosBuscados = libros.stream()
//                .map(l -> new Libro(l))
//                .collect(Collectors.toList());
        List<Libro> librosGuardados = repository.findAll();
        if (librosGuardados.isEmpty()){
            System.out.println("No Hay libros buscados");
        }else{
            System.out.println("""
                    **********************
                    *       LIBROS       *
                    **********************                   
                    """);
            librosGuardados.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(l -> System.out.printf("""
                        ----- LIBRO -----
                        Titulo: %s
                        Autor: %s
                        Idioma: %s
                        Numero de descargas: %d
                        ------------------
                        """,l.getTitulo(),l.getAutor(),l.getIdiomas(),l.getNumeroDeDescargas()));
        }


    }

    private void buscarPorAutor() {
     //   DatosLibros
    }




    private void buscarLibroWeb(){
        DatosLibros datos = buscarLibro();
        Libro libro = new Libro(datos);
        if(libro == null){
            return;
        }
        try {
            boolean existeLibro = repository.existsByTitulo(libro.getTitulo());
            if (existeLibro){
                System.out.println("Ya existe el libro en la base de datos");
            }else{
                repository.save(libro);
                System.out.printf("""
                     ----- LIBRO -----
                        Titulo: %s
                        Autor: %s
                        Idioma: %s
                        Numero de descargas: %d
                        ------------------
                    """, datos.titulo(),datos.autor(),datos.idiomas().stream().findFirst(),datos.numeroDeDescargas());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede cargar el libro" + e.getMessage());
        }

    }


}
