package ec.edu.espe.ms_catalogo.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionRecibidaDto {
    private String tipoPublicacion;
    private JsonNode datos;
}