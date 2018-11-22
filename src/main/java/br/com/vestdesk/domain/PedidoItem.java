package br.com.vestdesk.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vestdesk.domain.enumeration.FormaPagamento;
import br.com.vestdesk.domain.enumeration.StatusPedidoItem;

/**
 * A PedidoItem.
 */
@Entity
@Table(name = "pedido_item")
public class PedidoItem implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome_roupa")
	private String nomeRoupa;

	@Column
	private String observacao;

	@Column
	private String clienteCamisa;

	@Column
	private String telefone;

	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Pedido pedido;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column
	private BigDecimal primeiroPagamento;

	@Column
	private BigDecimal valor;

	@Column
	private FormaPagamento formaPrimeiroPagamento;

	@Enumerated(EnumType.STRING)
	@Column
	private StatusPedidoItem status;

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
		PedidoItem pedidoItem = (PedidoItem) o;
		if (pedidoItem.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), pedidoItem.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "PedidoItem{" + "id=" + getId() + "'" + "}";
	}

	public Produto getProduto()
	{
		return this.produto;
	}

	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}

	public Pedido getPedido()
	{
		return this.pedido;
	}

	public void setPedido(Pedido pedido)
	{
		this.pedido = pedido;
	}

	public String getTelefone()
	{
		return this.telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
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
