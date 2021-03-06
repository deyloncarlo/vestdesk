package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.UnidadeMedida;

/**
 * A DTO for the Material entity.
 */
public class MaterialDTO implements Serializable
{

	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private BigDecimal preco;

	private Float quantidadeEstoque;

	private Float quantidadeMinima;

	@NotNull
	private String codigo;

	@NotNull
	private UnidadeMedida unidadeMedida;

	private CorDTO cor;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public BigDecimal getPreco()
	{
		return this.preco;
	}

	public void setPreco(BigDecimal preco)
	{
		this.preco = preco;
	}

	public Float getQuantidadeEstoque()
	{
		return this.quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Float quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Float getQuantidadeMinima()
	{
		return this.quantidadeMinima;
	}

	public void setQuantidadeMinima(Float quantidadeMinima)
	{
		this.quantidadeMinima = quantidadeMinima;
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

		MaterialDTO materialDTO = (MaterialDTO) o;
		if (materialDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), materialDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "MaterialDTO{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", preco=" + getPreco()
				+ ", quantidadeEstoque=" + getQuantidadeEstoque() + ", quantidadeMinima=" + getQuantidadeMinima() + "}";
	}

	public String getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public UnidadeMedida getUnidadeMedida()
	{
		return this.unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida)
	{
		this.unidadeMedida = unidadeMedida;
	}

	public CorDTO getCor()
	{
		return this.cor;
	}

	public void setCor(CorDTO cor)
	{
		this.cor = cor;
	}
}
