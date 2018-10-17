package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.service.dto.PedidoDTO;

/**
 * Mapper for the entity Pedido and its DTO PedidoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PedidoMapper
{
	Pedido toEntity(PedidoDTO pedidoDTO);

	PedidoDTO toDto(Pedido pedido);

	default Pedido fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Pedido pedido = new Pedido();
		pedido.setId(id);
		return pedido;
	}

}
