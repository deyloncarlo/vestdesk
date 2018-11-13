package br.com.vestdesk.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.domain.enumeration.Tamanho;
import br.com.vestdesk.repository.PedidoItemRepository;
import br.com.vestdesk.service.dto.PedidoItemDTO;

/**
 * Service Interface for managing PedidoItem.
 */
@Service
@Transactional
public class PedidoItemService
{

	private final PedidoItemRepository pedidoItemRepository;

	private final ProdutoService produtoService;

	public PedidoItemService(PedidoItemRepository pedidoItemRepository, ProdutoService produtoService)
	{
		this.pedidoItemRepository = pedidoItemRepository;
		this.produtoService = produtoService;
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

	public void save(Set<PedidoItem> listaPedidoItem, Pedido pedido) throws Exception
	{
		for (PedidoItem pedidoItem : listaPedidoItem)
		{
			if (pedidoItem.getProduto().getId() == null)
			{
				Modelo modelo = pedidoItem.getProduto().getModelo();
				Tamanho tamanho = pedidoItem.getProduto().getTamanho();
				Set<Cor> listaCor = pedidoItem.getProduto().getListaCor();
				Produto produtoEncontrado = this.produtoService.obterPeloModeloTamanhoCor(modelo, tamanho, listaCor);
				if (produtoEncontrado == null)
				{
					throw new RuntimeException("error.produto.nenhumProdutoCadastrado");
				}
				produtoEncontrado.setQuantidadeEstoque(produtoEncontrado.getQuantidadeEstoque() - 1);
				pedidoItem.setProduto(produtoEncontrado);
			}
			pedidoItem.setPedido(pedido);
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
}
