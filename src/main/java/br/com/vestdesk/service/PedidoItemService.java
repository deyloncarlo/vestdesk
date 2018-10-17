package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.service.dto.PedidoItemDTO;

/**
 * Service Interface for managing PedidoItem.
 */
@Service
@Transactional
public class PedidoItemService
{

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
}
