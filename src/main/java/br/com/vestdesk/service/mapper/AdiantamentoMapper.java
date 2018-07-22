package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.AdiantamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Adiantamento and its DTO AdiantamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {FormaPagamentoMapper.class})
public interface AdiantamentoMapper extends EntityMapper<AdiantamentoDTO, Adiantamento> {

    @Mapping(source = "formaPagemento.id", target = "formaPagementoId")
    AdiantamentoDTO toDto(Adiantamento adiantamento);

    @Mapping(source = "formaPagementoId", target = "formaPagemento")
    Adiantamento toEntity(AdiantamentoDTO adiantamentoDTO);

    default Adiantamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adiantamento adiantamento = new Adiantamento();
        adiantamento.setId(id);
        return adiantamento;
    }
}
