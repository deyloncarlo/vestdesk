package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MaterialTamanho entity.
 */
public class MaterialTamanhoDTO implements Serializable
{

	private Long id;

	@NotNull
	private Float quantidadeMaterial;

	private Long produtoId;

	private Long materialId;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Float getQuantidadeMaterial()
	{
		return this.quantidadeMaterial;
	}

	public void setQuantidadeMaterial(Float quantidadeMaterial)
	{
		this.quantidadeMaterial = quantidadeMaterial;
	}

	public Long getMaterialId()
	{
		return this.materialId;
	}

	public void setMaterialId(Long materialId)
	{
		this.materialId = materialId;
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

		MaterialTamanhoDTO materialTamanhoDTO = (MaterialTamanhoDTO) o;
		if (materialTamanhoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), materialTamanhoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "MaterialTamanhoDTO{" + "id=" + getId() + ", quantidadeMaterial=" + getQuantidadeMaterial() + "}";
	}

	public Long getProdutoId()
	{
		return this.produtoId;
	}

	public void setProdutoId(Long produtoId)
	{
		this.produtoId = produtoId;
	}
}
