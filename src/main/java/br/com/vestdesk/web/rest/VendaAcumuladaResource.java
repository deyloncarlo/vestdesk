package br.com.vestdesk.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.domain.enumeration.StatusVendaAcumulada;
import br.com.vestdesk.repository.VendaAcumuladaRepository;
import br.com.vestdesk.service.VendaAcumuladaService;
import br.com.vestdesk.service.dto.VendaAcumuladaDTO;
import br.com.vestdesk.service.mapper.VendaAcumuladaMapper;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing VendaAcumulada.
 */
@RestController
@RequestMapping("/api")
public class VendaAcumuladaResource
{

	private final Logger log = LoggerFactory.getLogger(VendaAcumuladaResource.class);

	private static final String ENTITY_NAME = "vendaAcumulada";

	private final VendaAcumuladaRepository vendaAcumuladaRepository;

	private final VendaAcumuladaService vendaAcumuladaService;

	private final VendaAcumuladaMapper vendaAcumuladaMapper;

	public VendaAcumuladaResource(VendaAcumuladaRepository vendaAcumuladaRepository,
			VendaAcumuladaMapper vendaAcumuladaMapper, VendaAcumuladaService vendaAcumuladaService)
	{
		this.vendaAcumuladaRepository = vendaAcumuladaRepository;
		this.vendaAcumuladaMapper = vendaAcumuladaMapper;
		this.vendaAcumuladaService = vendaAcumuladaService;

	}

	/**
	 * POST /venda-acumuladas : Create a new vendaAcumulada.
	 *
	 * @param vendaAcumuladaDTO the vendaAcumuladaDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new vendaAcumuladaDTO, or with status 400 (Bad Request) if the
	 * vendaAcumulada has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/venda-acumuladas")
	@Timed
	public ResponseEntity<VendaAcumuladaDTO> createVendaAcumulada(@RequestBody VendaAcumuladaDTO vendaAcumuladaDTO)
			throws URISyntaxException
	{
		this.log.debug("REST request to save VendaAcumulada : {}", vendaAcumuladaDTO);
		if (vendaAcumuladaDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new vendaAcumulada cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		VendaAcumulada vendaAcumulada = this.vendaAcumuladaMapper.toEntity(vendaAcumuladaDTO);
		vendaAcumulada = this.vendaAcumuladaRepository.save(vendaAcumulada);
		VendaAcumuladaDTO result = this.vendaAcumuladaMapper.toDto(vendaAcumulada);
		return ResponseEntity.created(new URI("/api/venda-acumuladas/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /venda-acumuladas : Updates an existing vendaAcumulada.
	 *
	 * @param vendaAcumuladaDTO the vendaAcumuladaDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * vendaAcumuladaDTO, or with status 400 (Bad Request) if the
	 * vendaAcumuladaDTO is not valid, or with status 500 (Internal Server
	 * Error) if the vendaAcumuladaDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/venda-acumuladas")
	@Timed
	public ResponseEntity<VendaAcumuladaDTO> updateVendaAcumulada(@RequestBody VendaAcumuladaDTO vendaAcumuladaDTO)
			throws URISyntaxException
	{
		this.log.debug("REST request to update VendaAcumulada : {}", vendaAcumuladaDTO);
		if (vendaAcumuladaDTO.getId() == null)
		{
			return createVendaAcumulada(vendaAcumuladaDTO);
		}
		VendaAcumulada vendaAcumulada = this.vendaAcumuladaMapper.toEntity(vendaAcumuladaDTO);
		vendaAcumulada = this.vendaAcumuladaRepository.save(vendaAcumulada);
		VendaAcumuladaDTO result = this.vendaAcumuladaMapper.toDto(vendaAcumulada);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendaAcumuladaDTO.getId().toString()))
				.body(result);
	}

	@PostMapping("/venda-acumuladas/produzir")
	@Timed
	public ResponseEntity<VendaAcumuladaDTO> produzir(@RequestBody VendaAcumuladaDTO vendaAcumuladaDTO)

	{
		this.log.debug("REST request to update VendaAcumulada : {}", vendaAcumuladaDTO);
		VendaAcumulada vendaAcumulada = this.vendaAcumuladaService.produzir(vendaAcumuladaDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "")).body(null);
	}

	@PostMapping("/venda-acumuladas/concluir")
	@Timed
	public ResponseEntity<VendaAcumuladaDTO> concluir(@RequestBody VendaAcumuladaDTO vendaAcumuladaDTO)

	{
		VendaAcumulada vendaAcumulada = this.vendaAcumuladaService.concluir(vendaAcumuladaDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "")).body(null);
	}

	/**
	 * GET /venda-acumuladas : get all the vendaAcumuladas.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * vendaAcumuladas in body
	 */
	@GetMapping("/venda-acumuladas")
	@Timed
	public ResponseEntity<List<VendaAcumuladaDTO>> getAllVendaAcumuladas(Pageable pageable,
			@RequestParam(name = "status", required = false) StatusVendaAcumulada status)
	{
		this.log.debug("REST request to get a page of VendaAcumuladas");
		Page<VendaAcumuladaDTO> page = this.vendaAcumuladaService.findAll(pageable, status);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/venda-acumuladas");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /venda-acumuladas/:id : get the "id" vendaAcumulada.
	 *
	 * @param id the id of the vendaAcumuladaDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * vendaAcumuladaDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/venda-acumuladas/{id}")
	@Timed
	public ResponseEntity<VendaAcumuladaDTO> getVendaAcumulada(@PathVariable Long id)
	{
		this.log.debug("REST request to get VendaAcumulada : {}", id);
		VendaAcumulada vendaAcumulada = this.vendaAcumuladaRepository.findOne(id);
		VendaAcumuladaDTO vendaAcumuladaDTO = this.vendaAcumuladaMapper.toDto(vendaAcumulada);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vendaAcumuladaDTO));
	}

	/**
	 * DELETE /venda-acumuladas/:id : delete the "id" vendaAcumulada.
	 *
	 * @param id the id of the vendaAcumuladaDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/venda-acumuladas/{id}")
	@Timed
	public ResponseEntity<Void> deleteVendaAcumulada(@PathVariable Long id)
	{
		this.log.debug("REST request to delete VendaAcumulada : {}", id);
		this.vendaAcumuladaRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
