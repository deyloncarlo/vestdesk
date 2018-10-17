package br.com.vestdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoPedido;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	@Column(name = "nome_responsavel", nullable = false)
	private String nomeResponsavel;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pedido", nullable = false)
	private TipoPedido tipoPedido;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_frente")
	private TipoEstampa tipoEstampaFrente;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_costa")
	private TipoEstampa tipoEstampaCosta;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_manga_direita")
	private TipoEstampa tipoEstampaMangaDireita;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estampa_manga_esquerda")
	private TipoEstampa tipoEstampaMangaEsquerda;

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	@ManyToOne
	private User vendedor;

	@ManyToOne
	private Cliente cliente;

	@OneToMany
	private Set<PedidoItem> listaPedidoItem = new HashSet<>();

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

	public String getNome()
	{
		return this.nome;
	}

	public Pedido nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public TipoPedido getTipoPedido()
	{
		return this.tipoPedido;
	}

	public Pedido tipoPedido(TipoPedido tipoPedido)
	{
		this.tipoPedido = tipoPedido;
		return this;
	}

	public void setTipoPedido(TipoPedido tipoPedido)
	{
		this.tipoPedido = tipoPedido;
	}

	public TipoEstampa getTipoEstampaFrente()
	{
		return this.tipoEstampaFrente;
	}

	public Pedido tipoEstampaFrente(TipoEstampa tipoEstampaFrente)
	{
		this.tipoEstampaFrente = tipoEstampaFrente;
		return this;
	}

	public void setTipoEstampaFrente(TipoEstampa tipoEstampaFrente)
	{
		this.tipoEstampaFrente = tipoEstampaFrente;
	}

	public TipoEstampa getTipoEstampaCosta()
	{
		return this.tipoEstampaCosta;
	}

	public Pedido tipoEstampaCosta(TipoEstampa tipoEstampaCosta)
	{
		this.tipoEstampaCosta = tipoEstampaCosta;
		return this;
	}

	public void setTipoEstampaCosta(TipoEstampa tipoEstampaCosta)
	{
		this.tipoEstampaCosta = tipoEstampaCosta;
	}

	public TipoEstampa getTipoEstampaMangaDireita()
	{
		return this.tipoEstampaMangaDireita;
	}

	public Pedido tipoEstampaMangaDireita(TipoEstampa tipoEstampaMangaDireita)
	{
		this.tipoEstampaMangaDireita = tipoEstampaMangaDireita;
		return this;
	}

	public void setTipoEstampaMangaDireita(TipoEstampa tipoEstampaMangaDireita)
	{
		this.tipoEstampaMangaDireita = tipoEstampaMangaDireita;
	}

	public TipoEstampa getTipoEstampaMangaEsquerda()
	{
		return this.tipoEstampaMangaEsquerda;
	}

	public Pedido tipoEstampaMangaEsquerda(TipoEstampa tipoEstampaMangaEsquerda)
	{
		this.tipoEstampaMangaEsquerda = tipoEstampaMangaEsquerda;
		return this;
	}

	public void setTipoEstampaMangaEsquerda(TipoEstampa tipoEstampaMangaEsquerda)
	{
		this.tipoEstampaMangaEsquerda = tipoEstampaMangaEsquerda;
	}

	public LocalDate getDataCriacao()
	{
		return this.dataCriacao;
	}

	public Pedido dataCriacao(LocalDate dataCriacao)
	{
		this.dataCriacao = dataCriacao;
		return this;
	}

	public void setDataCriacao(LocalDate dataCriacao)
	{
		this.dataCriacao = dataCriacao;
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
		Pedido pedido = (Pedido) o;
		if (pedido.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), pedido.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Pedido{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", tipoPedido='" + getTipoPedido() + "'"
				+ ", tipoEstampaFrente='" + getTipoEstampaFrente() + "'" + ", tipoEstampaCosta='"
				+ getTipoEstampaCosta() + "'" + ", tipoEstampaMangaDireita='" + getTipoEstampaMangaDireita() + "'"
				+ ", tipoEstampaMangaEsquerda='" + getTipoEstampaMangaEsquerda() + "'" + ", dataCriacao='"
				+ getDataCriacao() + "'" + "}";
	}

	public User getVendedor()
	{
		return this.vendedor;
	}

	public void setVendedor(User vendedor)
	{
		this.vendedor = vendedor;
	}

	public Cliente getCliente()
	{
		return this.cliente;
	}

	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}

	public String getNomeResponsavel()
	{
		return this.nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel)
	{
		this.nomeResponsavel = nomeResponsavel;
	}

	public Set<PedidoItem> getListaPedidoItem()
	{
		return this.listaPedidoItem;
	}

	public void setListaPedidoItem(Set<PedidoItem> listaPedidoItem)
	{
		this.listaPedidoItem = listaPedidoItem;
	}
}
