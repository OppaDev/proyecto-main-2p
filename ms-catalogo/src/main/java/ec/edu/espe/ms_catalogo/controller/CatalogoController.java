package ec.edu.espe.ms_catalogo.controller;

import ec.edu.espe.ms_catalogo.dto.CatalogoDto;
import ec.edu.espe.ms_catalogo.entity.Catalogo;
import ec.edu.espe.ms_catalogo.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogo") // Ruta base para el API
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    // Helper para convertir Entidad a DTO
    private CatalogoDto convertToDto(Catalogo catalogo) {
        CatalogoDto dto = new CatalogoDto();
        dto.setId(catalogo.getId());
        dto.setTitulo(catalogo.getTitulo());
        dto.setAnioPublicacion(catalogo.getAnioPublicacion());
        dto.setEditorial(catalogo.getEditorial());
        dto.setIsbn(catalogo.getIsbn());
        dto.setResumen(catalogo.getResumen());
        dto.setIdioma(catalogo.getIdioma());
        dto.setTipoPublicacion(catalogo.getTipoPublicacion());
        dto.setGenero(catalogo.getGenero());
        dto.setNumeroPaginas(catalogo.getNumeroPaginas());
        dto.setEdicion(catalogo.getEdicion());
        dto.setRevista(catalogo.getRevista());
        dto.setDoi(catalogo.getDoi());
        dto.setAreaInvestigacion(catalogo.getAreaInvestigacion());
        dto.setFechaPublicacionArticulo(catalogo.getFechaPublicacionArticulo());
        dto.setFechaRegistro(catalogo.getFechaRegistro());
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<CatalogoDto>> listarTodoElCatalogo() {
        List<Catalogo> catalogos = catalogoService.obtenerTodoElCatalogo();
        List<CatalogoDto> dtos = catalogos.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoDto> obtenerCatalogoPorId(@PathVariable Long id) {
        Catalogo catalogo = catalogoService.obtenerPorId(id);
        if (catalogo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(catalogo));
    }
}