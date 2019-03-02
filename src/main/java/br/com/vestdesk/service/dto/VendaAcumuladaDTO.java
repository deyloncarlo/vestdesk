package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.vestdesk.domain.enumeration.StatusVendaAcumulada;

/**
 * A DTO for the VendaAcumulada entity.
 */
public class VendaAcumuladaDTO implements Serializable
{

	private Long id;

	private Integer quantidadeAcumulada;

	private ProdutoMinDTO produto;

	private List<PedidoItemMinDTO> listaPedidoItemAcumulado = new ArrayList<>();

	private StatusVendaAcumulada status;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getQuantidadeAcumulada()
	{
		return this.quantidadeAcumulada;
	}

	public void setQuantidadeAcumulada(Integer quantidadeAcumulada)
	{
		this.quantidadeAcumulada = quantidadeAcumulada;
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

		VendaAcumuladaDTO vendaAcumuladaDTO = (VendaAcumuladaDTO) o;
		if (vendaAcumuladaDTO.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), vendaAcumuladaDTO.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "VendaAcumuladaDTO{" + "id=" + getId() + ", quantidadeAcumulada=" + getQuantidadeAcumulada() + "}";
	}

	public ProdutoMinDTO getProduto()
	{
		return this.produto;
	}

	public void setProduto(ProdutoMinDTO produto)
	{
		this.produto = produto;
	}

	public List<PedidoItemMinDTO> getListaPedidoItemAcumulado()
	{
		return this.listaPedidoItemAcumulado;
	}

	public void setListaPedidoItemAcumulado(List<PedidoItemMinDTO> listaPedidoItemAcumulado)
	{
		this.listaPedidoItemAcumulado = listaPedidoItemAcumulado;
	}

	public StatusVendaAcumulada getStatus()
	{
		return this.status;
	}

	public void setStatus(StatusVendaAcumulada status)
	{
		this.status = status;
	}
}
