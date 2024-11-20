package com.aluracursos.literalura.models.entity.libros;

import com.aluracursos.literalura.models.entity.autor.Autor;
import com.aluracursos.literalura.models.entity.autor.DatosAutor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages")List<String> lenguajes,
        @JsonAlias("download_count") int numeroDeDescargas,
        @JsonAlias("authors") List<DatosAutor> autor
) {
}
