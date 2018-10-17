package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.service.dto.UsuarioDTO;

/**
 * Service for managing Usuario.
 */
@Service
@Transactional
public class UsuarioService
{
	/**
	 * Save a usuario.
	 *
	 * @param usuarioDTO the entity to save
	 * @return the persisted entity
	 */
	public UsuarioDTO save(UsuarioDTO usuarioDTO)
	{
		return null;
	}

	/**
	 * Get all the usuarios.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<UsuarioDTO> findAll(Pageable pageable)
	{
		return null;
	}

	/**
	 * Get the "id" usuario.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public UsuarioDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" usuario.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}
}
