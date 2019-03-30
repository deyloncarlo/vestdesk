package br.com.vestdesk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.User;
import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.domain.enumeration.StatusPedido;
import br.com.vestdesk.domain.enumeration.StatusPedidoItem;
import br.com.vestdesk.domain.enumeration.StatusVendaAcumulada;
import br.com.vestdesk.repository.PedidoRepository;
import br.com.vestdesk.service.dto.PedidoDTO;
import br.com.vestdesk.service.dto.PedidoGridDTO;
import br.com.vestdesk.service.mapper.PedidoMapper;

/**
 * Service for managing Pedido.
 */

@Service
@Transactional
public class PedidoService
{

	private final Logger log = LoggerFactory.getLogger(PedidoService.class);

	private final PedidoRepository pedidoRepository;

	private final PedidoMapper pedidoMapper;

	private final PedidoItemService pedidoItemService;

	private final VendaAcumuladaService vendaAcumuladaService;

	private final ConfiguracaoLayoutService configuracaoLayoutService;

	private final UserService userService;

	private final MessageSource messageSource;

	private final NotificacaoService notificationService;

	private EntityManager em;

	public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			PedidoItemService pedidoItemService, ConfiguracaoLayoutService configuracaoLayoutService, EntityManager em,
			VendaAcumuladaService vendaAcumuladaService, UserService userService, MessageSource messageSource,
			NotificacaoService notificationService)
	{
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.pedidoItemService = pedidoItemService;
		this.configuracaoLayoutService = configuracaoLayoutService;
		this.em = em;
		this.vendaAcumuladaService = vendaAcumuladaService;
		this.userService = userService;
		this.messageSource = messageSource;
		this.notificationService = notificationService;
	}

	/**
	 * Save a pedido.
	 *
	 * @param orderDTO the entity to save
	 * @return the persisted entity
	 * @throws Exception
	 */
	public PedidoDTO save(PedidoDTO orderDTO) throws Exception
	{
		this.log.debug("Request to save Pedido : {}", orderDTO);
		Pedido order = this.pedidoMapper.toEntity(orderDTO);
		Set<PedidoItem> listaPedidoItem = new HashSet<>(order.getListaPedidoItem());
		Set<ConfiguracaoLayout> listaConfiguracaoLayout = new HashSet<>(order.getListaConfiguracaoLayout());
		boolean atualizarEstoque = false;
		Pedido orderFromDatabase = null;
		if (order.getStatusPedido().equals(StatusPedido.EM_ANDAMENTO) && order.getDataConclusao() == null)
		{
			order.setDataConclusao(LocalDate.now());
			atualizarEstoque = true;

		}
		else if (order.getStatusPedido().equals(StatusPedido.CONCLUIDO))
		{
			order.setDataFim(LocalDate.now());
		}
		if (order.getId() != null)
		{
			orderFromDatabase = this.pedidoRepository.findOne(order.getId());
			Set<PedidoItem> listaPedidoItemRemovido = new HashSet<>();
			Set<ConfiguracaoLayout> listaConfiguracaoLayoutRemovido = new HashSet<>();

			for (PedidoItem pedidoItem : orderFromDatabase.getListaPedidoItem())
			{
				if (!listaPedidoItem.contains(pedidoItem))
				{
					listaPedidoItemRemovido.add(pedidoItem);
				}
			}

			this.pedidoItemService.delete(listaPedidoItemRemovido);

			for (ConfiguracaoLayout configuracaoLayout : orderFromDatabase.getListaConfiguracaoLayout())
			{
				if (!listaConfiguracaoLayout.contains(configuracaoLayout))
				{
					listaConfiguracaoLayoutRemovido.add(configuracaoLayout);
				}
			}
			this.configuracaoLayoutService.delete(listaConfiguracaoLayoutRemovido);
		}
		else
		{
			order.setDataCriacao(LocalDate.now());
		}
		this.configuracaoLayoutService.save(listaConfiguracaoLayout);
		order.getListaConfiguracaoLayout().clear();
		order.getListaConfiguracaoLayout().addAll(listaConfiguracaoLayout);

		if (order.getStatusPedido().equals(StatusPedido.EM_ANDAMENTO) && concluirPedido(listaPedidoItem))
		{
			order.setStatusPedido(StatusPedido.CONCLUIDO);
		}

		throwNotificationIfOrderStatusChange(order, orderFromDatabase);
		order = this.pedidoRepository.save(order);
		throwNotificationIfNewOrder(order, orderDTO);

		this.pedidoItemService.save(listaPedidoItem, order, atualizarEstoque);
		if (atualizarEstoque)
		{
			atualizarVendaAcumulada(order, listaPedidoItem);
		}

		return this.pedidoMapper.toDto(order);
	}

	private void throwNotificationIfOrderStatusChange(Pedido order, Pedido orderFromDatabase)
	{
		if (orderFromDatabase != null && !orderFromDatabase.getStatusPedido().equals(order.getStatusPedido()))
		{
			User currentUser = this.userService.getCurrentUser();
			Locale locale = Locale.forLanguageTag(currentUser.getLangKey());
			String titleNotification = this.messageSource.getMessage("notification.orderStatusChanged",
					new Object[] { orderFromDatabase.getId().toString(), order.getStatusPedido().toString() }, locale);
			this.notificationService.generateNotifications(titleNotification);
		}
	}

	private void throwNotificationIfNewOrder(Pedido order, PedidoDTO orderDTO)
	{
		if (orderDTO.getId() == null)
		{
			User currentUser = this.userService.getCurrentUser();
			Locale locale = Locale.forLanguageTag(currentUser.getLangKey());
			String titleNotification = this.messageSource.getMessage("notification.newOrder",
					new Object[] { order.getId().toString(), orderDTO.getStatusPedido().toString() }, locale);
			this.notificationService.generateNotifications(titleNotification);
		}
	}

	private void atualizarVendaAcumulada(Pedido pedido, Set<PedidoItem> listaPedidoItem)
	{
		HashMap<VendaAcumulada, Integer> listaVendaAcumulada = new HashMap<>();

		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			VendaAcumulada vendaAcumuladaEncontrada = this.vendaAcumuladaService
					.obterVendaAcumuladaSemStatusProduto(pedidoItem.getProduto());
			if (vendaAcumuladaEncontrada == null)
			{
				for (VendaAcumulada vendaNaLista : listaVendaAcumulada.keySet())
				{
					if (vendaNaLista.getProduto().getId().equals(pedidoItem.getProduto().getId()))
					{
						vendaAcumuladaEncontrada = vendaNaLista;
						break;
					}
				}
				if (vendaAcumuladaEncontrada == null)
				{
					vendaAcumuladaEncontrada = new VendaAcumulada();
					vendaAcumuladaEncontrada.setProduto(pedidoItem.getProduto());
					vendaAcumuladaEncontrada.setStatus(StatusVendaAcumulada.SEM_STATUS);
				}
			}
			if (!listaVendaAcumulada.containsKey(vendaAcumuladaEncontrada))
			{
				listaVendaAcumulada.put(vendaAcumuladaEncontrada, pedidoItem.getQuantidade());
			}
			else
			{
				Integer quantidade = listaVendaAcumulada.get(vendaAcumuladaEncontrada);
				listaVendaAcumulada.replace(vendaAcumuladaEncontrada, quantidade,
						quantidade + pedidoItem.getQuantidade());
			}
			vendaAcumuladaEncontrada.getListaPedidoItemAcumulado().add(pedidoItem);
		}

		for (VendaAcumulada vendaAcumulada : listaVendaAcumulada.keySet())
		{
			Integer quantidade = vendaAcumulada.getQuantidadeAcumulada();
			if (quantidade == null)
			{
				quantidade = 0;
			}
			vendaAcumulada.setQuantidadeAcumulada(quantidade + listaVendaAcumulada.get(vendaAcumulada));
			this.vendaAcumuladaService.save(vendaAcumulada);
		}
	}

	public boolean concluirPedido(Set<PedidoItem> listaPedidoItem)
	{
		boolean podeConcluir = true;
		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			if (pedidoItem.getStatus() == null || !pedidoItem.getStatus().equals(StatusPedidoItem.PRONTO))
			{
				podeConcluir = false;
				break;
			}
		}
		return podeConcluir;
	}

	/**
	 * Get all the pedidos.
	 *
	 * @param pageable the pagination information
	 * @param id
	 * @param tipoPedido
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<PedidoGridDTO> findAll(Pageable pageable, Long id, String statusPedido, boolean fechaEm10Dias)
	{

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteria = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteria.from(Pedido.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (id != null)
		{
			predicates.add((criteriaBuilder.equal(root.get("id"), id)));
		}
		if (statusPedido != null)
		{
			predicates.add(criteriaBuilder.equal(root.get("statusPedido"), StatusPedido.valueOf(statusPedido)));
		}
		if (fechaEm10Dias)
		{
			Predicate predicate1 = criteriaBuilder.between(root.<LocalDate>get("dataPrevisao"), LocalDate.now(),
					LocalDate.now().plusDays(10L));
			predicates.add(predicate1);

			Predicate predicate2 = criteriaBuilder.equal(root.get("statusPedido"), StatusPedido.EM_ANDAMENTO);
			predicates.add(predicate2);
		}
		criteria.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
		int primeiroRegistro = pageable.getPageNumber() * pageable.getPageSize();
		TypedQuery<Pedido> query = this.em.createQuery(criteria);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(pageable.getPageSize());

		List<Pedido> listaPedido = query.getResultList();
		Page<Pedido> page = new PageImpl<>(listaPedido, pageable, listaPedido.size());

		return page.map(this.pedidoMapper::toGridDto);
	}

	@Transactional(readOnly = true)
	public Page<PedidoDTO> findAll(Pageable pageable, Long v_id, String statusPedido)
	{
		this.log.debug("Request to get all Pedidos");

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteria = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteria.from(Pedido.class);
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("statusPedido"), "EM_ANDAMENTO"));

		List<Pedido> listaPedido = this.em.createQuery(criteria).getResultList();
		Page<Pedido> page = new PageImpl<>(listaPedido, pageable, listaPedido.size());

		return page.map(this.pedidoMapper::toDto);
	}

	/**
	 * Get one pedido by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public PedidoDTO findOne(Long id)
	{
		this.log.debug("Request to get Pedido : {}", id);
		Pedido pedido = this.pedidoRepository.findOne(id);
		return this.pedidoMapper.toDto(pedido);
	}

	public Pedido getById(Long id)
	{
		return this.pedidoRepository.findOne(id);
	}

	/**
	 * Delete the pedido by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.log.debug("Request to delete Pedido : {}", id);

		Pedido pedidoEncontrado = this.pedidoRepository.findOne(id);

		List<VendaAcumulada> listaVendaAcumulada = this.vendaAcumuladaService
				.obterPorListaPedidoItemAcumulado(pedidoEncontrado.getListaPedidoItem());
		List<VendaAcumulada> listaVendaProduzido = this.vendaAcumuladaService
				.obterPorListaPedidoItemProduzido(pedidoEncontrado.getListaPedidoItem());

		for (VendaAcumulada vendaAcumulada : listaVendaAcumulada)
		{
			vendaAcumulada.getListaPedidoItemAcumulado().removeAll(pedidoEncontrado.getListaPedidoItem());
			this.vendaAcumuladaService.save(vendaAcumulada);
		}

		for (VendaAcumulada vendaAcumulada : listaVendaProduzido)
		{
			vendaAcumulada.getListaPedidoItemProduzido().removeAll(pedidoEncontrado.getListaPedidoItem());
			this.vendaAcumuladaService.save(vendaAcumulada);
		}

		this.pedidoItemService.delete(pedidoEncontrado.getListaPedidoItem());

		this.pedidoRepository.delete(id);
	}

	/**
	 * Método que obtém a quantidade de Pedidos com Status igual a RASCUNHO
	 * 
	 * @return
	 */
	public Long obterQuantidadePedidoStatusRascunho()
	{

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
		Root<Pedido> root = criteria.from(Pedido.class);
		criteria.select(criteriaBuilder.count(root));
		criteria.where(criteriaBuilder.equal(root.get("statusPedido"), StatusPedido.RASCUNHO)).distinct(true);

		Long quantidade = (long) this.em.createQuery(criteria).getSingleResult();
		return quantidade;
	}

	/**
	 * Método que obtém a quantidade de Pedidos que serão fechados em 10 dias.
	 * 
	 * @return
	 */
	public Long obterQuantidadePedidoSeraoFechados10Dias()
	{

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
		Root<Pedido> root = criteria.from(Pedido.class);
		criteria.select(criteriaBuilder.count(root));
		Predicate predicate0 = criteriaBuilder.between(root.<LocalDate>get("dataPrevisao"), LocalDate.now(),
				LocalDate.now().plusDays(10L));

		// Predicate predicate1 =
		// criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("dataPrevisao"),
		// LocalDate.now().plusDays(10L));

		Predicate predicate2 = criteriaBuilder.equal(root.get("statusPedido"), StatusPedido.EM_ANDAMENTO);

		criteria.where(predicate0, predicate2).distinct(true);

		Long quantidade = (long) this.em.createQuery(criteria).getSingleResult();
		return quantidade;
	}

}
