package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MaterialTamanho.
 */
public interface MaterialTamanhoService {

    /**
     * Save a materialTamanho.
     *
     * @param materialTamanhoDTO the entity to save
     * @return the persisted entity
     */
    MaterialTamanhoDTO save(MaterialTamanhoDTO materialTamanhoDTO);

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
}
