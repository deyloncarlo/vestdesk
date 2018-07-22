package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing QuantidadeTamanho.
 */
public interface QuantidadeTamanhoService {

    /**
     * Save a quantidadeTamanho.
     *
     * @param quantidadeTamanhoDTO the entity to save
     * @return the persisted entity
     */
    QuantidadeTamanhoDTO save(QuantidadeTamanhoDTO quantidadeTamanhoDTO);

    /**
     * Get all the quantidadeTamanhos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuantidadeTamanhoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" quantidadeTamanho.
     *
     * @param id the id of the entity
     * @return the entity
     */
    QuantidadeTamanhoDTO findOne(Long id);

    /**
     * Delete the "id" quantidadeTamanho.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
