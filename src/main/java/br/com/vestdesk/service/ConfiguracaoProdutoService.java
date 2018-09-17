package br.com.vestdesk.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;

/**
 * Service Interface for managing ConfiguracaoProduto.
 */
public interface ConfiguracaoProdutoService
{

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	ConfiguracaoProdutoDTO save(ConfiguracaoProdutoDTO configuracaoProdutoDTO);

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	ConfiguracaoProdutoDTO saveList(Set<ConfiguracaoProduto> listaConfiguracaoProduto, ModeloVestuario modeloVestuario);

	/**
	 * Get all the configuracaoProdutos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	Page<ConfiguracaoProdutoDTO> findAll(Pageable pageable);

	List<ConfiguracaoProdutoDTO> filtrar(Long idModeloVestuario);

	/**
	 * Get the "id" configuracaoProduto.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	ConfiguracaoProdutoDTO findOne(Long id);

	/**
	 * Delete the "id" configuracaoProduto.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);

	void delete(Set<ConfiguracaoProduto> listaConfiguracaoProduto);
}
