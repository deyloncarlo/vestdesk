package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Produto entity.
 */
public class ProdutoDTO implements Serializable
{

	private Long id;

	@NotNull
	private Integer quantidadeEstoque;

	@NotNull
	private String descricao;

	private Long configuracaoProdutoId;

	private ModeloVestuarioDTO modeloVestuario;

	@NotNull
	private ConfiguracaoProdutoDTO configuracaoProduto;

	private Set<CorDTO> listaCors = new HashSet<>();

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getQuantidadeEstoque()
	{
		return this.quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque)
	{
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getDescricao()
	{
		return this.descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Long getConfiguracaoProdutoId()
	{
		return this.configuracaoProdutoId;
	}

	public void setConfiguracaoProdutoId(Long configuracaoProdutoId)
	{
		this.configuracaoProdutoId = configuracaoProdutoId;
	}

	public Set<CorDTO> getListaCors()
	{
		return this.listaCors;
	}

	public void setListaCors(Set<CorDTO> cors)
	{
		this.listaCors = cors;
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

		ProdutoDTO produtoDTO = (ProdutoDTO) o;
		if (produtoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), produtoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "ProdutoDTO{" + "id=" + getId() + ", quantidadeEstoque=" + getQuantidadeEstoque() + ", descricao='"
				+ getDescricao() + "'" + "}";
	}

	public ConfiguracaoProdutoDTO getConfiguracaoProduto()
	{
		return this.configuracaoProduto;
	}

	public void setConfiguracaoProduto(ConfiguracaoProdutoDTO configuracaoProduto)
	{
		this.configuracaoProduto = configuracaoProduto;
	}

	public ModeloVestuarioDTO getModeloVestuario()
	{
		return this.modeloVestuario;
	}

	public void setModeloVestuario(ModeloVestuarioDTO modeloVestuario)
	{
		this.modeloVestuario = modeloVestuario;
	}
}
