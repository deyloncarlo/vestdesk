package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuantidadeTamanho and its DTO QuantidadeTamanhoDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeloVestuarioMapper.class})
public interface QuantidadeTamanhoMapper extends EntityMapper<QuantidadeTamanhoDTO, QuantidadeTamanho> {

    @Mapping(source = "modeloVestuario.id", target = "modeloVestuarioId")
    QuantidadeTamanhoDTO toDto(QuantidadeTamanho quantidadeTamanho);

    @Mapping(source = "modeloVestuarioId", target = "modeloVestuario")
    QuantidadeTamanho toEntity(QuantidadeTamanhoDTO quantidadeTamanhoDTO);

    default QuantidadeTamanho fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuantidadeTamanho quantidadeTamanho = new QuantidadeTamanho();
        quantidadeTamanho.setId(id);
        return quantidadeTamanho;
    }
}
