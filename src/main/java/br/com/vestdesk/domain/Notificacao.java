package br.com.vestdesk.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private String textoNotificacao;

	@NotNull
	@Column
	private Boolean visualizado;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User usuario;

	@NotNull
	@Column
	private LocalDateTime dataCriacao;

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

	public User getUsuario()
	{
		return this.usuario;
	}

	public void setUsuario(User usuario)
	{
		this.usuario = usuario;
	}

	public LocalDateTime getDataCriacao()
	{
		return this.dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao)
	{
		this.dataCriacao = dataCriacao;
	}

}