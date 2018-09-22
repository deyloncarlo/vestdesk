package br.com.vestdesk.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import br.com.vestdesk.service.ConfiguracaoProdutoService;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ConfiguracaoProduto.
 */
@RestController
@RequestMapping("/api")
public class ConfiguracaoProdutoResource
{

	private final Logger log = LoggerFactory.getLogger(ConfiguracaoProdutoResource.class);

	private static final String ENTITY_NAME = "configuracaoProduto";

	private final ConfiguracaoProdutoService configuracaoProdutoService;

	public ConfiguracaoProdutoResource(ConfiguracaoProdutoService configuracaoProdutoService)
	{
		this.configuracaoProdutoService = configuracaoProdutoService;
	}

	/**
	 * POST /configuracao-produtos : Create a new configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the configuracaoProdutoDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new configuracaoProdutoDTO, or with status 400 (Bad Request) if the
	 * configuracaoProduto has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/configuracao-produtos")
	@Timed
	public ResponseEntity<ConfiguracaoProdutoDTO> createConfiguracaoProduto(
			@Valid @RequestBody ConfiguracaoProdutoDTO configuracaoProdutoDTO) throws URISyntaxException
	{
		this.log.debug("REST request to save ConfiguracaoProduto : {}", configuracaoProdutoDTO);
		if (configuracaoProdutoDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new configuracaoProduto cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		ConfiguracaoProdutoDTO result = this.configuracaoProdutoService.save(configuracaoProdutoDTO);
		return ResponseEntity.created(new URI("/api/configuracao-produtos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /configuracao-produtos : Updates an existing configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the configuracaoProdutoDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * configuracaoProdutoDTO, or with status 400 (Bad Request) if the
	 * configuracaoProdutoDTO is not valid, or with status 500 (Internal Server
	 * Error) if the configuracaoProdutoDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/configuracao-produtos")
	@Timed
	public ResponseEntity<ConfiguracaoProdutoDTO> updateConfiguracaoProduto(
			@Valid @RequestBody ConfiguracaoProdutoDTO configuracaoProdutoDTO) throws URISyntaxException
	{
		this.log.debug("REST request to update ConfiguracaoProduto : {}", configuracaoProdutoDTO);
		if (configuracaoProdutoDTO.getId() == null)
		{
			return createConfiguracaoProduto(configuracaoProdutoDTO);
		}
		ConfiguracaoProdutoDTO result = this.configuracaoProdutoService.save(configuracaoProdutoDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configuracaoProdutoDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /configuracao-produtos : get all the configuracaoProdutos.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * configuracaoProdutos in body
	 */
	@GetMapping("/configuracao-produtos")
	@Timed
	public ResponseEntity<List<ConfiguracaoProdutoDTO>> getAllConfiguracaoProdutos(Pageable pageable,
			@RequestParam(name = "idModeloVestuario", required = false) Long idModeloVestuario)
	{
		this.log.debug("REST request to get a page of ConfiguracaoProdutos");
		Page<ConfiguracaoProdutoDTO> page = new PageImpl<ConfiguracaoProdutoDTO>(
				this.configuracaoProdutoService.filtrar(idModeloVestuario));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/configuracao-produtos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /configuracao-produtos/:id : get the "id" configuracaoProduto.
	 *
	 * @param id the id of the configuracaoProdutoDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * configuracaoProdutoDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/configuracao-produtos/{id}")
	@Timed
	public ResponseEntity<ConfiguracaoProdutoDTO> getConfiguracaoProduto(@PathVariable Long id)
	{
		this.log.debug("REST request to get ConfiguracaoProduto : {}", id);
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(configuracaoProdutoDTO));
	}

	/**
	 * DELETE /configuracao-produtos/:id : delete the "id" configuracaoProduto.
	 *
	 * @param id the id of the configuracaoProdutoDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/configuracao-produtos/{id}")
	@Timed
	public ResponseEntity<Void> deleteConfiguracaoProduto(@PathVariable Long id)
	{
		this.log.debug("REST request to delete ConfiguracaoProduto : {}", id);
		this.configuracaoProdutoService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
