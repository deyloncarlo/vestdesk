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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Cor.
 */
@Entity
@Table(name = "cor")
public class Cor implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@Column(name = "codigo", length = 15)
	private String codigo;

	@OneToMany(mappedBy = "cor")
	private Set<Produto> listaProduto = new HashSet();

	@OneToMany(mappedBy = "cor")
	private Set<Material> listaMaterial = new HashSet();

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

	public String getCodigo()
	{
		return this.codigo;
	}

	public Cor codigo(String codigo)
	{
		this.codigo = codigo;
		return this;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
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
		Cor cor = (Cor) o;
		if (cor.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), cor.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Cor{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + "}";
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Set<Produto> getListaProduto()
	{
		return this.listaProduto;
	}

	public void setListaProduto(Set<Produto> listaProduto)
	{
		this.listaProduto = listaProduto;
	}

	public Set<Material> getListaMaterial()
	{
		return this.listaMaterial;
	}

	public void setListaMaterial(Set<Material> listaMaterial)
	{
		this.listaMaterial = listaMaterial;
	}
}
