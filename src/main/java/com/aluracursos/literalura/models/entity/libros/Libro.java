package com.aluracursos.literalura.models.entity.libros;

import com.aluracursos.literalura.models.entity.autor.Autor;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private List<String> idioma;
    private int numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibros l, Autor autor){
        if(l.titulo().length()>255){
            this.titulo = l.titulo().substring(0, 255);
        }else {
            this.titulo = l.titulo();
        }
        this.idioma = l.lenguajes();
        this.numeroDeDescargas = l.numeroDeDescargas();
        this.autor = autor;
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

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
