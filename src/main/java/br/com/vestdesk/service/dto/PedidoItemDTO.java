package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.vestdesk.domain.enumeration.FormaPagamento;
import br.com.vestdesk.domain.enumeration.StatusPedidoItem;

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

	private String observacao;

	private String clienteCamisa;

	private BigDecimal primeiroPagamento;

	private BigDecimal valor;

	private FormaPagamento formaPrimeiroPagamento;

	private StatusPedidoItem status;

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

	public String getObservacao()
	{
		return this.observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getClienteCamisa()
	{
		return this.clienteCamisa;
	}

	public void setClienteCamisa(String clienteCamisa)
	{
		this.clienteCamisa = clienteCamisa;
	}

	public BigDecimal getPrimeiroPagamento()
	{
		return this.primeiroPagamento;
	}

	public void setPrimeiroPagamento(BigDecimal primeiroPagamento)
	{
		this.primeiroPagamento = primeiroPagamento;
	}

	public BigDecimal getValor()
	{
		return this.valor;
	}

	public void setValor(BigDecimal valor)
	{
		this.valor = valor;
	}

	public FormaPagamento getFormaPrimeiroPagamento()
	{
		return this.formaPrimeiroPagamento;
	}

	public void setFormaPrimeiroPagamento(FormaPagamento formaPrimeiroPagamento)
	{
		this.formaPrimeiroPagamento = formaPrimeiroPagamento;
	}

	public StatusPedidoItem getStatus()
	{
		return this.status;
	}

	public void setStatus(StatusPedidoItem status)
	{
		this.status = status;
	}

}
