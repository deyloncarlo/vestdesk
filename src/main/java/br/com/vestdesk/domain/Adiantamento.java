package br.com.vestdesk.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Adiantamento.
 */
@Entity
@Table(name = "adiantamento")
public class Adiantamento implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "valor", precision = 10, scale = 2, nullable = false)
	private BigDecimal valor;

	@ManyToOne
	private PedidoItem pedidoItem;

	@ManyToOne
	private FormaPagamento formaPagemento;

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

	public BigDecimal getValor()
	{
		return this.valor;
	}

	public Adiantamento valor(BigDecimal valor)
	{
		this.valor = valor;
		return this;
	}

	public void setValor(BigDecimal valor)
	{
		this.valor = valor;
	}

	public FormaPagamento getFormaPagemento()
	{
		return this.formaPagemento;
	}

	public Adiantamento formaPagemento(FormaPagamento formaPagamento)
	{
		this.formaPagemento = formaPagamento;
		return this;
	}

	public void setFormaPagemento(FormaPagamento formaPagamento)
	{
		this.formaPagemento = formaPagamento;
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
		Adiantamento adiantamento = (Adiantamento) o;
		if (adiantamento.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), adiantamento.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Adiantamento{" + "id=" + getId() + ", valor=" + getValor() + "}";
	}

	public PedidoItem getPedidoItem()
	{
		return this.pedidoItem;
	}

	public void setPedidoItem(PedidoItem pedidoItem)
	{
		this.pedidoItem = pedidoItem;
	}
}
