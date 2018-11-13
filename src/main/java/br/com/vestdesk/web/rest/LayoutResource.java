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

import br.com.vestdesk.service.LayoutService;
import br.com.vestdesk.service.dto.LayoutDTO;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Layout.
 */
@RestController
@RequestMapping("/api")
public class LayoutResource
{

	private final Logger log = LoggerFactory.getLogger(LayoutResource.class);

	private static final String ENTITY_NAME = "layout";

	private final LayoutService layoutService;

	public LayoutResource(LayoutService layoutService)
	{
		this.layoutService = layoutService;
	}

	/**
	 * POST /layouts : Create a new layout.
	 *
	 * @param layoutDTO the layoutDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new layoutDTO, or with status 400 (Bad Request) if the layout has already
	 * an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/layouts")
	@Timed
	public ResponseEntity<LayoutDTO> createLayout(@Valid @RequestBody LayoutDTO layoutDTO) throws URISyntaxException
	{
		this.log.debug("REST request to save Layout : {}", layoutDTO);
		if (layoutDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new layout cannot already have an ID", ENTITY_NAME, "idexists");
		}
		LayoutDTO result = this.layoutService.save(layoutDTO);
		return ResponseEntity.created(new URI("/api/layouts/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /layouts : Updates an existing layout.
	 *
	 * @param layoutDTO the layoutDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * layoutDTO, or with status 400 (Bad Request) if the layoutDTO is not
	 * valid, or with status 500 (Internal Server Error) if the layoutDTO
	 * couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/layouts")
	@Timed
	public ResponseEntity<LayoutDTO> updateLayout(@Valid @RequestBody LayoutDTO layoutDTO) throws URISyntaxException
	{
		this.log.debug("REST request to update Layout : {}", layoutDTO);
		if (layoutDTO.getId() == null)
		{
			return createLayout(layoutDTO);
		}
		LayoutDTO result = this.layoutService.save(layoutDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, layoutDTO.getId().toString())).body(result);
	}

	/**
	 * GET /layouts : get all the layouts.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of layouts
	 * in body
	 */
	@GetMapping("/layouts")
	@Timed
	public ResponseEntity<List<LayoutDTO>> getAllLayouts(Pageable pageable,
			@RequestParam(name = "nome", required = false) String nome)
	{
		this.log.debug("REST request to get a page of Layouts");
		Page<LayoutDTO> page = this.layoutService.findAll(pageable, nome);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/layouts");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /layouts/:id : get the "id" layout.
	 *
	 * @param id the id of the layoutDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * layoutDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/layouts/{id}")
	@Timed
	public ResponseEntity<LayoutDTO> getLayout(@PathVariable Long id)
	{
		this.log.debug("REST request to get Layout : {}", id);
		LayoutDTO layoutDTO = this.layoutService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(layoutDTO));
	}

	/**
	 * DELETE /layouts/:id : delete the "id" layout.
	 *
	 * @param id the id of the layoutDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/layouts/{id}")
	@Timed
	public ResponseEntity<Void> deleteLayout(@PathVariable Long id)
	{
		this.log.debug("REST request to delete Layout : {}", id);
		this.layoutService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
