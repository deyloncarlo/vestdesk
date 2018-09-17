package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Cor entity.
 */
public class CorDTO implements Serializable
{

	private Long id;

	@NotNull
	private String codigo;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

		CorDTO corDTO = (CorDTO) o;
		if (corDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), corDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "CorDTO{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + "}";
	}
}
