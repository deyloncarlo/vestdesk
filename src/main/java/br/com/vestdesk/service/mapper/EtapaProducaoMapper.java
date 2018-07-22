package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtapaProducao and its DTO EtapaProducaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtapaProducaoMapper extends EntityMapper<EtapaProducaoDTO, EtapaProducao> {



    default EtapaProducao fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapaProducao etapaProducao = new EtapaProducao();
        etapaProducao.setId(id);
        return etapaProducao;
    }
}
