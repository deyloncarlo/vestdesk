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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "observacao")
	private String observacao;

	@Column(name = "email")
	private String email;

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

	public Cliente nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getEndereco()
	{
		return this.endereco;
	}

	public Cliente endereco(String endereco)
	{
		this.endereco = endereco;
		return this;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	public String getTelefone()
	{
		return this.telefone;
	}

	public Cliente telefone(String telefone)
	{
		this.telefone = telefone;
		return this;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public String getObservacao()
	{
		return this.observacao;
	}

	public Cliente observacao(String observacao)
	{
		this.observacao = observacao;
		return this;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getEmail()
	{
		return this.email;
	}

	public Cliente email(String email)
	{
		this.email = email;
		return this;
	}

	public void setEmail(String email)
	{
		this.email = email;
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
		Cliente cliente = (Cliente) o;
		if (cliente.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), cliente.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Cliente{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", endereco='" + getEndereco() + "'"
				+ ", telefone='" + getTelefone() + "'" + ", observacao='" + getObservacao() + "'" + ", email='"
				+ getEmail() + "'" + "}";
	}

	public String getCpf()
	{
		return this.cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}
}
