package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class NotificacaoDTO implements Serializable
{
	private Long id;
	private String textoNotificacao;
	private Boolean visualizado;
	private LocalDate dataCriacao;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTextoNotificacao()
	{
		return this.textoNotificacao;
	}

	public void setTextoNotificacao(String textoNotificacao)
	{
		this.textoNotificacao = textoNotificacao;
	}

	public Boolean getVisualizado()
	{
		return this.visualizado;
	}

	public void setVisualizado(Boolean visualizado)
	{
		this.visualizado = visualizado;
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

		EntityDTO clienteDTO = (EntityDTO) o;
		if (clienteDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), clienteDTO.getId());
	}

	public LocalDate getDataCriacao()
	{
		return this.dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao)
	{
		this.dataCriacao = dataCriacao;
	}
}
