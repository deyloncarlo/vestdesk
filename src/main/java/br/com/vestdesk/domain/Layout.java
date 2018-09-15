package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Layout.
 */
@Entity
@Table(name = "layout")
public class Layout implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	@Lob
	@Column(name = "imagem", nullable = false)
	private byte[] imagem;

	@Column(name = "imagem_content_type", nullable = false)
	private String imagemContentType;

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

	public Layout nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public byte[] getImagem()
	{
		return this.imagem;
	}

	public Layout imagem(byte[] imagem)
	{
		this.imagem = imagem;
		return this;
	}

	public void setImagem(byte[] imagem)
	{
		this.imagem = imagem;
	}

	public String getImagemContentType()
	{
		return this.imagemContentType;
	}

	public Layout imagemContentType(String imagemContentType)
	{
		this.imagemContentType = imagemContentType;
		return this;
	}

	public void setImagemContentType(String imagemContentType)
	{
		this.imagemContentType = imagemContentType;
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
		Layout layout = (Layout) o;
		if (layout.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), layout.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Layout{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", imagem='" + getImagem() + "'"
				+ ", imagemContentType='" + getImagemContentType() + "'" + "}";
	}
}
