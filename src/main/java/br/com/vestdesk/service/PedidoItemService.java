package br.com.vestdesk.service;

import java.util.Locale;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
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

	public PedidoItemService(PedidoItemRepository pedidoItemRepository, ProdutoRepository produtoRepository,
			UserService userService, MessageSource messageSource, NotificacaoService notificationService)
	{
		this.pedidoItemRepository = pedidoItemRepository;
		this.produtoRepository = produtoRepository;
		this.userService = userService;
		this.messageSource = messageSource;
		this.notificationService = notificationService;
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
