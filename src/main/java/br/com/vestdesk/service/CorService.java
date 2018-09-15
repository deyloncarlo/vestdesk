package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.CorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cor.
 */
public interface CorService {

    /**
     * Save a cor.
     *
     * @param corDTO the entity to save
     * @return the persisted entity
     */
    CorDTO save(CorDTO corDTO);

    /**
     * Get all the cors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorDTO findOne(Long id);

    /**
     * Delete the "id" cor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
