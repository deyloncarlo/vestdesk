package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.domain.enumeration.Tamanho;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "quantidadeInicial")
	private Integer quantidadeInicial;

	@Column(name = "totalEntrada")
	private Integer totalEntrada;

	@Column(name = "quantidadeMinima")
	private Integer quantidadeMinima;

	@Column(name = "totalSaida")
	private Integer totalSaida;

	@Column(name = "quantidadeAtualizada")
	private Integer quantidadeAtualizada;

	@NotNull
	@Column(name = "tamanho")
	private Tamanho tamanho;

	@NotNull
	@Column(name = "modelo")
	private Modelo modelo;

	@Column(name = "quantidade_estoque", nullable = false)
	private Integer quantidadeEstoque;

	@ManyToMany
	private Set<Cor> listaMaterial = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "produto_lista_cor", joinColumns = @JoinColumn(name = "produtos_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lista_cors_id", referencedColumnName = "id"))
	private Set<Cor> listaCor = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
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

	public Produto quantidadeEstoque(Integer quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
		return this;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getDescricao()
	{
		return this.descricao;
	}

	public Produto descricao(String descricao)
	{
		this.descricao = descricao;
		return this;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters
	// and setters here, do not remove

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
		Produto produto = (Produto) o;
		if (produto.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), produto.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Produto{" + "id=" + getId() + ", quantidadeEstoque=" + getQuantidadeEstoque() + ", descricao='"
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

	public Set<Cor> getListaCor()
	{
		return this.listaCor;
	}

	public void setListaCor(Set<Cor> listaCor)
	{
		this.listaCor = listaCor;
	}

	public Set<Cor> getListaMaterial()
	{
		return this.listaMaterial;
	}

	public void setListaMaterial(Set<Cor> litaMaterial)
	{
		this.listaMaterial = litaMaterial;
	}

}
