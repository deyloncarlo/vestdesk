package br.com.vestdesk.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.vestdesk.service.ClienteService;
import br.com.vestdesk.service.dto.ClienteDTO;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Cliente.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource
{

	private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

	private static final String ENTITY_NAME = "cliente";

	private final ClienteService clienteService;

	public ClienteResource(ClienteService clienteService)
	{
		this.clienteService = clienteService;
	}

	/**
	 * POST /clientes : Create a new cliente.
	 *
	 * @param clienteDTO the clienteDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new clienteDTO, or with status 400 (Bad Request) if the cliente has
	 * already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/clientes")
	@Timed
	public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException
	{
		this.log.debug("REST request to save Cliente : {}", clienteDTO);
		if (clienteDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ClienteDTO result = this.clienteService.save(clienteDTO);
		return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /clientes : Updates an existing cliente.
	 *
	 * @param clienteDTO the clienteDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * clienteDTO, or with status 400 (Bad Request) if the clienteDTO is not
	 * valid, or with status 500 (Internal Server Error) if the clienteDTO
	 * couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/clientes")
	@Timed
	public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException
	{
		this.log.debug("REST request to update Cliente : {}", clienteDTO);
		if (clienteDTO.getId() == null)
		{
			return createCliente(clienteDTO);
		}
		ClienteDTO result = this.clienteService.save(clienteDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteDTO.getId().toString())).body(result);
	}

	/**
	 * GET /clientes : get all the clientes.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of clientes
	 * in body
	 */
	@GetMapping("/clientes")
	@Timed
	public ResponseEntity<List<ClienteDTO>> getAllClientes(Pageable pageable,
			@RequestParam(name = "nome", required = false) String nome)
	{
		this.log.debug("REST request to get a page of Clientes");
		Page<ClienteDTO> page = this.clienteService.findAll(pageable, nome);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientes");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /clientes/:id : get the "id" cliente.
	 *
	 * @param id the id of the clienteDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * clienteDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/clientes/{id}")
	@Timed
	public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id)
	{
		this.log.debug("REST request to get Cliente : {}", id);
		ClienteDTO clienteDTO = this.clienteService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clienteDTO));
	}

	/**
	 * DELETE /clientes/:id : delete the "id" cliente.
	 *
	 * @param id the id of the clienteDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/clientes/{id}")
	@Timed
	public ResponseEntity<Void> deleteCliente(@PathVariable Long id)
	{
		this.log.debug("REST request to delete Cliente : {}", id);
		this.clienteService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
