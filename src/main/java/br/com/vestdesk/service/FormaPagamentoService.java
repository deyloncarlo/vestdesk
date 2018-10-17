package br.com.vestdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.repository.FormaPagamentoRepository;
import br.com.vestdesk.service.dto.FormaPagamentoDTO;
import br.com.vestdesk.service.mapper.FormaPagamentoMapper;

/**
 * Service for managing FormaPagamento.
 */
@Service
@Transactional
public class FormaPagamentoService
{

	private final Logger log = LoggerFactory.getLogger(CorService.class);

	private final FormaPagamentoRepository formaPagamentoRepository;

	private final FormaPagamentoMapper formaPagamentoMapper;

	public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository,
			FormaPagamentoMapper formaPagamentoMapper)
	{
		this.formaPagamentoMapper = formaPagamentoMapper;
		this.formaPagamentoRepository = formaPagamentoRepository;
	}

	/**
	 * Save a formaPagamento.
	 *
	 * @param formaPagamentoDTO the entity to save
	 * @return the persisted entity
	 */
	public FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO)
	{

		return null;
	}

	/**
	 * Get all the formaPagamentos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	public Page<FormaPagamentoDTO> findAll(Pageable pageable)
	{
		return null;
	}

	/**
	 * Get the "id" formaPagamento.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public FormaPagamentoDTO findOne(Long id)
	{
		return null;
	}

	/**
	 * Delete the "id" formaPagamento.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}
}
