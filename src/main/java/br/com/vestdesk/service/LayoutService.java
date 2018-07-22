package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.LayoutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Layout.
 */
public interface LayoutService {

    /**
     * Save a layout.
     *
     * @param layoutDTO the entity to save
     * @return the persisted entity
     */
    LayoutDTO save(LayoutDTO layoutDTO);

    /**
     * Get all the layouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LayoutDTO> findAll(Pageable pageable);

    /**
     * Get the "id" layout.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LayoutDTO findOne(Long id);

    /**
     * Delete the "id" layout.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
