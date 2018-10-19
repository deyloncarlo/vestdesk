package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private String telefone;

	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Pedido pedido;

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
}
