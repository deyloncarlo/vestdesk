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

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.domain.enumeration.Tamanho;
import br.com.vestdesk.service.CorService;
import br.com.vestdesk.service.ProdutoService;
import br.com.vestdesk.service.dto.ProdutoDTO;
import br.com.vestdesk.service.dto.ProdutoMinDTO;
import br.com.vestdesk.service.mapper.ProdutoMapper;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Produto.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource
{

	private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

	private static final String ENTITY_NAME = "produto";

	private final ProdutoService produtoService;

	private final CorService corService;

	private final ProdutoMapper produtoMapper;

	public ProdutoResource(ProdutoService produtoService, ProdutoMapper produtoMapper, CorService corService)
	{
		this.produtoService = produtoService;
		this.produtoMapper = produtoMapper;
		this.corService = corService;
	}

	/**
	 * POST /produtos : Create a new produto.
	 *
	 * @param produtoDTO the produtoDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new produtoDTO, or with status 400 (Bad Request) if the produto has
	 * already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/produtos")
	@Timed
	public ResponseEntity<ProdutoDTO> createProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException
	{
		this.log.debug("REST request to save Produto : {}", produtoDTO);
		if (produtoDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new produto cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ProdutoDTO result = this.produtoService.save(produtoDTO);
		return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /produtos : Updates an existing produto.
	 *
	 * @param produtoDTO the produtoDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * produtoDTO, or with status 400 (Bad Request) if the produtoDTO is not
	 * valid, or with status 500 (Internal Server Error) if the produtoDTO
	 * couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/produtos")
	@Timed
	public ResponseEntity<ProdutoDTO> updateProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException
	{
		this.log.debug("REST request to update Produto : {}", produtoDTO);
		if (produtoDTO.getId() == null)
		{
			return createProduto(produtoDTO);
		}
		ProdutoDTO result = this.produtoService.save(produtoDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produtoDTO.getId().toString())).body(result);
	}

	/**
	 * GET /produtos : get all the produtos.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of produtos
	 * in body
	 */
	@GetMapping("/produtos")
	@Timed
	public ResponseEntity<List<ProdutoDTO>> getAllProdutos(Pageable pageable,
			@RequestParam(name = "descricao", required = false) String descricao)
	{
		this.log.debug("REST request to get a page of Produtos");
		Page<ProdutoDTO> page = this.produtoService.findAll(pageable, descricao);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produtos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /produtos/:id : get the "id" produto.
	 *
	 * @param id the id of the produtoDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * produtoDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/produtos/{id}")
	@Timed
	public ResponseEntity<ProdutoDTO> getProduto(@PathVariable Long id)
	{
		this.log.debug("REST request to get Produto : {}", id);
		ProdutoDTO produtoDTO = this.produtoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produtoDTO));
	}

	/**
	 * DELETE /produtos/:id : delete the "id" produto.
	 *
	 * @param id the id of the produtoDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/produtos/{id}")
	@Timed
	public ResponseEntity<Void> deleteProduto(@PathVariable Long id)
	{
		this.log.debug("REST request to delete Produto : {}", id);
		this.produtoService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@GetMapping("/produtos/obterProduto")
	@Timed
	public ResponseEntity<ProdutoMinDTO> obterProduto(@RequestParam(name = "modelo", required = false) Modelo modelo,
			@RequestParam(name = "corId", required = false) Long corId,
			@RequestParam(name = "tamanho", required = false) Tamanho tamanho)
	{
		Cor cor = null;
		if (corId != null)
		{
			cor = this.corService.getById(corId);
		}
		Produto produtoEncontrado = this.produtoService.obterPeloModeloTamanhoCor(modelo, tamanho, cor);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(this.produtoMapper.toMinDto(produtoEncontrado)));
	}

}
