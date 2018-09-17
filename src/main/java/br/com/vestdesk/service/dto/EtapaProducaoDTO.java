package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the EtapaProducao entity.
 */
public class EtapaProducaoDTO implements Serializable
{

	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private Integer prazoExecucao;

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

	public Integer getPrazoExecucao()
	{
		return this.prazoExecucao;
	}

	public void setPrazoExecucao(Integer prazoExecucao)
	{
		this.prazoExecucao = prazoExecucao;
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

		EtapaProducaoDTO etapaProducaoDTO = (EtapaProducaoDTO) o;
		if (etapaProducaoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), etapaProducaoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "EtapaProducaoDTO{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", prazoExecucao="
				+ getPrazoExecucao() + "}";
	}
}
