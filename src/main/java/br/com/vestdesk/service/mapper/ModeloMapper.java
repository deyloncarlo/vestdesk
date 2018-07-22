package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.ModeloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modelo and its DTO ModeloDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModeloMapper extends EntityMapper<ModeloDTO, Modelo> {



    default Modelo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modelo modelo = new Modelo();
        modelo.setId(id);
        return modelo;
    }
}
