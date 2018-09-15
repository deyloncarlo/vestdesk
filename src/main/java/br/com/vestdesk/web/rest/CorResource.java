package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.CorService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.CorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cor.
 */
@RestController
@RequestMapping("/api")
public class CorResource {

    private final Logger log = LoggerFactory.getLogger(CorResource.class);

    private static final String ENTITY_NAME = "cor";

    private final CorService corService;

    public CorResource(CorService corService) {
        this.corService = corService;
    }

    /**
     * POST  /cors : Create a new cor.
     *
     * @param corDTO the corDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corDTO, or with status 400 (Bad Request) if the cor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cors")
    @Timed
    public ResponseEntity<CorDTO> createCor(@Valid @RequestBody CorDTO corDTO) throws URISyntaxException {
        log.debug("REST request to save Cor : {}", corDTO);
        if (corDTO.getId() != null) {
            throw new BadRequestAlertException("A new cor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorDTO result = corService.save(corDTO);
        return ResponseEntity.created(new URI("/api/cors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cors : Updates an existing cor.
     *
     * @param corDTO the corDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corDTO,
     * or with status 400 (Bad Request) if the corDTO is not valid,
     * or with status 500 (Internal Server Error) if the corDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cors")
    @Timed
    public ResponseEntity<CorDTO> updateCor(@Valid @RequestBody CorDTO corDTO) throws URISyntaxException {
        log.debug("REST request to update Cor : {}", corDTO);
        if (corDTO.getId() == null) {
            return createCor(corDTO);
        }
        CorDTO result = corService.save(corDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cors : get all the cors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cors in body
     */
    @GetMapping("/cors")
    @Timed
    public ResponseEntity<List<CorDTO>> getAllCors(Pageable pageable) {
        log.debug("REST request to get a page of Cors");
        Page<CorDTO> page = corService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cors/:id : get the "id" cor.
     *
     * @param id the id of the corDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cors/{id}")
    @Timed
    public ResponseEntity<CorDTO> getCor(@PathVariable Long id) {
        log.debug("REST request to get Cor : {}", id);
        CorDTO corDTO = corService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corDTO));
    }

    /**
     * DELETE  /cors/:id : delete the "id" cor.
     *
     * @param id the id of the corDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCor(@PathVariable Long id) {
        log.debug("REST request to delete Cor : {}", id);
        corService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
