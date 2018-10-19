package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoMinDTO implements Serializable
{

	private Long id;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		PedidoMinDTO pedidoDTO = (PedidoMinDTO) o;
		if (pedidoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), pedidoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

}
