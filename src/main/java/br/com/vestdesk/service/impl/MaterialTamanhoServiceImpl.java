package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.MaterialTamanhoService;
import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.repository.MaterialTamanhoRepository;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
import br.com.vestdesk.service.mapper.MaterialTamanhoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MaterialTamanho.
 */
@Service
@Transactional
public class MaterialTamanhoServiceImpl implements MaterialTamanhoService {

    private final Logger log = LoggerFactory.getLogger(MaterialTamanhoServiceImpl.class);

    private final MaterialTamanhoRepository materialTamanhoRepository;

    private final MaterialTamanhoMapper materialTamanhoMapper;

    public MaterialTamanhoServiceImpl(MaterialTamanhoRepository materialTamanhoRepository, MaterialTamanhoMapper materialTamanhoMapper) {
        this.materialTamanhoRepository = materialTamanhoRepository;
        this.materialTamanhoMapper = materialTamanhoMapper;
    }

    /**
     * Save a materialTamanho.
     *
     * @param materialTamanhoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MaterialTamanhoDTO save(MaterialTamanhoDTO materialTamanhoDTO) {
        log.debug("Request to save MaterialTamanho : {}", materialTamanhoDTO);
        MaterialTamanho materialTamanho = materialTamanhoMapper.toEntity(materialTamanhoDTO);
        materialTamanho = materialTamanhoRepository.save(materialTamanho);
        return materialTamanhoMapper.toDto(materialTamanho);
    }

    /**
     * Get all the materialTamanhos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MaterialTamanhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MaterialTamanhos");
        return materialTamanhoRepository.findAll(pageable)
            .map(materialTamanhoMapper::toDto);
    }

    /**
     * Get one materialTamanho by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MaterialTamanhoDTO findOne(Long id) {
        log.debug("Request to get MaterialTamanho : {}", id);
        MaterialTamanho materialTamanho = materialTamanhoRepository.findOne(id);
        return materialTamanhoMapper.toDto(materialTamanho);
    }

    /**
     * Delete the materialTamanho by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialTamanho : {}", id);
        materialTamanhoRepository.delete(id);
    }
}
