package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.FormaPagamentoService;
import br.com.vestdesk.domain.FormaPagamento;
import br.com.vestdesk.repository.FormaPagamentoRepository;
import br.com.vestdesk.service.dto.FormaPagamentoDTO;
import br.com.vestdesk.service.mapper.FormaPagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FormaPagamento.
 */
@Service
@Transactional
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final Logger log = LoggerFactory.getLogger(FormaPagamentoServiceImpl.class);

    private final FormaPagamentoRepository formaPagamentoRepository;

    private final FormaPagamentoMapper formaPagamentoMapper;

    public FormaPagamentoServiceImpl(FormaPagamentoRepository formaPagamentoRepository, FormaPagamentoMapper formaPagamentoMapper) {
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.formaPagamentoMapper = formaPagamentoMapper;
    }

    /**
     * Save a formaPagamento.
     *
     * @param formaPagamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO) {
        log.debug("Request to save FormaPagamento : {}", formaPagamentoDTO);
        FormaPagamento formaPagamento = formaPagamentoMapper.toEntity(formaPagamentoDTO);
        formaPagamento = formaPagamentoRepository.save(formaPagamento);
        return formaPagamentoMapper.toDto(formaPagamento);
    }

    /**
     * Get all the formaPagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormaPagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormaPagamentos");
        return formaPagamentoRepository.findAll(pageable)
            .map(formaPagamentoMapper::toDto);
    }

    /**
     * Get one formaPagamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormaPagamentoDTO findOne(Long id) {
        log.debug("Request to get FormaPagamento : {}", id);
        FormaPagamento formaPagamento = formaPagamentoRepository.findOne(id);
        return formaPagamentoMapper.toDto(formaPagamento);
    }

    /**
     * Delete the formaPagamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormaPagamento : {}", id);
        formaPagamentoRepository.delete(id);
    }
}
