package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A MaterialTamanho.
 */
@Entity
@Table(name = "material_tamanho")
public class MaterialTamanho implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "quantidade_material", nullable = false)
	private Float quantidadeMaterial;

	@ManyToOne(cascade = CascadeType.ALL)
	private Produto produto;

	@ManyToOne
	private Material material;

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

	public Float getQuantidadeMaterial()
	{
		return this.quantidadeMaterial;
	}

	public MaterialTamanho quantidadeMaterial(Float quantidadeMaterial)
	{
		this.quantidadeMaterial = quantidadeMaterial;
		return this;
	}

	public void setQuantidadeMaterial(Float quantidadeMaterial)
	{
		this.quantidadeMaterial = quantidadeMaterial;
	}

	public Material getMaterial()
	{
		return this.material;
	}

	public MaterialTamanho material(Material material)
	{
		this.material = material;
		return this;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
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
		MaterialTamanho materialTamanho = (MaterialTamanho) o;
		if (materialTamanho.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), materialTamanho.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "MaterialTamanho{" + "id=" + getId() + ", quantidadeMaterial=" + getQuantidadeMaterial() + "}";
	}

	public Produto getProduto()
	{
		return this.produto;
	}

	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
}
