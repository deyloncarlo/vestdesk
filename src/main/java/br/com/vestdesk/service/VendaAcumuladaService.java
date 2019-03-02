package br.com.vestdesk.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.domain.enumeration.StatusVendaAcumulada;
import br.com.vestdesk.repository.PedidoItemRepository;
import br.com.vestdesk.repository.VendaAcumuladaRepository;
import br.com.vestdesk.service.dto.UsuarioDTO;
import br.com.vestdesk.service.dto.VendaAcumuladaDTO;
import br.com.vestdesk.service.mapper.VendaAcumuladaMapper;

/**
 * Service for managing Usuario.
 */
@Service
@Transactional
public class VendaAcumuladaService
{
	private final EntityManager em;

	private final VendaAcumuladaRepository vendaAcumuladaRepository;

	private final VendaAcumuladaMapper vendaAcumuladaMapper;

	private final PedidoItemRepository pedidoItemRepository;

	private final ProdutoService produtoService;

	public VendaAcumuladaService(EntityManager em, VendaAcumuladaRepository vendaAcumuladaRepository,
			VendaAcumuladaMapper vendaAcumuladaMapper, @Lazy PedidoItemRepository pedidoItemRepository,
			@Lazy ProdutoService produtoService)
	{
		this.em = em;
		this.vendaAcumuladaRepository = vendaAcumuladaRepository;
		this.vendaAcumuladaMapper = vendaAcumuladaMapper;
		this.pedidoItemRepository = pedidoItemRepository;
		this.produtoService = produtoService;
	}

	/**
	 * Save a usuario.
	 *
	 * @param usuarioDTO the entity to save
	 * @return the persisted entity
	 */
	public UsuarioDTO save(VendaAcumuladaDTO vendaAcumuladaDTO)
	{
		return null;
	}

	public void save(VendaAcumulada vendaAcumulada)
	{
		this.vendaAcumuladaRepository.save(vendaAcumulada);
	}

	/**
	 * Get all the usuarios.
	 *
	 * @param pageable the pagination information
	 * @param status
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<VendaAcumuladaDTO> findAll(Pageable pageable, StatusVendaAcumulada status)
	{
		CriteriaBuilder criteriaBuider = this.em.getCriteriaBuilder();
		CriteriaQuery<VendaAcumulada> criteria = criteriaBuider.createQuery(VendaAcumulada.class);
		Root<VendaAcumulada> root = criteria.from(VendaAcumulada.class);

		Predicate statusPredicate = criteriaBuider.equal(root.<StatusVendaAcumulada>get("status"), (status));
		criteria.where(statusPredicate);

		List<VendaAcumulada> listaVendaAcumulada = this.em.createQuery(criteria).getResultList();

		Page<VendaAcumulada> page = new PageImpl<>(listaVendaAcumulada, pageable, listaVendaAcumulada.size());

		return page.map(this.vendaAcumuladaMapper::toDto);
	}

	/**
	 * Get the "id" usuario.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public VendaAcumuladaDTO findOne(Long id)
	{
		return null;
	}

	public VendaAcumulada getById(Long id)
	{
		return this.vendaAcumuladaRepository.findOne(id);
	}

	/**
	 * Delete the "id" usuario.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}

	public VendaAcumulada obterPeloProduto(Produto produto)
	{
		Query query = this.em.createQuery(
				"SELECT vendaAcumulada FROM VendaAcumulada vendaAcumulada " + "WHERE produto.id = :produtoId");
		query.setParameter("produtoId", produto.getId());

		List<VendaAcumulada> listaVendaAcumulada = query.getResultList();
		if (!listaVendaAcumulada.isEmpty())
		{
			return listaVendaAcumulada.get(0);
		}
		return null;
	}

	public VendaAcumulada obterVendaAcumuladaSemStatusProduto(Produto produto)
	{
		Query query = this.em.createQuery("SELECT vendaAcumulada FROM VendaAcumulada vendaAcumulada "
				+ "WHERE produto.id = :produtoId and status = 'SEM_STATUS'");
		query.setParameter("produtoId", produto.getId());

		List<VendaAcumulada> listaVendaAcumulada = query.getResultList();
		if (!listaVendaAcumulada.isEmpty())
		{
			return listaVendaAcumulada.get(0);
		}
		return null;
	}

	public VendaAcumulada produzir(VendaAcumuladaDTO vendaAcumuladaDTO)
	{
		VendaAcumulada vendaAcumuladaDaTela = this.vendaAcumuladaMapper.toEntity(vendaAcumuladaDTO);

		VendaAcumulada vendaAcumalada = getById(vendaAcumuladaDaTela.getId());
		vendaAcumalada.setStatus(StatusVendaAcumulada.EM_PRODUCAO);
		return this.vendaAcumuladaRepository.save(vendaAcumalada);

	}

	public VendaAcumulada concluir(VendaAcumuladaDTO vendaAcumuladaDTO)
	{
		VendaAcumulada vendaAcumuladaDaTela = this.vendaAcumuladaMapper.toEntity(vendaAcumuladaDTO);
		Integer quantidadeProduzir = contarQuantidadeParaProduzir(vendaAcumuladaDaTela.getProduto(),
				vendaAcumuladaDaTela.getListaPedidoItemAcumulado());

		VendaAcumulada vendaAcumalada = getById(vendaAcumuladaDaTela.getId());
		vendaAcumalada.setStatus(StatusVendaAcumulada.PRODUZIDO);

		Produto produto = this.produtoService.getById(vendaAcumalada.getProduto().getId());
		produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeProduzir);
		this.produtoService.save(produto);

		vendaAcumalada.getListaPedidoItemProduzido().addAll(vendaAcumalada.getListaPedidoItemAcumulado());
		vendaAcumalada.getListaPedidoItemAcumulado().clear();
		vendaAcumalada.setQuantidadeAcumulada(vendaAcumalada.getQuantidadeAcumulada() - quantidadeProduzir);
		return this.vendaAcumuladaRepository.save(vendaAcumalada);
	}

	public Integer contarQuantidadeParaProduzir(Produto produto, Set<PedidoItem> listaPedidoItem)
	{
		Integer quantidade = 0;

		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			PedidoItem pedidoItemEncontrado = this.pedidoItemRepository.findOne(pedidoItem.getId());
			if (pedidoItemEncontrado.getProduto().equals(produto))
			{
				quantidade += pedidoItemEncontrado.getQuantidade();
				pedidoItemEncontrado.setRetiradoAcumuloVendas(true);
				this.pedidoItemRepository.save(pedidoItemEncontrado);
			}
		}
		return quantidade;
	}

	public void delete(VendaAcumulada vendaAcumulada)
	{
		this.vendaAcumuladaRepository.delete(vendaAcumulada.getId());
	}

	public List<VendaAcumulada> obterPorListaPedidoItemAcumulado(Set<PedidoItem> listaPedidoItem)
	{

		List<Long> listaId = listaPedidoItem.stream().map(PedidoItem::getId).collect(Collectors.toList());
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<VendaAcumulada> criteria = criteriaBuilder.createQuery(VendaAcumulada.class);
		Root<VendaAcumulada> root = criteria.from(VendaAcumulada.class);
		Join<VendaAcumulada, PedidoItem> joinPedidoItemAcumulado = root.join("listaPedidoItemAcumulado");
		Path<Long> pathIdAcumulado = joinPedidoItemAcumulado.get("id");
		// Join<VendaAcumulada, PedidoItem> joinPedidoItemProduzido =
		// root.join("listaPedidoItemProduzido");
		// Path<Long> pathIdProduzido = joinPedidoItemProduzido.get("id");

		Predicate predicateAcumulado = pathIdAcumulado.in(listaId);

		criteria.where(predicateAcumulado);
		TypedQuery<VendaAcumulada> query = this.em.createQuery(criteria);
		List<VendaAcumulada> listaVendaAcumulada = query.getResultList();
		return listaVendaAcumulada;

	}

	public List<VendaAcumulada> obterPorListaPedidoItemProduzido(Set<PedidoItem> listaPedidoItem)
	{

		List<Long> listaId = listaPedidoItem.stream().map(PedidoItem::getId).collect(Collectors.toList());
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<VendaAcumulada> criteria = criteriaBuilder.createQuery(VendaAcumulada.class);
		Root<VendaAcumulada> root = criteria.from(VendaAcumulada.class);
		Join<VendaAcumulada, PedidoItem> joinPedidoItemProduzido = root.join("listaPedidoItemProduzido");
		Path<Long> pathIdProduzido = joinPedidoItemProduzido.get("id");

		Predicate predicateProduzido = pathIdProduzido.in(listaId);

		criteria.where(predicateProduzido);
		TypedQuery<VendaAcumulada> query = this.em.createQuery(criteria);
		List<VendaAcumulada> listaVendaAcumulada = query.getResultList();
		return listaVendaAcumulada;

	}

}
