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
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.vestdesk.service.ModeloVestuarioService;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ModeloVestuario.
 */
@RestController
@RequestMapping("/api")
public class ModeloVestuarioResource
{

	private final Logger log = LoggerFactory.getLogger(ModeloVestuarioResource.class);

	private static final String ENTITY_NAME = "modeloVestuario";

	private final ModeloVestuarioService modeloVestuarioService;

	public ModeloVestuarioResource(ModeloVestuarioService modeloVestuarioService)
	{
		this.modeloVestuarioService = modeloVestuarioService;
	}

	/**
	 * POST /modelo-vestuarios : Create a new modeloVestuario.
	 *
	 * @param modeloVestuarioDTO the modeloVestuarioDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new modeloVestuarioDTO, or with status 400 (Bad Request) if the
	 * modeloVestuario has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/modelo-vestuarios")
	@Timed
	public ResponseEntity<ModeloVestuarioDTO> createModeloVestuario(
			@Valid @RequestBody ModeloVestuarioDTO modeloVestuarioDTO) throws URISyntaxException
	{
		this.log.debug("REST request to save ModeloVestuario : {}", modeloVestuarioDTO);
		if (modeloVestuarioDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new modeloVestuario cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		ModeloVestuarioDTO result = this.modeloVestuarioService.save(modeloVestuarioDTO);
		return ResponseEntity.created(new URI("/api/modelo-vestuarios/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /modelo-vestuarios : Updates an existing modeloVestuario.
	 *
	 * @param modeloVestuarioDTO the modeloVestuarioDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * modeloVestuarioDTO, or with status 400 (Bad Request) if the
	 * modeloVestuarioDTO is not valid, or with status 500 (Internal Server
	 * Error) if the modeloVestuarioDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/modelo-vestuarios")
	@Timed
	public ResponseEntity<ModeloVestuarioDTO> updateModeloVestuario(
			@Valid @RequestBody ModeloVestuarioDTO modeloVestuarioDTO) throws URISyntaxException
	{
		this.log.debug("REST request to update ModeloVestuario : {}", modeloVestuarioDTO);
		if (modeloVestuarioDTO.getId() == null)
		{
			return createModeloVestuario(modeloVestuarioDTO);
		}
		ModeloVestuarioDTO result = this.modeloVestuarioService.save(modeloVestuarioDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modeloVestuarioDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /modelo-vestuarios : get all the modeloVestuarios.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * modeloVestuarios in body
	 */
	@GetMapping("/modelo-vestuarios")
	@Timed
	public ResponseEntity<List<ModeloVestuarioDTO>> getAllModeloVestuarios(Pageable pageable)
	{
		this.log.debug("REST request to get a page of ModeloVestuarios");
		Page<ModeloVestuarioDTO> page = this.modeloVestuarioService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modelo-vestuarios");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /modelo-vestuarios/:id : get the "id" modeloVestuario.
	 *
	 * @param id the id of the modeloVestuarioDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * modeloVestuarioDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/modelo-vestuarios/{id}")
	@Timed
	public ResponseEntity<ModeloVestuarioDTO> getModeloVestuario(@PathVariable Long id)
	{
		this.log.debug("REST request to get ModeloVestuario : {}", id);
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modeloVestuarioDTO));
	}

	/**
	 * DELETE /modelo-vestuarios/:id : delete the "id" modeloVestuario.
	 *
	 * @param id the id of the modeloVestuarioDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/modelo-vestuarios/{id}")
	@Timed
	public ResponseEntity<Void> deleteModeloVestuario(@PathVariable Long id)
	{
		this.log.debug("REST request to delete ModeloVestuario : {}", id);
		this.modeloVestuarioService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
