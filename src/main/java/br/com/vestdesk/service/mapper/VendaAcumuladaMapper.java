package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.service.dto.VendaAcumuladaDTO;

/**
 * Mapper for the entity VendaAcumulada and its DTO VendaAcumuladaDTO.
 */
@Mapper(componentModel = "spring", uses = { PedidoMapper.class, ProdutoMapper.class })
public interface VendaAcumuladaMapper
{

	VendaAcumuladaDTO toDto(VendaAcumulada vendaAcumulada);

	VendaAcumulada toEntity(VendaAcumuladaDTO vendaAcumuladaDTO);

	default VendaAcumulada fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		VendaAcumulada vendaAcumulada = new VendaAcumulada();
		vendaAcumulada.setId(id);
		return vendaAcumulada;
	}

}
