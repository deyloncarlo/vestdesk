package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.vestdesk.domain.enumeration.TipoEstampa;

/**
 * A DTO for the ConfiguracaoLayout entity.
 */
public class ConfiguracaoLayoutDTO implements Serializable
{
	private Long id;

	private LocalDate dataCricao;

	private TipoEstampa tipoEstampaFrente;

	private TipoEstampa tipoEstampaCosta;

	private TipoEstampa tipoEstampaMangaDireita;

	private TipoEstampa tipoEstampaMangaEsquerda;

	private LayoutDTO layout;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public LocalDate getDataCricao()
	{
		return this.dataCricao;
	}

	public void setDataCricao(LocalDate dataCricao)
	{
		this.dataCricao = dataCricao;
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

		ConfiguracaoLayoutDTO configuracaoLayoutDTO = (ConfiguracaoLayoutDTO) o;
		if (configuracaoLayoutDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), configuracaoLayoutDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ConfiguracaoLayoutDTO{" + "id=" + getId() + ", dataCricao='" + getDataCricao() + "'" + "}";
	}

	public TipoEstampa getTipoEstampaFrente()
	{
		return this.tipoEstampaFrente;
	}

	public void setTipoEstampaFrente(TipoEstampa tipoEstampaFrente)
	{
		this.tipoEstampaFrente = tipoEstampaFrente;
	}

	public TipoEstampa getTipoEstampaCosta()
	{
		return this.tipoEstampaCosta;
	}

	public void setTipoEstampaCosta(TipoEstampa tipoEstampaCosta)
	{
		this.tipoEstampaCosta = tipoEstampaCosta;
	}

	public TipoEstampa getTipoEstampaMangaDireita()
	{
		return this.tipoEstampaMangaDireita;
	}

	public void setTipoEstampaMangaDireita(TipoEstampa tipoEstampaMangaDireita)
	{
		this.tipoEstampaMangaDireita = tipoEstampaMangaDireita;
	}

	public TipoEstampa getTipoEstampaMangaEsquerda()
	{
		return this.tipoEstampaMangaEsquerda;
	}

	public void setTipoEstampaMangaEsquerda(TipoEstampa tipoEstampaMangaEsquerda)
	{
		this.tipoEstampaMangaEsquerda = tipoEstampaMangaEsquerda;
	}

	public LayoutDTO getLayout()
	{
		return this.layout;
	}

	public void setLayout(LayoutDTO layout)
	{
		this.layout = layout;
	}
}
