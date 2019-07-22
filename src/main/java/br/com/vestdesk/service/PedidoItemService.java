package br.com.vestdesk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.User;
import br.com.vestdesk.repository.PedidoItemRepository;
import br.com.vestdesk.repository.ProdutoRepository;
import br.com.vestdesk.service.dto.PedidoItemDTO;
import br.com.vestdesk.service.dto.RelatorioVendaItemDTO;
import br.com.vestdesk.service.mapper.PedidoItemMapper;
import br.com.vestdesk.service.mapper.ProdutoMapper;

/**
 * Service Interface for managing PedidoItem.
 */
@Service
@Transactional
public class PedidoItemService
{

	private final PedidoItemRepository pedidoItemRepository;

	private final ProdutoRepository produtoRepository;

	private final UserService userService;

	private final MessageSource messageSource;

	private final NotificacaoService notificationService;

	private final EntityManager em;

	private final PedidoItemMapper pedidoItemMapper;

	private final ProdutoMapper produtoMapper;

	public PedidoItemService(PedidoItemRepository pedidoItemRepository, ProdutoRepository produtoRepository,
			UserService userService, MessageSource messageSource, NotificacaoService notificationService,
			EntityManager em, PedidoItemMapper pedidoItemMapper, ProdutoMapper produtoMapper)
	{
		this.pedidoItemRepository = pedidoItemRepository;
		this.produtoRepository = produtoRepository;
		this.userService = userService;
		this.messageSource = messageSource;
		this.notificationService = notificationService;
		this.em = em;
		this.pedidoItemMapper = pedidoItemMapper;
		this.produtoMapper = produtoMapper;
	}

	/**
	 * Save a pedidoItem.
	 *
	 * @param pedidoItemDTO the entity to save
	 * @return the persisted entity
	 */
	public PedidoItemDTO save(PedidoItemDTO pedidoItemDTO)
	{
		return null;
	}

	/**
	 * Get all the pedidoItems.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<RelatorioVendaItemDTO> getRelatorioVenda(Pageable pageable)
	{
		Query query = this.em.createQuery("SELECT pedidoItem FROM PedidoItem pedidoItem"
				+ " LEFT JOIN FETCH pedidoItem.produto produto" + " LEFT JOIN FETCH produto.cor cor"
				+ " LEFT JOIN FETCH pedidoItem.pedido pedido" + " where pedido.statusPedido != 'RASCUNHO'");

		// CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		// CriteriaQuery<PedidoItem> criteria =
		// criteriaBuilder.createQuery(PedidoItem.class);
		// Root<PedidoItem> root = criteria.from(PedidoItem.class);
		//
		// List<Predicate> predicates = new ArrayList<Predicate>();
		// // if (id != null)
		// // {
		// // predicates.add((criteriaBuilder.equal(root.get("id"), id)));
		// // }
		// // if (statusPedido != null)
		// // {
		// // predicates.add(criteriaBuilder.equal(root.get("statusPedido"),
		// // StatusPedido.valueOf(statusPedido)));
		// // }
		// // if (fechaEm10Dias)
		// // {
		// // Predicate predicate1 =
		// // criteriaBuilder.between(root.<LocalDate>get("dataPrevisao"),
		// // LocalDate.now(),
		// // LocalDate.now().plusDays(10L));
		// // predicates.add(predicate1);
		// //
		// // Predicate predicate2 =
		// // criteriaBuilder.equal(root.get("statusPedido"),
		// // StatusPedido.EM_ANDAMENTO);
		// // predicates.add(predicate2);
		// // }
		// criteria.where(criteriaBuilder.and(predicates.toArray(new Predicate[]
		// {})));
		// TypedQuery<PedidoItem> query = this.em.createQuery(criteria);

		List<PedidoItem> listaPedidoItem = query.getResultList();
		HashMap<Produto, Integer> hashProdutoPedidoItemList = new HashMap<>();
		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			if (!hashProdutoPedidoItemList.containsKey(pedidoItem.getProduto()))
			{
				hashProdutoPedidoItemList.put(pedidoItem.getProduto(), 0);
				hashProdutoPedidoItemList.replace(pedidoItem.getProduto(), pedidoItem.getQuantidade());
			}
			else
			{
				hashProdutoPedidoItemList.replace(pedidoItem.getProduto(),
						hashProdutoPedidoItemList.get(pedidoItem.getProduto()) + pedidoItem.getQuantidade());
			}
		}

		List<RelatorioVendaItemDTO> list = new ArrayList<>();
		for (Produto produto : hashProdutoPedidoItemList.keySet())
		{
			RelatorioVendaItemDTO relatorioVendaItemDTO = new RelatorioVendaItemDTO();
			relatorioVendaItemDTO.setProduto(this.produtoMapper.toDto(produto));
			relatorioVendaItemDTO.setAmount(hashProdutoPedidoItemList.get(produto));
			list.add(relatorioVendaItemDTO);
		}

		Page<RelatorioVendaItemDTO> page = new PageImpl<>(list, pageable, list.size());
		return page;
	}

	/**
	 * Get all the pedidoItems.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<PedidoItemDTO> findAll(Pageable pageable)
	{
		return null;
	}

	/**
	 * Get the "id" pedidoItem.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public PedidoItemDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" pedidoItem.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}

	public void save(Set<PedidoItem> listaPedidoItem, Pedido pedido, boolean atualizarEstoque) throws Exception
	{
		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			if (pedidoItem.getId() == null)
			{
				pedidoItem.setPedido(pedido);
			}
			if (atualizarEstoque)
			{
				Produto produtoEncontrado = this.produtoRepository.findOne(pedidoItem.getProduto().getId());
				produtoEncontrado
						.setQuantidadeEstoque(produtoEncontrado.getQuantidadeEstoque() - pedidoItem.getQuantidade());
				this.produtoRepository.save(produtoEncontrado);
				throwNotificationIfMinimunQuantityReached(produtoEncontrado);
			}
			this.pedidoItemRepository.save(pedidoItem);
		}
	}

	public void throwNotificationIfMinimunQuantityReached(Produto product)
	{
		if (product.getQuantidadeEstoque() != null && product.getQuantidadeMinima() != null
				&& product.getQuantidadeEstoque() <= product.getQuantidadeMinima())
		{
			User currentUser = this.userService.getCurrentUser();
			Locale locale = Locale.forLanguageTag(currentUser.getLangKey());
			String titleNotification = this.messageSource.getMessage("notification.productMinimunQuantityReached",
					new Object[] { product.getCodigo(), product.getQuantidadeMinima().toString() }, locale);
			this.notificationService.generateNotifications(titleNotification);
		}
	}

	public void delete(Set<PedidoItem> listaPedidoItemRemovido)
	{
		for (PedidoItem pedidoItem : listaPedidoItemRemovido)
		{
			this.pedidoItemRepository.delete(pedidoItem);
		}

	}

	public PedidoItem getById(Long id)
	{
		return this.pedidoItemRepository.findOne(id);
	}
}
