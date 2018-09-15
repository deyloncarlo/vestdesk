package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Material and its DTO MaterialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {


    @Mapping(target = "listaMaterialTamanhos", ignore = true)
    Material toEntity(MaterialDTO materialDTO);

    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
