package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.service.dto.ModeloVestuarioDTO;

/**
 * Service for managing ModeloVestuario.
 */
@Service
@Transactional
public class ModeloVestuarioService
{

	/**
	 * Save a modeloVestuario.
	 *
	 * @param modeloVestuarioDTO the entity to save
	 * @return the persisted entity
	 */
	public ModeloVestuarioDTO save(ModeloVestuarioDTO modeloVestuarioDTO)
	{
		return null;
	}

	/**
	 * Get all the modeloVestuarios.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<ModeloVestuarioDTO> findAll(Pageable pageable)
	{
		return null;
	}

	/**
	 * Get the "id" modeloVestuario.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public ModeloVestuarioDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" modeloVestuario.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}
}
