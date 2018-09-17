package br.com.vestdesk.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.repository.MaterialTamanhoRepository;
import br.com.vestdesk.service.MaterialTamanhoService;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
import br.com.vestdesk.service.mapper.MaterialTamanhoMapper;

/**
 * Service Implementation for managing MaterialTamanho.
 */
@Service
@Transactional
public class MaterialTamanhoServiceImpl implements MaterialTamanhoService
{

	private final Logger log = LoggerFactory.getLogger(MaterialTamanhoServiceImpl.class);

	private final MaterialTamanhoRepository materialTamanhoRepository;

	private final MaterialTamanhoMapper materialTamanhoMapper;

	public MaterialTamanhoServiceImpl(MaterialTamanhoRepository materialTamanhoRepository,
			MaterialTamanhoMapper materialTamanhoMapper)
	{
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
	public MaterialTamanhoDTO save(MaterialTamanhoDTO materialTamanhoDTO)
	{
		this.log.debug("Request to save MaterialTamanho : {}", materialTamanhoDTO);
		MaterialTamanho materialTamanho = this.materialTamanhoMapper.toEntity(materialTamanhoDTO);
		materialTamanho = this.materialTamanhoRepository.save(materialTamanho);
		return this.materialTamanhoMapper.toDto(materialTamanho);
	}

	/**
	 * Get all the materialTamanhos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MaterialTamanhoDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all MaterialTamanhos");
		return this.materialTamanhoRepository.findAll(pageable).map(this.materialTamanhoMapper::toDto);
	}

	/**
	 * Get one materialTamanho by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public MaterialTamanhoDTO findOne(Long id)
	{
		this.log.debug("Request to get MaterialTamanho : {}", id);
		MaterialTamanho materialTamanho = this.materialTamanhoRepository.findOne(id);
		return this.materialTamanhoMapper.toDto(materialTamanho);
	}

	/**
	 * Delete the materialTamanho by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id)
	{
		this.log.debug("Request to delete MaterialTamanho : {}", id);
		this.materialTamanhoRepository.delete(id);
	}

	@Override
	public Set<MaterialTamanho> save(Set<MaterialTamanho> listaMaterialTamanho, ConfiguracaoProduto configuracaoProduto)
	{
		for (MaterialTamanho materialTamanho : listaMaterialTamanho)
		{
			materialTamanho.setConfiguracaoProduto(configuracaoProduto);
			this.materialTamanhoRepository.save(materialTamanho);
		}
		return listaMaterialTamanho;
	}

	@Override
	public void delete(Set<MaterialTamanho> lsitaMaterialTamanho)
	{
		for (MaterialTamanho materialTamanho : lsitaMaterialTamanho)
		{
			this.materialTamanhoRepository.delete(materialTamanho);
		}
	}
}
