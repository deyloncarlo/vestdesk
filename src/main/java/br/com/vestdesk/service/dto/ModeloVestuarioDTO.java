package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.Modelo;

/**
 * A DTO for the ModeloVestuario entity.
 */
public class ModeloVestuarioDTO implements Serializable
{

	private Long id;

	@NotNull
	private Modelo modelo;

	@NotNull
	private String nome;

	private Set<ConfiguracaoProdutoDTO> listaConfiguracaoProdutos;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Modelo getModelo()
	{
		return this.modelo;
	}

	public void setModelo(Modelo modelo)
	{
		this.modelo = modelo;
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

		ModeloVestuarioDTO modeloVestuarioDTO = (ModeloVestuarioDTO) o;
		if (modeloVestuarioDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), modeloVestuarioDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ModeloVestuarioDTO{" + "id=" + getId() + ", modelo='" + getModelo() + "'" + "}";
	}

	public Set<ConfiguracaoProdutoDTO> getListaConfiguracaoProdutos()
	{
		return this.listaConfiguracaoProdutos;
	}

	public void setListaConfiguracaoProdutos(Set<ConfiguracaoProdutoDTO> listaConfiguracaoProdutos)
	{
		this.listaConfiguracaoProdutos = listaConfiguracaoProdutos;
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
}
