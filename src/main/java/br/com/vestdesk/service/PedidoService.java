package br.com.vestdesk.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.enumeration.StatusPedido;
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

	public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
			PedidoItemService pedidoItemService, ProdutoService produtoService)
	{
		this.pedidoRepository = pedidoRepository;
		this.pedidoMapper = pedidoMapper;
		this.pedidoItemService = pedidoItemService;
		this.produtoService = produtoService;
	}

	/**
	 * Save a pedido.
	 *
	 * @param pedidoDTO the entity to save
	 * @return the persisted entity
	 */
	public PedidoDTO save(PedidoDTO pedidoDTO)
	{
		this.log.debug("Request to save Pedido : {}", pedidoDTO);
		Pedido pedido = this.pedidoMapper.toEntity(pedidoDTO);
		Set<PedidoItem> listaPedidoItem = new HashSet<>(pedido.getListaPedidoItem());

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
		}
		else
		{
			pedido.setDataCriacao(LocalDate.now());
		}

		pedido = this.pedidoRepository.save(pedido);

		this.pedidoItemService.save(listaPedidoItem, pedido);
		return this.pedidoMapper.toDto(pedido);
	}

	/**
	 * Get all the pedidos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<PedidoDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all Pedidos");
		return this.pedidoRepository.findAll(pageable).map(this.pedidoMapper::toDto);
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
