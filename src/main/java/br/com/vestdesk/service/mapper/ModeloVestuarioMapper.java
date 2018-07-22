package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModeloVestuario and its DTO ModeloVestuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, ModeloMapper.class})
public interface ModeloVestuarioMapper extends EntityMapper<ModeloVestuarioDTO, ModeloVestuario> {

    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "modelo.id", target = "modeloId")
    ModeloVestuarioDTO toDto(ModeloVestuario modeloVestuario);

    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "modeloId", target = "modelo")
    ModeloVestuario toEntity(ModeloVestuarioDTO modeloVestuarioDTO);

    default ModeloVestuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModeloVestuario modeloVestuario = new ModeloVestuario();
        modeloVestuario.setId(id);
        return modeloVestuario;
    }
}
