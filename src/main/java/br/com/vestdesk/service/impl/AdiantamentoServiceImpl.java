package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.AdiantamentoService;
import br.com.vestdesk.domain.Adiantamento;
import br.com.vestdesk.repository.AdiantamentoRepository;
import br.com.vestdesk.service.dto.AdiantamentoDTO;
import br.com.vestdesk.service.mapper.AdiantamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Adiantamento.
 */
@Service
@Transactional
public class AdiantamentoServiceImpl implements AdiantamentoService {

    private final Logger log = LoggerFactory.getLogger(AdiantamentoServiceImpl.class);

    private final AdiantamentoRepository adiantamentoRepository;

    private final AdiantamentoMapper adiantamentoMapper;

    public AdiantamentoServiceImpl(AdiantamentoRepository adiantamentoRepository, AdiantamentoMapper adiantamentoMapper) {
        this.adiantamentoRepository = adiantamentoRepository;
        this.adiantamentoMapper = adiantamentoMapper;
    }

    /**
     * Save a adiantamento.
     *
     * @param adiantamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdiantamentoDTO save(AdiantamentoDTO adiantamentoDTO) {
        log.debug("Request to save Adiantamento : {}", adiantamentoDTO);
        Adiantamento adiantamento = adiantamentoMapper.toEntity(adiantamentoDTO);
        adiantamento = adiantamentoRepository.save(adiantamento);
        return adiantamentoMapper.toDto(adiantamento);
    }

    /**
     * Get all the adiantamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdiantamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adiantamentos");
        return adiantamentoRepository.findAll(pageable)
            .map(adiantamentoMapper::toDto);
    }

    /**
     * Get one adiantamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdiantamentoDTO findOne(Long id) {
        log.debug("Request to get Adiantamento : {}", id);
        Adiantamento adiantamento = adiantamentoRepository.findOne(id);
        return adiantamentoMapper.toDto(adiantamento);
    }

    /**
     * Delete the adiantamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adiantamento : {}", id);
        adiantamentoRepository.delete(id);
    }
}
