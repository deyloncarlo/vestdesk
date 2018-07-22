package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.AdiantamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Adiantamento.
 */
public interface AdiantamentoService {

    /**
     * Save a adiantamento.
     *
     * @param adiantamentoDTO the entity to save
     * @return the persisted entity
     */
    AdiantamentoDTO save(AdiantamentoDTO adiantamentoDTO);

    /**
     * Get all the adiantamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdiantamentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adiantamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdiantamentoDTO findOne(Long id);

    /**
     * Delete the "id" adiantamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
