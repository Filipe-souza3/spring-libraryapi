package cursoJPA.libraryapi.enums;

import cursoJPA.libraryapi.exception.IllegalArgumentException;

public enum GeneroLivro {
        FICCAO,
        FANTASIA,
        MISTERIO,
        ROMANCE,
        BIOGRAFIA,
        CIENCIA;

        public String genero;

        public static GeneroLivro verificar(String tipogenero){
                try {
                        return GeneroLivro.valueOf(tipogenero);
                } catch (Exception e) {
                        throw new IllegalArgumentException("Genero invalido");
                }       
        }
}
