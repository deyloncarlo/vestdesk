package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A EtapaProducao.
 */
@Entity
@Table(name = "etapa_producao")
public class EtapaProducao implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	@Column(name = "prazo_execucao", nullable = false)
	private Integer prazoExecucao;

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

	public EtapaProducao nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Integer getPrazoExecucao()
	{
		return this.prazoExecucao;
	}

	public EtapaProducao prazoExecucao(Integer prazoExecucao)
	{
		this.prazoExecucao = prazoExecucao;
		return this;
	}

	public void setPrazoExecucao(Integer prazoExecucao)
	{
		this.prazoExecucao = prazoExecucao;
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
		EtapaProducao etapaProducao = (EtapaProducao) o;
		if (etapaProducao.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), etapaProducao.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "EtapaProducao{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", prazoExecucao="
				+ getPrazoExecucao() + "}";
	}
}
