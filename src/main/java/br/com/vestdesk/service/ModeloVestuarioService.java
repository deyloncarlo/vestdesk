package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vestdesk.service.dto.ModeloVestuarioDTO;

/**
 * Service Interface for managing ModeloVestuario.
 */
public interface ModeloVestuarioService
{

	/**
	 * Save a modeloVestuario.
	 *
	 * @param modeloVestuarioDTO the entity to save
	 * @return the persisted entity
	 */
	ModeloVestuarioDTO save(ModeloVestuarioDTO modeloVestuarioDTO);

	/**
	 * Get all the modeloVestuarios.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	Page<ModeloVestuarioDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" modeloVestuario.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	ModeloVestuarioDTO findOne(Long id);

	/**
	 * Delete the "id" modeloVestuario.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
