package br.com.vestdesk.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.VendaAcumulada;
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

	public VendaAcumuladaService(EntityManager em, VendaAcumuladaRepository vendaAcumuladaRepository,
			VendaAcumuladaMapper vendaAcumuladaMapper, @Lazy PedidoItemRepository pedidoItemRepository)
	{
		this.em = em;
		this.vendaAcumuladaRepository = vendaAcumuladaRepository;
		this.vendaAcumuladaMapper = vendaAcumuladaMapper;
		this.pedidoItemRepository = pedidoItemRepository;
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
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<VendaAcumuladaDTO> findAll(Pageable pageable)
	{
		return this.vendaAcumuladaRepository.findAll(pageable).map(this.vendaAcumuladaMapper::toDto);
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

	public VendaAcumulada produzir(VendaAcumuladaDTO vendaAcumuladaDTO)
	{
		VendaAcumulada vendaAcumuladaDaTela = this.vendaAcumuladaMapper.toEntity(vendaAcumuladaDTO);
		Integer quantidadeProduzir = contarQuantidadeParaProduzir(vendaAcumuladaDaTela.getProduto(),
				vendaAcumuladaDaTela.getListaPedidoItemAcumulado());

		VendaAcumulada vendaAcumalada = getById(vendaAcumuladaDaTela.getId());

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
				pedidoItem.setRetiradoAcumuloVendas(true);
				this.pedidoItemRepository.save(pedidoItem);
			}
		}
		return quantidade;
	}

	public void delete(VendaAcumulada vendaAcumulada)
	{
		this.vendaAcumuladaRepository.delete(vendaAcumulada.getId());
	}

}
