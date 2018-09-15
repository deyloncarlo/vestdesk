package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModeloVestuario and its DTO ModeloVestuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModeloVestuarioMapper extends EntityMapper<ModeloVestuarioDTO, ModeloVestuario> {


    @Mapping(target = "listaConfiguracaoProdutos", ignore = true)
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
