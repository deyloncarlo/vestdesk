package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.vestdesk.domain.enumeration.Modelo;

/**
 * A ModeloVestuario.
 */
@Entity
@Table(name = "modelo_vestuario")
public class ModeloVestuario implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "modelo", nullable = false)
	private Modelo modelo;

	@OneToMany(mappedBy = "modeloVestuario")
	@JsonIgnore
	private Set<ConfiguracaoProduto> listaConfiguracaoProdutos = new HashSet<>();

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

	public Modelo getModelo()
	{
		return this.modelo;
	}

	public ModeloVestuario modelo(Modelo modelo)
	{
		this.modelo = modelo;
		return this;
	}

	public void setModelo(Modelo modelo)
	{
		this.modelo = modelo;
	}

	public Set<ConfiguracaoProduto> getListaConfiguracaoProdutos()
	{
		return this.listaConfiguracaoProdutos;
	}

	public ModeloVestuario listaConfiguracaoProdutos(Set<ConfiguracaoProduto> configuracaoProdutos)
	{
		this.listaConfiguracaoProdutos = configuracaoProdutos;
		return this;
	}

	public ModeloVestuario addListaConfiguracaoProduto(ConfiguracaoProduto configuracaoProduto)
	{
		this.listaConfiguracaoProdutos.add(configuracaoProduto);
		configuracaoProduto.setModeloVestuario(this);
		return this;
	}

	public ModeloVestuario removeListaConfiguracaoProduto(ConfiguracaoProduto configuracaoProduto)
	{
		this.listaConfiguracaoProdutos.remove(configuracaoProduto);
		configuracaoProduto.setModeloVestuario(null);
		return this;
	}

	public void setListaConfiguracaoProdutos(Set<ConfiguracaoProduto> configuracaoProdutos)
	{
		this.listaConfiguracaoProdutos = configuracaoProdutos;
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
		ModeloVestuario modeloVestuario = (ModeloVestuario) o;
		if (modeloVestuario.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), modeloVestuario.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ModeloVestuario{" + "id=" + getId() + ", modelo='" + getModelo() + "'" + "}";
	}
}
