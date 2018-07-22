package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.ModeloVestuarioService;
import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.repository.ModeloVestuarioRepository;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;
import br.com.vestdesk.service.mapper.ModeloVestuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ModeloVestuario.
 */
@Service
@Transactional
public class ModeloVestuarioServiceImpl implements ModeloVestuarioService {

    private final Logger log = LoggerFactory.getLogger(ModeloVestuarioServiceImpl.class);

    private final ModeloVestuarioRepository modeloVestuarioRepository;

    private final ModeloVestuarioMapper modeloVestuarioMapper;

    public ModeloVestuarioServiceImpl(ModeloVestuarioRepository modeloVestuarioRepository, ModeloVestuarioMapper modeloVestuarioMapper) {
        this.modeloVestuarioRepository = modeloVestuarioRepository;
        this.modeloVestuarioMapper = modeloVestuarioMapper;
    }

    /**
     * Save a modeloVestuario.
     *
     * @param modeloVestuarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeloVestuarioDTO save(ModeloVestuarioDTO modeloVestuarioDTO) {
        log.debug("Request to save ModeloVestuario : {}", modeloVestuarioDTO);
        ModeloVestuario modeloVestuario = modeloVestuarioMapper.toEntity(modeloVestuarioDTO);
        modeloVestuario = modeloVestuarioRepository.save(modeloVestuario);
        return modeloVestuarioMapper.toDto(modeloVestuario);
    }

    /**
     * Get all the modeloVestuarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeloVestuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModeloVestuarios");
        return modeloVestuarioRepository.findAll(pageable)
            .map(modeloVestuarioMapper::toDto);
    }

    /**
     * Get one modeloVestuario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModeloVestuarioDTO findOne(Long id) {
        log.debug("Request to get ModeloVestuario : {}", id);
        ModeloVestuario modeloVestuario = modeloVestuarioRepository.findOne(id);
        return modeloVestuarioMapper.toDto(modeloVestuario);
    }

    /**
     * Delete the modeloVestuario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeloVestuario : {}", id);
        modeloVestuarioRepository.delete(id);
    }
}
