package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.PedidoItemService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.PedidoItemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PedidoItem.
 */
@RestController
@RequestMapping("/api")
public class PedidoItemResource {

    private final Logger log = LoggerFactory.getLogger(PedidoItemResource.class);

    private static final String ENTITY_NAME = "pedidoItem";

    private final PedidoItemService pedidoItemService;

    public PedidoItemResource(PedidoItemService pedidoItemService) {
        this.pedidoItemService = pedidoItemService;
    }

    /**
     * POST  /pedido-items : Create a new pedidoItem.
     *
     * @param pedidoItemDTO the pedidoItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pedidoItemDTO, or with status 400 (Bad Request) if the pedidoItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pedido-items")
    @Timed
    public ResponseEntity<PedidoItemDTO> createPedidoItem(@RequestBody PedidoItemDTO pedidoItemDTO) throws URISyntaxException {
        log.debug("REST request to save PedidoItem : {}", pedidoItemDTO);
        if (pedidoItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new pedidoItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PedidoItemDTO result = pedidoItemService.save(pedidoItemDTO);
        return ResponseEntity.created(new URI("/api/pedido-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pedido-items : Updates an existing pedidoItem.
     *
     * @param pedidoItemDTO the pedidoItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pedidoItemDTO,
     * or with status 400 (Bad Request) if the pedidoItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the pedidoItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pedido-items")
    @Timed
    public ResponseEntity<PedidoItemDTO> updatePedidoItem(@RequestBody PedidoItemDTO pedidoItemDTO) throws URISyntaxException {
        log.debug("REST request to update PedidoItem : {}", pedidoItemDTO);
        if (pedidoItemDTO.getId() == null) {
            return createPedidoItem(pedidoItemDTO);
        }
        PedidoItemDTO result = pedidoItemService.save(pedidoItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pedidoItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pedido-items : get all the pedidoItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pedidoItems in body
     */
    @GetMapping("/pedido-items")
    @Timed
    public ResponseEntity<List<PedidoItemDTO>> getAllPedidoItems(Pageable pageable) {
        log.debug("REST request to get a page of PedidoItems");
        Page<PedidoItemDTO> page = pedidoItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pedido-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pedido-items/:id : get the "id" pedidoItem.
     *
     * @param id the id of the pedidoItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pedidoItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pedido-items/{id}")
    @Timed
    public ResponseEntity<PedidoItemDTO> getPedidoItem(@PathVariable Long id) {
        log.debug("REST request to get PedidoItem : {}", id);
        PedidoItemDTO pedidoItemDTO = pedidoItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pedidoItemDTO));
    }

    /**
     * DELETE  /pedido-items/:id : delete the "id" pedidoItem.
     *
     * @param id the id of the pedidoItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pedido-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePedidoItem(@PathVariable Long id) {
        log.debug("REST request to delete PedidoItem : {}", id);
        pedidoItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
