package br.com.vestdesk.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.vestdesk.domain.enumeration.UnidadeMedida;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
public class Material implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	@Column(name = "preco", precision = 10, scale = 2, nullable = false)
	private BigDecimal preco;

	@Column(name = "quantidade_estoque")
	private Float quantidadeEstoque;

	@Column(name = "quantidade_minima")
	private Float quantidadeMinima;

	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<MaterialTamanho> listaMaterialTamanhos = new HashSet<>();

	@NotNull
	@Column(name = "unidadeMedida")
	private UnidadeMedida unidadeMedida;

	@ManyToOne
	private Cor cor;

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

	public String getNome()
	{
		return this.nome;
	}

	public Material nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public BigDecimal getPreco()
	{
		return this.preco;
	}

	public Material preco(BigDecimal preco)
	{
		this.preco = preco;
		return this;
	}

	public void setPreco(BigDecimal preco)
	{
		this.preco = preco;
	}

	public Float getQuantidadeEstoque()
	{
		return this.quantidadeEstoque;
	}

	public Material quantidadeEstoque(Float quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
		return this;
	}

	public void setQuantidadeEstoque(Float quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Float getQuantidadeMinima()
	{
		return this.quantidadeMinima;
	}

	public Material quantidadeMinima(Float quantidadeMinima)
	{
		this.quantidadeMinima = quantidadeMinima;
		return this;
	}

	public void setQuantidadeMinima(Float quantidadeMinima)
	{
		this.quantidadeMinima = quantidadeMinima;
	}

	public Set<MaterialTamanho> getListaMaterialTamanhos()
	{
		return this.listaMaterialTamanhos;
	}

	public Material listaMaterialTamanhos(Set<MaterialTamanho> materialTamanhos)
	{
		this.listaMaterialTamanhos = materialTamanhos;
		return this;
	}

	public Material addListaMaterialTamanho(MaterialTamanho materialTamanho)
	{
		this.listaMaterialTamanhos.add(materialTamanho);
		materialTamanho.setMaterial(this);
		return this;
	}

	public Material removeListaMaterialTamanho(MaterialTamanho materialTamanho)
	{
		this.listaMaterialTamanhos.remove(materialTamanho);
		materialTamanho.setMaterial(null);
		return this;
	}

	public void setListaMaterialTamanhos(Set<MaterialTamanho> materialTamanhos)
	{
		this.listaMaterialTamanhos = materialTamanhos;
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
		Material material = (Material) o;
		if (material.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), material.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Material{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", preco=" + getPreco()
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

	public Cor getCor()
	{
		return this.cor;
	}

	public void setCor(Cor cor)
	{
		this.cor = cor;
	}
}
