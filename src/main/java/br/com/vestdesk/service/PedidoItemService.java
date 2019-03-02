package br.com.vestdesk.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.repository.PedidoItemRepository;
import br.com.vestdesk.repository.ProdutoRepository;
import br.com.vestdesk.service.dto.PedidoItemDTO;

/**
 * Service Interface for managing PedidoItem.
 */
@Service
@Transactional
public class PedidoItemService
{

	private final PedidoItemRepository pedidoItemRepository;

	private final ProdutoRepository produtoRepository;

	public PedidoItemService(PedidoItemRepository pedidoItemRepository, ProdutoRepository produtoRepository)
	{
		this.pedidoItemRepository = pedidoItemRepository;
		this.produtoRepository = produtoRepository;
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
			// Produto produtoEncontrado =
			// this.produtoService.obterPeloModeloTamanhoCor(modelo, tamanho,
			// cor);
			// if (produtoEncontrado == null)
			// {
			// throw new
			// RuntimeException("error.produto.nenhumProdutoCadastrado");
			// }
			if (atualizarEstoque)
			{
				Produto produtoEncontrado = this.produtoRepository.findOne(pedidoItem.getProduto().getId());
				produtoEncontrado
						.setQuantidadeEstoque(produtoEncontrado.getQuantidadeEstoque() - pedidoItem.getQuantidade());
				this.produtoRepository.save(produtoEncontrado);
			}
			this.pedidoItemRepository.save(pedidoItem);
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
