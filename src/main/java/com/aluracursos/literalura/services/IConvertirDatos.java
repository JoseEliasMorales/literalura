package com.aluracursos.literalura.services;

public interface IConvertirDatos {
    <T> T convertirDatos(String json, Class<T> clase);
}
