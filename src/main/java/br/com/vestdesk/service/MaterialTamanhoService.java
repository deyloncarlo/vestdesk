package br.com.vestdesk.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;

/**
 * Service Interface for managing MaterialTamanho.
 */
public interface MaterialTamanhoService
{

	/**
	 * Save a materialTamanho.
	 *
	 * @param materialTamanhoDTO the entity to save
	 * @return the persisted entity
	 */
	MaterialTamanhoDTO save(MaterialTamanhoDTO materialTamanhoDTO);

	Set<MaterialTamanho> save(Set<MaterialTamanho> listaMaterialTamanho, ConfiguracaoProduto configuracaoProduto);

	/**
	 * Get all the materialTamanhos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	Page<MaterialTamanhoDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" materialTamanho.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	MaterialTamanhoDTO findOne(Long id);

	/**
	 * Delete the "id" materialTamanho.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);

	void delete(Set<MaterialTamanho> lsitaMaterialTamanho);
}
