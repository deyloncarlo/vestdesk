package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;

/**
 * Service for managing QuantidadeTamanho.
 */
@Service
@Transactional
public class QuantidadeTamanhoService
{

	/**
	 * Save a quantidadeTamanho.
	 *
	 * @param quantidadeTamanhoDTO the entity to save
	 * @return the persisted entity
	 */
	public QuantidadeTamanhoDTO save(QuantidadeTamanhoDTO quantidadeTamanhoDTO)
	{
		return null;
	}

	/**
	 * Get all the quantidadeTamanhos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<QuantidadeTamanhoDTO> findAll(Pageable pageable)
	{
		return null;
	}

	/**
	 * Get the "id" quantidadeTamanho.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public QuantidadeTamanhoDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" quantidadeTamanho.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}
}
