package ec.edu.espe.ms_catalogo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.ms_catalogo.dto.ArticuloCientificoDto;
import ec.edu.espe.ms_catalogo.dto.LibroDto;
import ec.edu.espe.ms_catalogo.dto.PublicacionRecibidaDto;
import ec.edu.espe.ms_catalogo.entity.Catalogo;
import ec.edu.espe.ms_catalogo.entity.TipoPublicacion;
import ec.edu.espe.ms_catalogo.repository.CatalogoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogoService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogoService.class);

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void procesarYGuardarPublicacion(PublicacionRecibidaDto publicacionRecibida) {
        try {
            Catalogo catalogo = new Catalogo();
            JsonNode datos = publicacionRecibida.getDatos();

            if ("LIBRO".equalsIgnoreCase(publicacionRecibida.getTipoPublicacion())) {
                LibroDto libroDto = objectMapper.treeToValue(datos, LibroDto.class);
                catalogo.setTipoPublicacion(TipoPublicacion.LIBRO);
                // Mapeo común
                catalogo.setTitulo(libroDto.getTitulo());
                catalogo.setAnioPublicacion(libroDto.getAnioPublicacion());
                catalogo.setEditorial(libroDto.getEditorial());
                catalogo.setIsbn(libroDto.getIsbn());
                catalogo.setResumen(libroDto.getResumen());
                catalogo.setIdioma(libroDto.getIdioma());
                // Mapeo específico de Libro
                catalogo.setGenero(libroDto.getGenero());
                catalogo.setNumeroPaginas(libroDto.getNumeroPaginas());
                catalogo.setEdicion(libroDto.getEdicion());
                logger.info("Procesando LIBRO: {}", libroDto.getTitulo());

            } else if ("ARTICULO".equalsIgnoreCase(publicacionRecibida.getTipoPublicacion())) {
                ArticuloCientificoDto articuloDto = objectMapper.treeToValue(datos, ArticuloCientificoDto.class);
                catalogo.setTipoPublicacion(TipoPublicacion.ARTICULO);
                // Mapeo común
                catalogo.setTitulo(articuloDto.getTitulo());
                catalogo.setAnioPublicacion(articuloDto.getAnioPublicacion());
                catalogo.setEditorial(articuloDto.getEditorial());
                catalogo.setIsbn(articuloDto.getIsbn()); // ISBN puede ser nulo para artículos
                catalogo.setResumen(articuloDto.getResumen());
                catalogo.setIdioma(articuloDto.getIdioma());
                // Mapeo específico de Artículo
                catalogo.setRevista(articuloDto.getRevista());
                catalogo.setDoi(articuloDto.getDoi());
                catalogo.setAreaInvestigacion(articuloDto.getAreaInvestigacion());
                catalogo.setFechaPublicacionArticulo(articuloDto.getFechaPublicacion());
                logger.info("Procesando ARTICULO: {}", articuloDto.getTitulo());

            } else {
                logger.warn("Tipo de publicación desconocido: {}", publicacionRecibida.getTipoPublicacion());
                return;
            }

            catalogoRepository.save(catalogo);
            logger.info("Publicación guardada en catálogo con ID: {}", catalogo.getId());

        } catch (JsonProcessingException e) {
            logger.error("Error al deserializar los datos de la publicación: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al procesar y guardar la publicación: {}", e.getMessage(), e);

        }
    }

    public List<Catalogo> obtenerTodoElCatalogo() {
        return catalogoRepository.findAll();
    }

    public Catalogo obtenerPorId(Long id) {
        return catalogoRepository.findById(id).orElse(null);
    }
}