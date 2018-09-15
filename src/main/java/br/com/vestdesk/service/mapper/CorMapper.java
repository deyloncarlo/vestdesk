package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.CorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cor and its DTO CorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorMapper extends EntityMapper<CorDTO, Cor> {


    @Mapping(target = "listaProdutos", ignore = true)
    Cor toEntity(CorDTO corDTO);

    default Cor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cor cor = new Cor();
        cor.setId(id);
        return cor;
    }
}
