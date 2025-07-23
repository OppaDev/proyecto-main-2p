package publicaciones.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name="Libro")
@Getter
@Setter
public class Libro extends Publicacion {
    private String genero;
    private int numeroPaginas;
    private String edicion;

    @ManyToOne
    @JoinColumn(name = "id_autor",nullable = false)
    @JsonBackReference
    private Autor autor;

}
