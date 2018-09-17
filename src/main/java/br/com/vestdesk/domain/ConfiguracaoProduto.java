package br.com.vestdesk.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.vestdesk.domain.enumeration.Tamanho;

/**
 * A ConfiguracaoProduto.
 */
@Entity
@Table(name = "configuracao_produto")
public class ConfiguracaoProduto implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tamanho", nullable = false)
	private Tamanho tamanho;

	@NotNull
	@Column(name = "preco", precision = 10, scale = 2, nullable = false)
	private BigDecimal preco;

	@OneToMany(mappedBy = "configuracaoProduto", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<MaterialTamanho> listaMaterialTamanhos = new HashSet<>();

	@ManyToOne
	private ModeloVestuario modeloVestuario;

	@OneToMany(mappedBy = "configuracaoProduto", cascade = CascadeType.ALL)
	private Set<Produto> listaProduto = new HashSet<>();

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

	public Tamanho getTamanho()
	{
		return this.tamanho;
	}

	public ConfiguracaoProduto tamanho(Tamanho tamanho)
	{
		this.tamanho = tamanho;
		return this;
	}

	public void setTamanho(Tamanho tamanho)
	{
		this.tamanho = tamanho;
	}

	public BigDecimal getPreco()
	{
		return this.preco;
	}

	public ConfiguracaoProduto preco(BigDecimal preco)
	{
		this.preco = preco;
		return this;
	}

	public void setPreco(BigDecimal preco)
	{
		this.preco = preco;
	}

	public Set<MaterialTamanho> getListaMaterialTamanhos()
	{
		return this.listaMaterialTamanhos;
	}

	public ConfiguracaoProduto listaMaterialTamanhos(Set<MaterialTamanho> materialTamanhos)
	{
		this.listaMaterialTamanhos = materialTamanhos;
		return this;
	}

	public ConfiguracaoProduto addListaMaterialTamanho(MaterialTamanho materialTamanho)
	{
		this.listaMaterialTamanhos.add(materialTamanho);
		materialTamanho.setConfiguracaoProduto(this);
		return this;
	}

	public ConfiguracaoProduto removeListaMaterialTamanho(MaterialTamanho materialTamanho)
	{
		this.listaMaterialTamanhos.remove(materialTamanho);
		materialTamanho.setConfiguracaoProduto(null);
		return this;
	}

	public void setListaMaterialTamanhos(Set<MaterialTamanho> materialTamanhos)
	{
		this.listaMaterialTamanhos = materialTamanhos;
	}

	public ModeloVestuario getModeloVestuario()
	{
		return this.modeloVestuario;
	}

	public ConfiguracaoProduto modeloVestuario(ModeloVestuario modeloVestuario)
	{
		this.modeloVestuario = modeloVestuario;
		return this;
	}

	public void setModeloVestuario(ModeloVestuario modeloVestuario)
	{
		this.modeloVestuario = modeloVestuario;
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
		ConfiguracaoProduto configuracaoProduto = (ConfiguracaoProduto) o;
		if (configuracaoProduto.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), configuracaoProduto.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ConfiguracaoProduto{" + "id=" + getId() + ", tamanho='" + getTamanho() + "'" + ", preco=" + getPreco()
				+ "}";
	}

	public Set<Produto> getListaProduto()
	{
		return this.listaProduto;
	}

	public void setListaProduto(Set<Produto> listaProduto)
	{
		this.listaProduto = listaProduto;
	}
}
