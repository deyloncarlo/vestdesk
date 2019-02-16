package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cor entity.
 */
public class CorDTO implements Serializable
{

	private String nome;

	private String codigo;

	protected Long id;

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

	public String getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	@Override
	public String toString()
	{
		return "CorDTO{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + "}";
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
}
