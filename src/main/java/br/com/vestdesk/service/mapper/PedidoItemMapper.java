package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.PedidoItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PedidoItem and its DTO PedidoItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PedidoItemMapper extends EntityMapper<PedidoItemDTO, PedidoItem> {



    default PedidoItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setId(id);
        return pedidoItem;
    }
}
