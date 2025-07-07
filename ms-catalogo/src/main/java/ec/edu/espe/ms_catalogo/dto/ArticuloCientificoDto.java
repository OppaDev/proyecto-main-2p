package ec.edu.espe.ms_catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloCientificoDto {
    // Campos de Publicacion
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    // Campos específicos de Articulo
    private String revista;
    private String doi;
    private String areaInvestigacion;
    private String fechaPublicacion;

    private long idAutor;
    private String tipo;
}