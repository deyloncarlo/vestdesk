package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Cliente;
import br.com.vestdesk.service.dto.ClienteDTO;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = { PedidoMapper.class })
public interface ClienteMapper
{

	ClienteDTO toDto(Cliente cliente);

	Cliente toEntity(ClienteDTO clienteDTO);

	default Cliente fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Cliente cliente = new Cliente();
		cliente.setId(id);
		return cliente;
	}
}