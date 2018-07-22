package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.LayoutService;
import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.repository.LayoutRepository;
import br.com.vestdesk.service.dto.LayoutDTO;
import br.com.vestdesk.service.mapper.LayoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Layout.
 */
@Service
@Transactional
public class LayoutServiceImpl implements LayoutService {

    private final Logger log = LoggerFactory.getLogger(LayoutServiceImpl.class);

    private final LayoutRepository layoutRepository;

    private final LayoutMapper layoutMapper;

    public LayoutServiceImpl(LayoutRepository layoutRepository, LayoutMapper layoutMapper) {
        this.layoutRepository = layoutRepository;
        this.layoutMapper = layoutMapper;
    }

    /**
     * Save a layout.
     *
     * @param layoutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LayoutDTO save(LayoutDTO layoutDTO) {
        log.debug("Request to save Layout : {}", layoutDTO);
        Layout layout = layoutMapper.toEntity(layoutDTO);
        layout = layoutRepository.save(layout);
        return layoutMapper.toDto(layout);
    }

    /**
     * Get all the layouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LayoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Layouts");
        return layoutRepository.findAll(pageable)
            .map(layoutMapper::toDto);
    }

    /**
     * Get one layout by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LayoutDTO findOne(Long id) {
        log.debug("Request to get Layout : {}", id);
        Layout layout = layoutRepository.findOne(id);
        return layoutMapper.toDto(layout);
    }

    /**
     * Delete the layout by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Layout : {}", id);
        layoutRepository.delete(id);
    }
}
