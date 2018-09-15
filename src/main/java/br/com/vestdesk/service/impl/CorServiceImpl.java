package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.CorService;
import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.repository.CorRepository;
import br.com.vestdesk.service.dto.CorDTO;
import br.com.vestdesk.service.mapper.CorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cor.
 */
@Service
@Transactional
public class CorServiceImpl implements CorService {

    private final Logger log = LoggerFactory.getLogger(CorServiceImpl.class);

    private final CorRepository corRepository;

    private final CorMapper corMapper;

    public CorServiceImpl(CorRepository corRepository, CorMapper corMapper) {
        this.corRepository = corRepository;
        this.corMapper = corMapper;
    }

    /**
     * Save a cor.
     *
     * @param corDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorDTO save(CorDTO corDTO) {
        log.debug("Request to save Cor : {}", corDTO);
        Cor cor = corMapper.toEntity(corDTO);
        cor = corRepository.save(cor);
        return corMapper.toDto(cor);
    }

    /**
     * Get all the cors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cors");
        return corRepository.findAll(pageable)
            .map(corMapper::toDto);
    }

    /**
     * Get one cor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorDTO findOne(Long id) {
        log.debug("Request to get Cor : {}", id);
        Cor cor = corRepository.findOne(id);
        return corMapper.toDto(cor);
    }

    /**
     * Delete the cor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cor : {}", id);
        corRepository.delete(id);
    }
}
