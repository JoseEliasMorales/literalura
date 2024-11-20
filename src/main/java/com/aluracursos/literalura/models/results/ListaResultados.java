package com.aluracursos.literalura.models.results;

import com.aluracursos.literalura.models.entity.libros.DatosLibros;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaResultados(
        @JsonAlias("results")List<DatosLibros> libros
        ) {
}
