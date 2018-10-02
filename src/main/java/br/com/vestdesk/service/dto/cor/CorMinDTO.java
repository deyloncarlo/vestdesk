package br.com.vestdesk.service.dto.cor;

import java.io.Serializable;
import java.util.Objects;

import br.com.vestdesk.service.dto.EntityDTO;

/**
 * A DTO for the Cor entity.
 */
public class CorMinDTO implements Serializable
{

	private String nome;

	protected Long id;

	@Override
	public String toString()
	{
		return "CorMinDTO{" + "id=" + getId() + "}";
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

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

		EntityDTO clienteDTO = (EntityDTO) o;
		if (clienteDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), clienteDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}
}
