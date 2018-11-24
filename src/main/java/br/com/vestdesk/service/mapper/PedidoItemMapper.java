package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.service.dto.PedidoItemDTO;
import br.com.vestdesk.service.dto.PedidoItemMinDTO;

/**
 * Mapper for the entity PedidoItem and its DTO PedidoItemDTO.
 */
@Mapper(componentModel = "spring", uses = { ProdutoMapper.class, PedidoMapper.class })
public interface PedidoItemMapper
{

	PedidoItem toEntity(PedidoItemDTO pedidoItem);

	PedidoItem toEntity(PedidoItemMinDTO pedidoItem);

	PedidoItemDTO toDto(PedidoItem pedidoItem);

	PedidoItemMinDTO toMinDto(PedidoItem pedidoItem);

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
