package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.EtapaProducaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EtapaProducao.
 */
public interface EtapaProducaoService {

    /**
     * Save a etapaProducao.
     *
     * @param etapaProducaoDTO the entity to save
     * @return the persisted entity
     */
    EtapaProducaoDTO save(EtapaProducaoDTO etapaProducaoDTO);

    /**
     * Get all the etapaProducaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EtapaProducaoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" etapaProducao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EtapaProducaoDTO findOne(Long id);

    /**
     * Delete the "id" etapaProducao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
