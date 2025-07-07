package ec.edu.espe.ms_catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {
    // Campos de Publicacion
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    // Campos específicos de Libro
    private String genero;
    private int numeroPaginas;
    private String edicion;

    private long idAutor;
    private String tipo;
}