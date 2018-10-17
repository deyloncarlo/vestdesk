package br.com.vestdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.EtapaProducao;
import br.com.vestdesk.repository.EtapaProducaoRepository;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;
import br.com.vestdesk.service.mapper.EtapaProducaoMapper;

/**
 * Service for managing EtapaProducao.
 */
@Service
@Transactional
public class EtapaProducaoService
{

	private final Logger log = LoggerFactory.getLogger(EtapaProducaoService.class);

	private final EtapaProducaoRepository etapaProducaoRepository;

	private final EtapaProducaoMapper etapaProducaoMapper;

	public EtapaProducaoService(EtapaProducaoRepository etapaProducaoRepository,
			EtapaProducaoMapper etapaProducaoMapper)
	{
		this.etapaProducaoRepository = etapaProducaoRepository;
		this.etapaProducaoMapper = etapaProducaoMapper;
	}

	/**
	 * Save a etapaProducao.
	 *
	 * @param etapaProducaoDTO the entity to save
	 * @return the persisted entity
	 */
	public EtapaProducaoDTO save(EtapaProducaoDTO etapaProducaoDTO)
	{
		EtapaProducao etapaProducao = this.etapaProducaoMapper.toEntity(etapaProducaoDTO);
		etapaProducao = this.etapaProducaoRepository.save(etapaProducao);
		return this.etapaProducaoMapper.toDto(etapaProducao);
	}

	/**
	 * Get all the etapaProducaos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<EtapaProducaoDTO> findAll(Pageable pageable)
	{
		return this.etapaProducaoRepository.findAll(pageable).map(this.etapaProducaoMapper::toDto);
	}

	/**
	 * Get the "id" etapaProducao.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public EtapaProducaoDTO findOne(Long id)
	{
		EtapaProducao etapaProducao = this.etapaProducaoRepository.findOne(id);
		return this.etapaProducaoMapper.toDto(etapaProducao);
	}

	/**
	 * Delete the "id" etapaProducao.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.etapaProducaoRepository.delete(id);
	}
}
