package br.com.vestdesk.service;

import br.com.vestdesk.service.dto.FormaPagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormaPagamento.
 */
public interface FormaPagamentoService {

    /**
     * Save a formaPagamento.
     *
     * @param formaPagamentoDTO the entity to save
     * @return the persisted entity
     */
    FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO);

    /**
     * Get all the formaPagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormaPagamentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" formaPagamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FormaPagamentoDTO findOne(Long id);

    /**
     * Delete the "id" formaPagamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
