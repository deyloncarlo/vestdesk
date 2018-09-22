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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
	@Column(name = "quantidade_estoque", nullable = false)
	private Integer quantidadeEstoque;

	@NotNull
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@OneToOne
	@JoinColumn(unique = true)
	private ConfiguracaoProduto configuracaoProduto;

	@ManyToMany
	@JoinTable(name = "produto_lista_cor", joinColumns = @JoinColumn(name = "produtos_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lista_cors_id", referencedColumnName = "id"))
	private Set<Cor> listaCors = new HashSet<>();

	@Transient
	private ModeloVestuario modeloVestuario;

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

	public ConfiguracaoProduto getConfiguracaoProduto()
	{
		return this.configuracaoProduto;
	}

	public Produto configuracaoProduto(ConfiguracaoProduto configuracaoProduto)
	{
		this.configuracaoProduto = configuracaoProduto;
		return this;
	}

	public void setConfiguracaoProduto(ConfiguracaoProduto configuracaoProduto)
	{
		this.configuracaoProduto = configuracaoProduto;
	}

	public Set<Cor> getListaCors()
	{
		return this.listaCors;
	}

	public Produto listaCors(Set<Cor> cors)
	{
		this.listaCors = cors;
		return this;
	}

	public Produto addListaCor(Cor cor)
	{
		this.listaCors.add(cor);
		cor.getListaProdutos().add(this);
		return this;
	}

	public Produto removeListaCor(Cor cor)
	{
		this.listaCors.remove(cor);
		cor.getListaProdutos().remove(this);
		return this;
	}

	public void setListaCors(Set<Cor> cors)
	{
		this.listaCors = cors;
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

	public ModeloVestuario getModeloVestuario()
	{
		return getConfiguracaoProduto().getModeloVestuario();
	}

	public void setModeloVestuario(ModeloVestuario modeloVestuario)
	{
		this.modeloVestuario = modeloVestuario;
	}
}
