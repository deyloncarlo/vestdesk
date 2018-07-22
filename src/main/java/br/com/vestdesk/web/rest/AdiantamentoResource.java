package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.AdiantamentoService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.AdiantamentoDTO;
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
 * REST controller for managing Adiantamento.
 */
@RestController
@RequestMapping("/api")
public class AdiantamentoResource {

    private final Logger log = LoggerFactory.getLogger(AdiantamentoResource.class);

    private static final String ENTITY_NAME = "adiantamento";

    private final AdiantamentoService adiantamentoService;

    public AdiantamentoResource(AdiantamentoService adiantamentoService) {
        this.adiantamentoService = adiantamentoService;
    }

    /**
     * POST  /adiantamentos : Create a new adiantamento.
     *
     * @param adiantamentoDTO the adiantamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adiantamentoDTO, or with status 400 (Bad Request) if the adiantamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adiantamentos")
    @Timed
    public ResponseEntity<AdiantamentoDTO> createAdiantamento(@Valid @RequestBody AdiantamentoDTO adiantamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Adiantamento : {}", adiantamentoDTO);
        if (adiantamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new adiantamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdiantamentoDTO result = adiantamentoService.save(adiantamentoDTO);
        return ResponseEntity.created(new URI("/api/adiantamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adiantamentos : Updates an existing adiantamento.
     *
     * @param adiantamentoDTO the adiantamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adiantamentoDTO,
     * or with status 400 (Bad Request) if the adiantamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the adiantamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adiantamentos")
    @Timed
    public ResponseEntity<AdiantamentoDTO> updateAdiantamento(@Valid @RequestBody AdiantamentoDTO adiantamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Adiantamento : {}", adiantamentoDTO);
        if (adiantamentoDTO.getId() == null) {
            return createAdiantamento(adiantamentoDTO);
        }
        AdiantamentoDTO result = adiantamentoService.save(adiantamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adiantamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adiantamentos : get all the adiantamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adiantamentos in body
     */
    @GetMapping("/adiantamentos")
    @Timed
    public ResponseEntity<List<AdiantamentoDTO>> getAllAdiantamentos(Pageable pageable) {
        log.debug("REST request to get a page of Adiantamentos");
        Page<AdiantamentoDTO> page = adiantamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adiantamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adiantamentos/:id : get the "id" adiantamento.
     *
     * @param id the id of the adiantamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adiantamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adiantamentos/{id}")
    @Timed
    public ResponseEntity<AdiantamentoDTO> getAdiantamento(@PathVariable Long id) {
        log.debug("REST request to get Adiantamento : {}", id);
        AdiantamentoDTO adiantamentoDTO = adiantamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adiantamentoDTO));
    }

    /**
     * DELETE  /adiantamentos/:id : delete the "id" adiantamento.
     *
     * @param id the id of the adiantamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adiantamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdiantamento(@PathVariable Long id) {
        log.debug("REST request to delete Adiantamento : {}", id);
        adiantamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
