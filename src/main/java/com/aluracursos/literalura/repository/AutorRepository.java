package com.aluracursos.literalura.repository;


import com.aluracursos.literalura.models.entity.autor.Autor;
import com.aluracursos.literalura.models.entity.libros.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombreAutor AND l.titulo = :tituloLibro ")
    Optional<Libro> findByTituloAndAutor(String tituloLibro, String nombreAutor);

    @Query("SELECT l FROM Libro l")
    List<Libro> buscarTodosLosLibros();

    @Query("SELECT a FROM Autor a WHERE a.anioDeNacimiento <= :anioBuscado AND a.anioDeFallecimiento >= :anioBuscado")
    List<Autor> buscarPorAnio(int anioBuscado);


}
