package br.com.vestdesk.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

	private EntityManager em;

	public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, EntityManager em)
	{
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
		this.em = em;
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
	 * @param nome
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable, String nome)
	{

		if (nome == null)
		{
			nome = "";
		}
		Query query = this.em.createQuery("SELECT cliente FROM Cliente cliente WHERE nome LIKE :nomeCliente");
		query.setParameter("nomeCliente", "%" + nome + "%");

		List<Cliente> listaCliente = query.getResultList();
		Page<Cliente> page = new PageImpl<>(listaCliente, pageable, listaCliente.size());

		this.log.debug("Request to get all Clientes");
		return page.map(this.clienteMapper::toDto);
		// this.clienteRepository.findAll(pageable, ).map();
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
