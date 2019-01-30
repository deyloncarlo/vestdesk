package br.com.vestdesk.web.rest;

import java.net.URI;
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

import br.com.vestdesk.service.PedidoService;
import br.com.vestdesk.service.dto.PedidoDTO;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Pedido.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource
{

	private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

	private static final String ENTITY_NAME = "pedido";

	private final PedidoService pedidoService;

	public PedidoResource(PedidoService pedidoService)
	{
		this.pedidoService = pedidoService;
	}

	/**
	 * POST /pedidos : Create a new pedido.
	 *
	 * @param pedidoDTO the pedidoDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new pedidoDTO, or with status 400 (Bad Request) if the pedido has already
	 * an ID
	 * @throws Exception
	 */
	@PostMapping("/pedidos")
	@Timed
	public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) throws Exception
	{
		this.log.debug("REST request to save Pedido : {}", pedidoDTO);
		if (pedidoDTO.getId() != null)
		{
			throw new BadRequestAlertException("A new pedido cannot already have an ID", ENTITY_NAME, "idexists");
		}
		PedidoDTO result = this.pedidoService.save(pedidoDTO);
		return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /pedidos : Updates an existing pedido.
	 *
	 * @param pedidoDTO the pedidoDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * pedidoDTO, or with status 400 (Bad Request) if the pedidoDTO is not
	 * valid, or with status 500 (Internal Server Error) if the pedidoDTO
	 * couldn't be updated
	 * @throws Exception
	 */
	@PutMapping("/pedidos")
	@Timed
	public ResponseEntity<PedidoDTO> updatePedido(@Valid @RequestBody PedidoDTO pedidoDTO) throws Exception
	{
		this.log.debug("REST request to update Pedido : {}", pedidoDTO);
		if (pedidoDTO.getId() == null)
		{
			return createPedido(pedidoDTO);
		}
		PedidoDTO result = this.pedidoService.save(pedidoDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pedidoDTO.getId().toString())).body(result);
	}

	/**
	 * GET /pedidos : get all the pedidos.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of pedidos
	 * in body
	 */
	@GetMapping("/pedidos")
	@Timed
	public ResponseEntity<List<PedidoDTO>> getAllPedidos(Pageable pageable,
			@RequestParam(name = "id", required = false) String id,
			@RequestParam(name = "statusPedido", required = false) String statusPedido,
			@RequestParam(name = "fechaEm10Dias", required = false) boolean fechaEm10Dias)
	{
		this.log.debug("REST request to get a page of Pedidos");
		Long v_id = null;
		if (id != null && !id.equals("null"))
		{
			v_id = Long.parseLong(id);
		}
		else
		{
			v_id = null;
		}

		if (statusPedido == null || statusPedido.equals("null"))
		{
			statusPedido = null;
		}

		Page<PedidoDTO> page = this.pedidoService.findAll(pageable, v_id, statusPedido, fechaEm10Dias);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pedidos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /pedidos/:id : get the "id" pedido.
	 *
	 * @param id the id of the pedidoDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * pedidoDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/pedidos/{id}")
	@Timed
	public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id)
	{
		this.log.debug("REST request to get Pedido : {}", id);
		PedidoDTO pedidoDTO = this.pedidoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pedidoDTO));
	}

	@GetMapping("/pedidos/quantidadePedidoStatusRascunho")
	@Timed
	public ResponseEntity<Long> obterQuantidadePedidoStatusRascunho()
	{
		Long quantidade = this.pedidoService.obterQuantidadePedidoStatusRascunho();
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantidade));
	}

	@GetMapping("/pedidos/obterQuantidadePedidoSeraoFechados10Dias")
	@Timed
	public ResponseEntity<Long> obterQuantidadePedidoSeraoFechados10Dias()
	{
		Long quantidade = this.pedidoService.obterQuantidadePedidoSeraoFechados10Dias();
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantidade));
	}

	/**
	 * DELETE /pedidos/:id : delete the "id" pedido.
	 *
	 * @param id the id of the pedidoDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/pedidos/{id}")
	@Timed
	public ResponseEntity<Void> deletePedido(@PathVariable Long id)
	{
		this.log.debug("REST request to delete Pedido : {}", id);
		this.pedidoService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
