package br.com.vestdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.vestdesk.domain.enumeration.TipoEstampa;

/**
 * A ConfiguracaoLayout.
 */
@Entity
@Table(name = "configuracao_layout")
public class ConfiguracaoLayout implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_cricao")
	private LocalDate dataCricao;
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_frente")
	private TipoEstampa tipoEstampaFrente;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_costa")
	private TipoEstampa tipoEstampaCosta;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_manga_direita")
	private TipoEstampa tipoEstampaMangaDireita;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_manga_esquerda")
	private TipoEstampa tipoEstampaMangaEsquerda;

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

	public LocalDate getDataCricao()
	{
		return this.dataCricao;
	}

	public ConfiguracaoLayout dataCricao(LocalDate dataCricao)
	{
		this.dataCricao = dataCricao;
		return this;
	}

	public void setDataCricao(LocalDate dataCricao)
	{
		this.dataCricao = dataCricao;
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
		ConfiguracaoLayout configuracaoLayout = (ConfiguracaoLayout) o;
		if (configuracaoLayout.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), configuracaoLayout.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ConfiguracaoLayout{" + "id=" + getId() + ", dataCricao='" + getDataCricao() + "'" + "}";
	}
}
