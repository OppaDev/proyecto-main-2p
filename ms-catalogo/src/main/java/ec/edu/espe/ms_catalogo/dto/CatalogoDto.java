package ec.edu.espe.ms_catalogo.dto;

import ec.edu.espe.ms_catalogo.entity.TipoPublicacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDto {
    private Long id;
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    private TipoPublicacion tipoPublicacion;
    private String genero;
    private Integer numeroPaginas;
    private String edicion;
    private String revista;
    private String doi;
    private String areaInvestigacion;
    private String fechaPublicacionArticulo;
    private LocalDateTime fechaRegistro;
}