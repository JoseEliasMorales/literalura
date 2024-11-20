# Proyecto LITERALURA

---

### Proyecto que forma parte del programa ONE - ORACLE NEXT Education

<div align="center">

![One](src/main/java/com/aluracursos/literalura/assets/logoOne.webp)
![ALura](src/main/java/com/aluracursos/literalura/assets/logo-aluraespanhol.svg)

</div>

---

### Objetivos:

Desarrollar un Catálogo de Libros que ofrezca interacción textual (vía consola) con los usuarios, proporcionando al menos 5 opciones de interacción. Los libros se buscarán a través de una API específica.

### Tecnologias utilizadas:
![Static Badge](https://img.shields.io/badge/Lenguaje-JAVA_17-blue)
![Static Badge](https://img.shields.io/badge/Framework-SPRING-green)



##### Dependencia utilizadas:
```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.18.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```
---

## Funcionalidades

#### Menu principal

![img.png](src/main/java/com/aluracursos/literalura/assets/img.png)

Al buscar por titulo consultamos a la API, y si el titulo y el autor no existen, agregamos el autor a la BD y el titulo, y mostramos los datos de los libros encontrados por pantalla.

![img_1.png](src/main/java/com/aluracursos/literalura/assets/img_1.png)

Tambien podemos listar los libros registrados directamente en la BD y mostrarlos de la misma manera.


Otra funcion es listar los autores registrados con sus libros:
![img_2.png](src/main/java/com/aluracursos/literalura/assets/img_2.png)


Si elegimos la opcion buscar autores vivos en un determinado año nos preguntara que año buscamos y nos mostrara la lista de los autores registrados en la BD:
![img_3.png](src/main/java/com/aluracursos/literalura/assets/img_3.png)


Y en la opcion de idiomas podemos buscar los libros en el idioma de la lista:
![img_4.png](src/main/java/com/aluracursos/literalura/assets/img_4.png)

