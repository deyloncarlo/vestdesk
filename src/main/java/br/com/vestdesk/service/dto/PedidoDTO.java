package br.com.vestdesk.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import br.com.vestdesk.domain.User;
import br.com.vestdesk.domain.enumeration.StatusPedido;
import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoPedido;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoDTO implements Serializable
{

	private Long id;

	private String nomeResponsavel;

	private TipoPedido tipoPedido;

	private TipoEstampa tipoEstampaFrente;

	private TipoEstampa tipoEstampaCosta;

	private TipoEstampa tipoEstampaMangaDireita;

	private TipoEstampa tipoEstampaMangaEsquerda;

	private LocalDate dataCriacao;

	private User vendedor;

	private ClienteDTO cliente;

	private Set<PedidoItemDTO> listaPedidoItem = new HashSet<>();

	private LocalDate dataPrevisao;

	private LocalDate dataConclusao;

	private LocalDate dataFim;

	private StatusPedido statusPedido;

	private List<ConfiguracaoLayoutDTO> listaConfiguracaoLayout = new ArrayList<>();

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

	public TipoEstampa getTipoEstampaFrente()
	{
		return this.tipoEstampaFrente;
	}

	public void setTipoEstampaFrente(TipoEstampa tipoEstampaFrente)
	{
		this.tipoEstampaFrente = tipoEstampaFrente;
	}

	public TipoEstampa getTipoEstampaCosta()
	{
		return this.tipoEstampaCosta;
	}

	public void setTipoEstampaCosta(TipoEstampa tipoEstampaCosta)
	{
		this.tipoEstampaCosta = tipoEstampaCosta;
	}

	public TipoEstampa getTipoEstampaMangaDireita()
	{
		return this.tipoEstampaMangaDireita;
	}

	public void setTipoEstampaMangaDireita(TipoEstampa tipoEstampaMangaDireita)
	{
		this.tipoEstampaMangaDireita = tipoEstampaMangaDireita;
	}

	public TipoEstampa getTipoEstampaMangaEsquerda()
	{
		return this.tipoEstampaMangaEsquerda;
	}

	public void setTipoEstampaMangaEsquerda(TipoEstampa tipoEstampaMangaEsquerda)
	{
		this.tipoEstampaMangaEsquerda = tipoEstampaMangaEsquerda;
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

		PedidoDTO pedidoDTO = (PedidoDTO) o;
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
				+ getTipoEstampaFrente() + "'" + ", tipoEstampaCosta='" + getTipoEstampaCosta() + "'"
				+ ", tipoEstampaMangaDireita='" + getTipoEstampaMangaDireita() + "'" + ", tipoEstampaMangaEsquerda='"
				+ getTipoEstampaMangaEsquerda() + "'" + ", dataCriacao='" + getDataCriacao() + "'" + "}";
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

	public Set<PedidoItemDTO> getListaPedidoItem()
	{
		return this.listaPedidoItem;
	}

	public void setListaPedidoItem(Set<PedidoItemDTO> listaPedidoItem)
	{
		this.listaPedidoItem = listaPedidoItem;
	}

	public StatusPedido getStatusPedido()
	{
		return this.statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido)
	{
		this.statusPedido = statusPedido;
	}

	public List<ConfiguracaoLayoutDTO> getListaConfiguracaoLayout()
	{
		return this.listaConfiguracaoLayout;
	}

	public void setListaConfiguracaoLayout(List<ConfiguracaoLayoutDTO> listaConfiguracaoLayout)
	{
		this.listaConfiguracaoLayout = listaConfiguracaoLayout;
	}
}
