package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.Tamanho;

/**
 * A DTO for the ConfiguracaoProduto entity.
 */
public class ConfiguracaoProdutoDTO implements Serializable
{

	private Long id;

	@NotNull
	private Tamanho tamanho;

	@NotNull
	private BigDecimal preco;

	private Long modeloVestuarioId;

	private Set<MaterialTamanhoDTO> listaMaterialTamanhos;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Tamanho getTamanho()
	{
		return this.tamanho;
	}

	public void setTamanho(Tamanho tamanho)
	{
		this.tamanho = tamanho;
	}

	public BigDecimal getPreco()
	{
		return this.preco;
	}

	public void setPreco(BigDecimal preco)
	{
		this.preco = preco;
	}

	public Long getModeloVestuarioId()
	{
		return this.modeloVestuarioId;
	}

	public void setModeloVestuarioId(Long modeloVestuarioId)
	{
		this.modeloVestuarioId = modeloVestuarioId;
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

		ConfiguracaoProdutoDTO configuracaoProdutoDTO = (ConfiguracaoProdutoDTO) o;
		if (configuracaoProdutoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), configuracaoProdutoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ConfiguracaoProdutoDTO{" + "id=" + getId() + ", tamanho='" + getTamanho() + "'" + ", preco="
				+ getPreco() + "}";
	}

	public Set<MaterialTamanhoDTO> getListaMaterialTamanhos()
	{
		return this.listaMaterialTamanhos;
	}

	public void setListaMaterialTamanhos(Set<MaterialTamanhoDTO> listaMaterialTamanhos)
	{
		this.listaMaterialTamanhos = listaMaterialTamanhos;
	}
}
