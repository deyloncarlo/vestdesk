package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.QuantidadeTamanhoService;
import br.com.vestdesk.domain.QuantidadeTamanho;
import br.com.vestdesk.repository.QuantidadeTamanhoRepository;
import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;
import br.com.vestdesk.service.mapper.QuantidadeTamanhoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing QuantidadeTamanho.
 */
@Service
@Transactional
public class QuantidadeTamanhoServiceImpl implements QuantidadeTamanhoService {

    private final Logger log = LoggerFactory.getLogger(QuantidadeTamanhoServiceImpl.class);

    private final QuantidadeTamanhoRepository quantidadeTamanhoRepository;

    private final QuantidadeTamanhoMapper quantidadeTamanhoMapper;

    public QuantidadeTamanhoServiceImpl(QuantidadeTamanhoRepository quantidadeTamanhoRepository, QuantidadeTamanhoMapper quantidadeTamanhoMapper) {
        this.quantidadeTamanhoRepository = quantidadeTamanhoRepository;
        this.quantidadeTamanhoMapper = quantidadeTamanhoMapper;
    }

    /**
     * Save a quantidadeTamanho.
     *
     * @param quantidadeTamanhoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuantidadeTamanhoDTO save(QuantidadeTamanhoDTO quantidadeTamanhoDTO) {
        log.debug("Request to save QuantidadeTamanho : {}", quantidadeTamanhoDTO);
        QuantidadeTamanho quantidadeTamanho = quantidadeTamanhoMapper.toEntity(quantidadeTamanhoDTO);
        quantidadeTamanho = quantidadeTamanhoRepository.save(quantidadeTamanho);
        return quantidadeTamanhoMapper.toDto(quantidadeTamanho);
    }

    /**
     * Get all the quantidadeTamanhos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuantidadeTamanhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuantidadeTamanhos");
        return quantidadeTamanhoRepository.findAll(pageable)
            .map(quantidadeTamanhoMapper::toDto);
    }

    /**
     * Get one quantidadeTamanho by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuantidadeTamanhoDTO findOne(Long id) {
        log.debug("Request to get QuantidadeTamanho : {}", id);
        QuantidadeTamanho quantidadeTamanho = quantidadeTamanhoRepository.findOne(id);
        return quantidadeTamanhoMapper.toDto(quantidadeTamanho);
    }

    /**
     * Delete the quantidadeTamanho by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuantidadeTamanho : {}", id);
        quantidadeTamanhoRepository.delete(id);
    }
}
