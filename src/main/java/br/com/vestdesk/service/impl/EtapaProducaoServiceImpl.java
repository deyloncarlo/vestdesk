package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.EtapaProducaoService;
import br.com.vestdesk.domain.EtapaProducao;
import br.com.vestdesk.repository.EtapaProducaoRepository;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;
import br.com.vestdesk.service.mapper.EtapaProducaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EtapaProducao.
 */
@Service
@Transactional
public class EtapaProducaoServiceImpl implements EtapaProducaoService {

    private final Logger log = LoggerFactory.getLogger(EtapaProducaoServiceImpl.class);

    private final EtapaProducaoRepository etapaProducaoRepository;

    private final EtapaProducaoMapper etapaProducaoMapper;

    public EtapaProducaoServiceImpl(EtapaProducaoRepository etapaProducaoRepository, EtapaProducaoMapper etapaProducaoMapper) {
        this.etapaProducaoRepository = etapaProducaoRepository;
        this.etapaProducaoMapper = etapaProducaoMapper;
    }

    /**
     * Save a etapaProducao.
     *
     * @param etapaProducaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EtapaProducaoDTO save(EtapaProducaoDTO etapaProducaoDTO) {
        log.debug("Request to save EtapaProducao : {}", etapaProducaoDTO);
        EtapaProducao etapaProducao = etapaProducaoMapper.toEntity(etapaProducaoDTO);
        etapaProducao = etapaProducaoRepository.save(etapaProducao);
        return etapaProducaoMapper.toDto(etapaProducao);
    }

    /**
     * Get all the etapaProducaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtapaProducaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapaProducaos");
        return etapaProducaoRepository.findAll(pageable)
            .map(etapaProducaoMapper::toDto);
    }

    /**
     * Get one etapaProducao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EtapaProducaoDTO findOne(Long id) {
        log.debug("Request to get EtapaProducao : {}", id);
        EtapaProducao etapaProducao = etapaProducaoRepository.findOne(id);
        return etapaProducaoMapper.toDto(etapaProducao);
    }

    /**
     * Delete the etapaProducao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtapaProducao : {}", id);
        etapaProducaoRepository.delete(id);
    }
}
