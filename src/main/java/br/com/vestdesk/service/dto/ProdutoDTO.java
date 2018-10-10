package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.domain.enumeration.Tamanho;

/**
 * A DTO for the Produto entity.
 */
public class ProdutoDTO implements Serializable
{

	private Long id;

	private String codigo;

	private String descricao;

	private Integer quantidadeInicial;

	private Integer totalEntrada;

	private Integer quantidadeMinima;

	private Integer totalSaida;

	private Integer quantidadeAtualizada;

	private Tamanho tamanho;

	private Modelo modelo;

	private Integer quantidadeEstoque;

	private Set<Cor> listaMaterial = new HashSet<>();

	private Set<Cor> listaCor = new HashSet<>();

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getQuantidadeEstoque()
	{
		return this.quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getDescricao()
	{
		return this.descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
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

		ProdutoDTO produtoDTO = (ProdutoDTO) o;
		if (produtoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), produtoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ProdutoDTO{" + "id=" + getId() + ", quantidadeEstoque=" + getQuantidadeEstoque() + ", descricao='"
				+ getDescricao() + "'" + "}";
	}

	public String getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public Integer getQuantidadeInicial()
	{
		return this.quantidadeInicial;
	}

	public void setQuantidadeInicial(Integer quantidadeInicial)
	{
		this.quantidadeInicial = quantidadeInicial;
	}

	public Integer getTotalEntrada()
	{
		return this.totalEntrada;
	}

	public void setTotalEntrada(Integer totalEntrada)
	{
		this.totalEntrada = totalEntrada;
	}

	public Integer getQuantidadeMinima()
	{
		return this.quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima)
	{
		this.quantidadeMinima = quantidadeMinima;
	}

	public Integer getTotalSaida()
	{
		return this.totalSaida;
	}

	public void setTotalSaida(Integer totalSaida)
	{
		this.totalSaida = totalSaida;
	}

	public Integer getQuantidadeAtualizada()
	{
		return this.quantidadeAtualizada;
	}

	public void setQuantidadeAtualizada(Integer quantidadeAtualizada)
	{
		this.quantidadeAtualizada = quantidadeAtualizada;
	}

	public Tamanho getTamanho()
	{
		return this.tamanho;
	}

	public void setTamanho(Tamanho tamanho)
	{
		this.tamanho = tamanho;
	}

	public Modelo getModelo()
	{
		return this.modelo;
	}

	public void setModelo(Modelo modelo)
	{
		this.modelo = modelo;
	}

	public Set<Cor> getListaMaterial()
	{
		return this.listaMaterial;
	}

	public void setListaMaterial(Set<Cor> listaMaterial)
	{
		this.listaMaterial = listaMaterial;
	}

	public Set<Cor> getListaCor()
	{
		return this.listaCor;
	}

	public void setListaCor(Set<Cor> listaCor)
	{
		this.listaCor = listaCor;
	}

}
