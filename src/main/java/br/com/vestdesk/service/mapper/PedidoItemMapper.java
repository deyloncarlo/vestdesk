package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.service.dto.PedidoItemDTO;

/**
 * Mapper for the entity PedidoItem and its DTO PedidoItemDTO.
 */
@Mapper(componentModel = "spring", uses = { ProdutoMapper.class, PedidoMapper.class })
public interface PedidoItemMapper
{

	PedidoItem toEntity(PedidoItemDTO pedidoItem);

	PedidoItemDTO toDto(PedidoItem pedidoItem);

	default PedidoItem fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setId(id);
		return pedidoItem;
	}

}
