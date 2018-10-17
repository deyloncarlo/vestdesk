package br.com.vestdesk.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;

/**
 * Service for managing ConfiguracaoProduto.
 */
@Service
@Transactional
public class ConfiguracaoProdutoService
{

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	public ConfiguracaoProdutoDTO save(ConfiguracaoProdutoDTO configuracaoProdutoDTO)
	{
		return null;
	}

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	public ConfiguracaoProdutoDTO saveList(Set<ConfiguracaoProduto> listaConfiguracaoProduto,
			ModeloVestuario modeloVestuario)
	{
		return null;
	}

	/**
	 * Get all the configuracaoProdutos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<ConfiguracaoProdutoDTO> findAll(Pageable pageable)
	{
		return null;
	}

	public List<ConfiguracaoProdutoDTO> filtrar(Long idModeloVestuario)
	{
		return null;
	}

	/**
	 * Get the "id" configuracaoProduto.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public ConfiguracaoProdutoDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" configuracaoProduto.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}

	public void delete(Set<ConfiguracaoProduto> listaConfiguracaoProduto)
	{
	}
}
