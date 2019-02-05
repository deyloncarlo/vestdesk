package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.vestdesk.domain.User;
import br.com.vestdesk.domain.enumeration.StatusPedido;
import br.com.vestdesk.domain.enumeration.TipoPedido;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoGridDTO implements Serializable
{

	private Long id;

	private String nomeResponsavel;

	private TipoPedido tipoPedido;

	private LocalDate dataCriacao;

	private User vendedor;

	private ClienteDTO cliente;

	private LocalDate dataPrevisao;

	private LocalDate dataConclusao;

	private LocalDate dataFim;

	private StatusPedido statusPedido;

	private String nomeCliente;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public TipoPedido getTipoPedido()
	{
		return this.tipoPedido;
	}

	public void setTipoPedido(TipoPedido tipoPedido)
	{
		this.tipoPedido = tipoPedido;
	}

	public LocalDate getDataCriacao()
	{
		return this.dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao)
	{
		this.dataCriacao = dataCriacao;
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

		PedidoGridDTO pedidoDTO = (PedidoGridDTO) o;
		if (pedidoDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), pedidoDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "PedidoDTO{" + "id=" + getId() + ", tipoPedido='" + getTipoPedido() + "'" + ", tipoEstampaFrente='"
				+ ", dataCriacao='" + getDataCriacao() + "'" + "}";
	}

	public String getNomeResponsavel()
	{
		return this.nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel)
	{
		this.nomeResponsavel = nomeResponsavel;
	}

	public User getVendedor()
	{
		return this.vendedor;
	}

	public void setVendedor(User vendedor)
	{
		this.vendedor = vendedor;
	}

	public LocalDate getDataPrevisao()
	{
		return this.dataPrevisao;
	}

	public void setDataPrevisao(LocalDate dataPrevisao)
	{
		this.dataPrevisao = dataPrevisao;
	}

	public LocalDate getDataConclusao()
	{
		return this.dataConclusao;
	}

	public void setDataConclusao(LocalDate dataConclusao)
	{
		this.dataConclusao = dataConclusao;
	}

	public LocalDate getDataFim()
	{
		return this.dataFim;
	}

	public void setDataFim(LocalDate dataFim)
	{
		this.dataFim = dataFim;
	}

	public ClienteDTO getCliente()
	{
		return this.cliente;
	}

	public void setCliente(ClienteDTO cliente)
	{
		this.cliente = cliente;
	}

	public StatusPedido getStatusPedido()
	{
		return this.statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido)
	{
		this.statusPedido = statusPedido;
	}

	public String getNomeCliente()
	{
		return this.nomeCliente;
	}

	public void setNomeCliente(String nomeCliente)
	{
		this.nomeCliente = nomeCliente;
	}
}
