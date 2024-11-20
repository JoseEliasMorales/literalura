package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.models.entity.autor.Autor;
import com.aluracursos.literalura.models.entity.autor.DatosAutor;
import com.aluracursos.literalura.models.entity.libros.Libro;
import com.aluracursos.literalura.models.results.ListaResultados;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.services.ConsumoAPI;
import com.aluracursos.literalura.services.ConvertirDatos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos convertirDatos = new ConvertirDatos();
    private AutorRepository repository;


    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() throws IOException, InterruptedException {

        var appActiva = -1;
        while (appActiva != 0){
            try {

            System.out.println("""
                ------------
                Por favor, escoge una opcion:
                
                1- buscar libro por titulo
                2- listar libros registrados
                3- listar autores registrados
                4- listar autores vivos en un determinado año
                5- listar libros por idioma
                0- salir
                
                ------------
                """);

                int opcionElegida= teclado.nextInt();
                teclado.nextLine();
                switch (opcionElegida){
                    case 1:
                        buscarTitulo();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        consultarAutoresPorAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Gracias por usar nuesta app!");
                        appActiva = 0;
                        break;
                    default:
                        System.out.println("Por favor, elige una opcion valida.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Por favor introduce una opción válida,");
                teclado.nextLine();
            }
        }
    }



    private void buscarTitulo() throws IOException, InterruptedException {

            System.out.println("Por favor, escribe el titulo que buscas: ");
            String tituloBuscado = teclado.nextLine();

            //Obtener titulo de la api
            var tituloObtenido = consumoAPI.obtenerLibro(tituloBuscado);

            //Comprobar si el titulo existe y convertirlo al tipo de dato buscado
            if (!tituloObtenido.isEmpty()){

                var tituloConvertido = convertirDatos.convertirDatos(tituloObtenido, ListaResultados.class);

                //Comprobar autores para agregar o no a la BD
                Set<DatosAutor> autoresSet = new HashSet<>();
                tituloConvertido.libros().forEach(libro -> {
                    if (!libro.autor().isEmpty()) {
                        autoresSet.add(libro.autor().get(0));
                    }
                });
                List<DatosAutor> listaAutoresUnicos = new ArrayList<>(autoresSet);

                //Llamamos a la funcion para agregar a los autores que no existen en la BD
                agregarAutoresABD(listaAutoresUnicos);

                //Despues de asegurado que el autor ya esta en la BD, agregamos los libros al autor que corresponde
                List<Libro> listaLibros = new ArrayList<>();
                tituloConvertido.libros().forEach(l -> {
                    if (!l.autor().isEmpty()){
                        var autor = encontrarAutor(l.autor().get(0).nombre());
                        var nuevoLibro = new Libro(l, autor);
                        listaLibros.add(nuevoLibro);
                        agregarLibrosaAutor(autor, listaLibros);
                    }

                });

                listaLibros.forEach(l-> imprimirLibro(l));
            }else {
                System.out.println("Titulo no encontrado.");
            }
    }

    //Funcion para comprobar si un autor esta en la BD, si no esta lo agrega
    private void agregarAutoresABD(List<DatosAutor> lista){
        for (int i = 0; i < lista.size(); i++) {
            var autorEncontrado = repository.findByNombre(lista.get(i).nombre());
            if (autorEncontrado.isEmpty()){
                Autor nuevoAutor = new Autor(lista.get(i));
                repository.save(nuevoAutor);
            }
        }
    }

    //Funcion para agregar autor a la BD
    private Autor encontrarAutor(String nombre){
        var autorEncontrado = repository.findByNombre(nombre);
        if (autorEncontrado.isPresent()){
            return autorEncontrado.get();
        }
        return null;
    }

    //Funcion para agregar libros al autor
    private void agregarLibrosaAutor(Autor autor, List<Libro> libros){
        //Si el autor no tiene libros, creamos un nuevo array.
        if (autor.getLibros()==null){
            autor.setLibros(new ArrayList<>());
        }

        //Comprobamos que los libros que queremos cargar no esten asociados al autor para no duplicar.
        List<Libro> librosNoDuplicados = libros.stream()
                .filter(libro -> !autor.getLibros().stream()
                        .anyMatch(l -> l.getTitulo().equals(libro.getTitulo()) && l.getAutor().getNombre().equals(libro.getAutor().getNombre())))
                .collect(Collectors.toList());


        //Verificamos que el libro no este en la BD y si no esta lo guardamos
        librosNoDuplicados.forEach(libro -> {
            var libroExistente = repository.findByTituloAndAutor(libro.getTitulo(), libro.getAutor().getNombre());
            if (!libroExistente.isPresent()) {
                autor.getLibros().add(libro);
                libro.setAutor(autor);
            }
        });
        repository.save(autor);
    }

    //Funcion para listar todos los libros existentes
    private void listarLibros() {
        var libros = repository.buscarTodosLosLibros();

        if (libros.size() > 0){
            libros.stream()
                    .forEach(l-> imprimirLibro(l));
        }
    }

    //Funcion para listar todos los autores
    private void listarAutores() {
        var autores = repository.findAll();

        autores.forEach(a-> imprimirAutores(a));
    }

    //Funcion para consultar que autores estaban vivos en determinado año
    private void consultarAutoresPorAnio() {
        System.out.println("Por favor, introduce el año: ");
        var anioBuscado = teclado.nextInt();
        teclado.nextLine();

        var autores = repository.buscarPorAnio(anioBuscado);
        if (!autores.isEmpty()){
            autores.forEach(a-> imprimirAutores(a));
        }else {
            System.out.println("No encontramos autores vivos en ese año.");
        }

    }

    //Listamos libros por idioma
    private void listarLibrosPorIdioma(){
        System.out.println("""
                \nPor favor elige el idioma buscado:
                
                1- en : Ingles
                2- es : Español
                3- fr : Frances
                4- fi : Finlandes
                5- de : Aleman
                6- pt : Portugués
                7. ru : Ruso
                
                """);

        var opcionBuscada = teclado.nextInt();
        teclado.nextLine();

        //Buscamos todos los libros
        List<Libro> libros = repository.buscarTodosLosLibros();

        //Creamos una lista con los libros filtrados por idioma
        List<Libro> librosFiltrados = new ArrayList<>();

        switch (opcionBuscada){
            case 1:
                librosFiltrados = buscarIdioma(libros, "en");
                break;
            case 2:
                librosFiltrados = buscarIdioma(libros, "es");
                break;
            case 3:
                librosFiltrados = buscarIdioma(libros, "fr");
                break;
            case 4:
                librosFiltrados = buscarIdioma(libros, "fi");
                break;
            case 5:
                librosFiltrados = buscarIdioma(libros, "de");
                break;
            case 6:
                librosFiltrados = buscarIdioma(libros, "pt");
                break;
            case 7:
                librosFiltrados = buscarIdioma(libros, "ru");
                break;
            default:
                System.out.println("No encontramos libros en ese idioma.");
                break;
        }

        if (librosFiltrados.size() > 0){
            librosFiltrados.stream()
                    .forEach(l-> imprimirLibro(l));
        }

    }

    //Funcion para buscar idioma
    public List<Libro> buscarIdioma(List<Libro> libros, String idioma){
        var librosFiltrados = libros.stream()
                .filter(l->l.getIdioma().get(0).equals(idioma))
                .collect(Collectors.toList());

        return librosFiltrados;
    }

    //Funcion para imprimir libros
    public void imprimirLibro(Libro l){
        System.out.println("""
                            \n----- LIBRO -----
                            
                            Titulo: %s
                            Autor: %s
                            Idioma: %s
                            Numero de descargas: %s
                            
                            -----------------
                            """.formatted(l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), l.getNumeroDeDescargas()));
    }

    public void imprimirAutores(Autor a){
        System.out.println("""
                \nAutor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: %s
                """.formatted(a.getNombre(), a.getAnioDeNacimiento(), a.getAnioDeFallecimiento(), a.getLibros()));
    }

}
