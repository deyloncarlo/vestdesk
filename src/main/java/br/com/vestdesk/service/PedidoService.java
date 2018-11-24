package br.com.vestdesk.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.domain.enumeration.StatusPedido;
import br.com.vestdesk.domain.enumeration.StatusPedidoItem;
import br.com.vestdesk.domain.enumeration.TipoPedido;
import br.com.vestdesk.repository.PedidoRepository;
import br.com.vestdesk.service.dto.PedidoDTO;
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

	private EntityManager em;

	public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			PedidoItemService pedidoItemService, ConfiguracaoLayoutService configuracaoLayoutService, EntityManager em,
			VendaAcumuladaService vendaAcumuladaService)
	{
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.pedidoItemService = pedidoItemService;
		this.configuracaoLayoutService = configuracaoLayoutService;
		this.em = em;
		this.vendaAcumuladaService = vendaAcumuladaService;
	}

	/**
	 * Save a pedido.
	 *
	 * @param pedidoDTO the entity to save
	 * @return the persisted entity
	 * @throws Exception
	 */
	public PedidoDTO save(PedidoDTO pedidoDTO) throws Exception
	{
		this.log.debug("Request to save Pedido : {}", pedidoDTO);
		Pedido pedido = this.pedidoMapper.toEntity(pedidoDTO);
		Set<PedidoItem> listaPedidoItem = new HashSet<>(pedido.getListaPedidoItem());
		Set<ConfiguracaoLayout> listaConfiguracaoLayout = new HashSet<>(pedido.getListaConfiguracaoLayout());
		boolean atualizarEstoque = false;

		if (pedido.getStatusPedido().equals(StatusPedido.EM_ANDAMENTO) && pedido.getDataConclusao() == null)
		{
			pedido.setDataConclusao(LocalDate.now());
			atualizarEstoque = true;

		}
		else if (pedido.getStatusPedido().equals(StatusPedido.CONCLUIDO))
		{
			pedido.setDataFim(LocalDate.now());
		}
		if (pedido.getId() != null)
		{
			Pedido pedidoEncontrado = this.pedidoRepository.findOne(pedido.getId());
			Set<PedidoItem> listaPedidoItemRemovido = new HashSet<>();
			Set<ConfiguracaoLayout> listaConfiguracaoLayoutRemovido = new HashSet<>();

			for (PedidoItem pedidoItem : pedidoEncontrado.getListaPedidoItem())
			{
				if (!listaPedidoItem.contains(pedidoItem))
				{
					listaPedidoItemRemovido.add(pedidoItem);
				}
			}

			this.pedidoItemService.delete(listaPedidoItemRemovido);

			for (ConfiguracaoLayout configuracaoLayout : pedidoEncontrado.getListaConfiguracaoLayout())
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
			pedido.setDataCriacao(LocalDate.now());
		}
		this.configuracaoLayoutService.save(listaConfiguracaoLayout);
		pedido.getListaConfiguracaoLayout().clear();
		pedido.getListaConfiguracaoLayout().addAll(listaConfiguracaoLayout);

		if (pedido.getStatusPedido().equals(StatusPedido.EM_ANDAMENTO) && concluirPedido(listaPedidoItem))
		{
			pedido.setStatusPedido(StatusPedido.CONCLUIDO);
		}

		pedido = this.pedidoRepository.save(pedido);

		this.pedidoItemService.save(listaPedidoItem, pedido, atualizarEstoque);
		if (atualizarEstoque)
		{
			atualizarVendaAcumulada(pedido, listaPedidoItem);
		}
		return this.pedidoMapper.toDto(pedido);
	}

	private void atualizarVendaAcumulada(Pedido pedido, Set<PedidoItem> listaPedidoItem)
	{
		HashMap<VendaAcumulada, Integer> listaVendaAcumulada = new HashMap<>();

		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			VendaAcumulada vendaAcumuladaEncontrada = this.vendaAcumuladaService
					.obterPeloProduto(pedidoItem.getProduto());
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
	public Page<PedidoDTO> findAll(Pageable pageable, Long id, TipoPedido tipoPedido)
	{
		this.log.debug("Request to get all Pedidos");

		if (id == null)
		{
			return this.pedidoRepository.findAll(pageable).map(this.pedidoMapper::toDto);
		}

		Query query = this.em
				.createQuery("select pedido from Pedido pedido where id = :idPedido and tipoPedido = :tipoPedido");
		query.setParameter("idPedido", id);
		query.setParameter("tipoPedido", tipoPedido);

		List<Pedido> listaPedido = query.getResultList();
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

		this.pedidoItemService.delete(pedidoEncontrado.getListaPedidoItem());

		this.pedidoRepository.delete(id);
	}

}
