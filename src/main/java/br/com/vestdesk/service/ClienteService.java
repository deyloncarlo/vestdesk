package br.com.vestdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Cliente;
import br.com.vestdesk.repository.ClienteRepository;
import br.com.vestdesk.service.dto.ClienteDTO;
import br.com.vestdesk.service.mapper.ClienteMapper;

/**
 * Service for managing Cliente.
 */
@Service
@Transactional
public class ClienteService
{

	private final Logger log = LoggerFactory.getLogger(ClienteService.class);

	private final ClienteRepository clienteRepository;

	private final ClienteMapper clienteMapper;

	public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper)
	{
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
	}

	/**
	 * Save a cliente.
	 *
	 * @param clienteDTO the entity to save
	 * @return the persisted entity
	 */
	public ClienteDTO save(ClienteDTO clienteDTO)
	{
		this.log.debug("Request to save Cliente : {}", clienteDTO);
		Cliente cliente = this.clienteMapper.toEntity(clienteDTO);
		cliente = this.clienteRepository.save(cliente);
		return this.clienteMapper.toDto(cliente);
	}

	/**
	 * Get all the clientes.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all Clientes");
		return this.clienteRepository.findAll(pageable).map(this.clienteMapper::toDto);
	}

	/**
	 * Get one cliente by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ClienteDTO findOne(Long id)
	{
		this.log.debug("Request to get Cliente : {}", id);
		Cliente cliente = this.clienteRepository.findOne(id);
		return this.clienteMapper.toDto(cliente);
	}

	/**
	 * Delete the cliente by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.log.debug("Request to delete Cliente : {}", id);
		this.clienteRepository.delete(id);
	}
}
