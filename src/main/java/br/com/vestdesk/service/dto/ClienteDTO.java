package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Cliente entity.
 */
public class ClienteDTO implements Serializable
{

	private Long id;

	@NotNull
	private String nome;

	private String endereco;

	private String cpf;

	private String telefone;

	private String observacao;

	private String email;

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

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getEndereco()
	{
		return this.endereco;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	public String getTelefone()
	{
		return this.telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public String getObservacao()
	{
		return this.observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
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

		ClienteDTO clienteDTO = (ClienteDTO) o;
		if (clienteDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), clienteDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ClienteDTO{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", endereco='" + getEndereco() + "'"
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
