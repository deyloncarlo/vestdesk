package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ConfiguracaoProduto.
 */
public interface ConfiguracaoProdutoService {

    /**
     * Save a configuracaoProduto.
     *
     * @param configuracaoProdutoDTO the entity to save
     * @return the persisted entity
     */
    ConfiguracaoProdutoDTO save(ConfiguracaoProdutoDTO configuracaoProdutoDTO);

    /**
     * Get all the configuracaoProdutos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConfiguracaoProdutoDTO> findAll(Pageable pageable);

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
}
