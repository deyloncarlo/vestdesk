package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.repository.LayoutRepository;
import br.com.vestdesk.service.dto.LayoutDTO;
import br.com.vestdesk.service.mapper.LayoutMapper;

/**
 * Service Interface for managing Layout.
 */
@Service
@Transactional
public class LayoutService
{

	private final LayoutRepository layoutRepository;

	private final LayoutMapper layoutMapper;

	public LayoutService(LayoutRepository layoutRepository, LayoutMapper layoutMapper)
	{
		this.layoutRepository = layoutRepository;
		this.layoutMapper = layoutMapper;
	}

	/**
	 * Save a layout.
	 *
	 * @param layoutDTO the entity to save
	 * @return the persisted entity
	 */
	public LayoutDTO save(LayoutDTO layoutDTO)
	{
		Layout layout = this.layoutMapper.toEntity(layoutDTO);
		layout = this.layoutRepository.save(layout);
		return this.layoutMapper.toDto(layout);
	}

	/**
	 * Get all the layouts.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<LayoutDTO> findAll(Pageable pageable)
	{
		return this.layoutRepository.findAll(pageable).map(this.layoutMapper::toDto);
	}

	/**
	 * Get the "id" layout.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public LayoutDTO findOne(Long id)
	{
		Layout layout = this.layoutRepository.findOne(id);
		return this.layoutMapper.toDto(layout);
	}

	/**
	 * Delete the "id" layout.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.layoutRepository.delete(id);
	}
}
