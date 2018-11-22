package br.com.vestdesk.service;

import java.time.LocalDate;
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
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.enumeration.StatusPedido;
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

	private final ProdutoService produtoService;

	private final ConfiguracaoLayoutService configuracaoLayoutService;

	private EntityManager em;

	public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			PedidoItemService pedidoItemService, ProdutoService produtoService,
			ConfiguracaoLayoutService configuracaoLayoutService, EntityManager em)
	{
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.pedidoItemService = pedidoItemService;
		this.produtoService = produtoService;
		this.configuracaoLayoutService = configuracaoLayoutService;
		this.em = em;
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

		if (pedido.getStatusPedido().equals(StatusPedido.CONCLUIDO))
		{
			pedido.setDataConclusao(LocalDate.now());
		}
		else if (pedido.getStatusPedido().equals(StatusPedido.FINALIZADO))
		{
			pedido.setDataFim(LocalDate.now());
		}
		if (pedido.getId() != null)
		{
			Set<PedidoItem> listaPedidoItemRemovido = new HashSet<>();
			Set<ConfiguracaoLayout> listaConfiguracaoLayoutRemovido = new HashSet<>();
			Pedido pedidoEncontrado = this.pedidoRepository.findOne(pedido.getId());

			for (PedidoItem pedidoItem : pedidoEncontrado.getListaPedidoItem())
			{
				if (!listaPedidoItem.contains(pedidoItem))
				{
					listaPedidoItemRemovido.add(pedidoItem);
					Produto produtoEncontrado = this.produtoService.getById(pedidoItem.getProduto().getId());
					produtoEncontrado.setQuantidadeEstoque(produtoEncontrado.getQuantidadeEstoque() + 1);
					this.produtoService.save(produtoEncontrado);
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

		pedido = this.pedidoRepository.save(pedido);

		this.pedidoItemService.save(listaPedidoItem, pedido);
		return this.pedidoMapper.toDto(pedido);
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
