package br.com.vestdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.repository.CorRepository;
import br.com.vestdesk.service.dto.CorDTO;
import br.com.vestdesk.service.mapper.CorMapper;

/**
 * Service Interface for managing Cor.
 */
@Service
@Transactional
public class CorService
{

	private final Logger log = LoggerFactory.getLogger(CorService.class);

	private final CorRepository corRepository;

	private final CorMapper corMapper;

	public CorService(CorRepository corRepository, CorMapper corMapper)
	{
		this.corRepository = corRepository;
		this.corMapper = corMapper;
	}

	/**
	 * Save a cor.
	 *
	 * @param corDTO the entity to save
	 * @return the persisted entity
	 */
	public CorDTO save(CorDTO corDTO)
	{
		this.log.debug("Request to save Cor : {}", corDTO);
		Cor cor = this.corMapper.toEntity(corDTO);
		cor = this.corRepository.save(cor);
		return this.corMapper.toDto(cor);
	}

	/**
	 * Get all the cors.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<CorDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all Cors");
		return this.corRepository.findAll(pageable).map(this.corMapper::toDto);
	}

	public Cor getById(Long id)
	{
		return this.corRepository.findOne(id);
	}

	/**
	 * Get one cor by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public CorDTO findOne(Long id)
	{
		this.log.debug("Request to get Cor : {}", id);
		Cor cor = this.corRepository.findOne(id);
		return this.corMapper.toDto(cor);
	}

	/**
	 * Delete the cor by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		Cor corEncontrada = getById(id);
		if (corEncontrada.getListaMaterial().isEmpty() && corEncontrada.getListaProduto().isEmpty())
		{
			this.corRepository.delete(id);
		}
		else if (!corEncontrada.getListaMaterial().isEmpty())
		{
			throw new RuntimeException("error.cor.existeMateriasReferenciandoEstaCor");
		}
		else if (!corEncontrada.getListaProduto().isEmpty())
		{
			throw new RuntimeException("error.cor.existeProdutosReferenciandoEstaCor");
		}
	}
}
