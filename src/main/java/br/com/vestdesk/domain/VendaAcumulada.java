package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A VendaAcumulada.
 */
@Entity
@Table(name = "venda_acumulada")
public class VendaAcumulada implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantidade_acumulada")
	private Integer quantidadeAcumulada;

	@OneToOne
	private Produto produto;

	// @ManyToMany
	// private Set<Pedido> listaPedido = new HashSet<Pedido>();

	@OneToMany
	private Set<PedidoItem> listaPedidoItemProduzido = new HashSet<PedidoItem>();

	@OneToMany
	private Set<PedidoItem> listaPedidoItemAcumulado = new HashSet<PedidoItem>();

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

	public Integer getQuantidadeAcumulada()
	{
		return this.quantidadeAcumulada;
	}

	public VendaAcumulada quantidadeAcumulada(Integer quantidadeAcumulada)
	{
		this.quantidadeAcumulada = quantidadeAcumulada;
		return this;
	}

	public void setQuantidadeAcumulada(Integer quantidadeAcumulada)
	{
		this.quantidadeAcumulada = quantidadeAcumulada;
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
		VendaAcumulada vendaAcumulada = (VendaAcumulada) o;
		if (vendaAcumulada.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), vendaAcumulada.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "VendaAcumulada{" + "id=" + getId() + ", quantidadeAcumulada=" + getQuantidadeAcumulada() + "}";
	}

	public Produto getProduto()
	{
		return this.produto;
	}

	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}

	public Set<PedidoItem> getListaPedidoItemProduzido()
	{
		return this.listaPedidoItemProduzido;
	}

	public void setListaPedidoItemProduzido(Set<PedidoItem> listaPedidoItemProduzido)
	{
		this.listaPedidoItemProduzido = listaPedidoItemProduzido;
	}

	public Set<PedidoItem> getListaPedidoItemAcumulado()
	{
		return this.listaPedidoItemAcumulado;
	}

	public void setListaPedidoItemAcumulado(Set<PedidoItem> listaPedidoItemAcumulado)
	{
		this.listaPedidoItemAcumulado = listaPedidoItemAcumulado;
	}
}
