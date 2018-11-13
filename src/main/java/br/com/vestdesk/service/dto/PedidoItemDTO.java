package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PedidoItem entity.
 */
public class PedidoItemDTO implements Serializable
{

	private Long id;

	private String nomeRoupa;

	private String telefone;

	private ProdutoDTO produto;

	private PedidoMinDTO pedido;

	private Integer quantidade;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

		PedidoItemDTO pedidoItemDTO = (PedidoItemDTO) o;
		if (pedidoItemDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), pedidoItemDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "PedidoItemDTO{" + "id=" + getId() + "}";
	}

	public String getTelefone()
	{
		return this.telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public ProdutoDTO getProduto()
	{
		return this.produto;
	}

	public void setProduto(ProdutoDTO produto)
	{
		this.produto = produto;
	}

	public PedidoMinDTO getPedido()
	{
		return this.pedido;
	}

	public void setPedido(PedidoMinDTO pedido)
	{
		this.pedido = pedido;
	}

	public String getNomeRoupa()
	{
		return this.nomeRoupa;
	}

	public void setNomeRoupa(String nomeRoupa)
	{
		this.nomeRoupa = nomeRoupa;
	}

	public Integer getQuantidade()
	{
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}

}
