package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.Modelo;

/**
 * A DTO for the Layout entity.
 */
public class LayoutDTO implements Serializable
{

	private Long id;

	@NotNull
	private String nome;

	// @NotNull
	@Lob
	private byte[] imagem;

	@Lob
	private byte[] optimizedImage;

	private String imagemContentType;

	private Modelo modelo;

	public LayoutDTO()
	{

	}

	public LayoutDTO(Long id, String nome, byte[] optimizedImage, String imagemContentType, Modelo modelo)
	{

		this.id = id;
		this.nome = nome;
		this.optimizedImage = optimizedImage;
		this.imagemContentType = imagemContentType;
		this.modelo = modelo;
	}

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

	public byte[] getImagem()
	{
		return this.imagem;
	}

	public void setImagem(byte[] imagem)
	{
		this.imagem = imagem;
	}

	public String getImagemContentType()
	{
		return this.imagemContentType;
	}

	public void setImagemContentType(String imagemContentType)
	{
		this.imagemContentType = imagemContentType;
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

		LayoutDTO layoutDTO = (LayoutDTO) o;
		if (layoutDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), layoutDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "LayoutDTO{" + "id=" + getId() + ", nome='" + getNome() + "'" + "}";
	}

	public Modelo getModelo()
	{
		return this.modelo;
	}

	public void setModelo(Modelo modelo)
	{
		this.modelo = modelo;
	}

	public byte[] getOptimizedImage()
	{
		return this.optimizedImage;
	}

	public void setOptimizedImage(byte[] optimizedImage)
	{
		this.optimizedImage = optimizedImage;
	}
}
