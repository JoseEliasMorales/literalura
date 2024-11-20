package com.aluracursos.literalura.models.entity.autor;

import com.aluracursos.literalura.models.entity.libros.Libro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int anioDeNacimiento;
    private int anioDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}
    public Autor(DatosAutor a) {
        this.nombre = a.nombre();
        this.anioDeFallecimiento = a.anioFallecimiento();
        this.anioDeNacimiento=a.anioNacimiento();
    }
    public void agregarLibro(Libro libro) {
        if (libros == null) {
            libros = new ArrayList<>();
        }
        if (!libros.contains(libro)) { // Evitar duplicados
            libros.add(libro);
            libro.setAutor(this); // Sincronizar la relación
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(int anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public int getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(int anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


}
