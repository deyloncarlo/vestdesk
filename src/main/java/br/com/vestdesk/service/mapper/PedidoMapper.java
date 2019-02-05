package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.service.dto.PedidoDTO;
import br.com.vestdesk.service.dto.PedidoGridDTO;
import br.com.vestdesk.service.dto.PedidoMinDTO;

/**
 * Mapper for the entity Pedido and its DTO PedidoDTO.
 */
@Mapper(componentModel = "spring", uses = { PedidoItemMapper.class, ConfiguracaoLayoutMapper.class })
public interface PedidoMapper
{
	Pedido toEntity(PedidoDTO pedidoDTO);

	Pedido toEntity(PedidoMinDTO pedido);

	PedidoDTO toDto(Pedido pedido);

	PedidoMinDTO toMinDto(Pedido pedido);

	PedidoGridDTO toGridDto(Pedido pedido);

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
