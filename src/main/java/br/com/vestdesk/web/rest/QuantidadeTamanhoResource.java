package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.QuantidadeTamanhoService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;
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
 * REST controller for managing QuantidadeTamanho.
 */
@RestController
@RequestMapping("/api")
public class QuantidadeTamanhoResource {

    private final Logger log = LoggerFactory.getLogger(QuantidadeTamanhoResource.class);

    private static final String ENTITY_NAME = "quantidadeTamanho";

    private final QuantidadeTamanhoService quantidadeTamanhoService;

    public QuantidadeTamanhoResource(QuantidadeTamanhoService quantidadeTamanhoService) {
        this.quantidadeTamanhoService = quantidadeTamanhoService;
    }

    /**
     * POST  /quantidade-tamanhos : Create a new quantidadeTamanho.
     *
     * @param quantidadeTamanhoDTO the quantidadeTamanhoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quantidadeTamanhoDTO, or with status 400 (Bad Request) if the quantidadeTamanho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quantidade-tamanhos")
    @Timed
    public ResponseEntity<QuantidadeTamanhoDTO> createQuantidadeTamanho(@Valid @RequestBody QuantidadeTamanhoDTO quantidadeTamanhoDTO) throws URISyntaxException {
        log.debug("REST request to save QuantidadeTamanho : {}", quantidadeTamanhoDTO);
        if (quantidadeTamanhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new quantidadeTamanho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuantidadeTamanhoDTO result = quantidadeTamanhoService.save(quantidadeTamanhoDTO);
        return ResponseEntity.created(new URI("/api/quantidade-tamanhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quantidade-tamanhos : Updates an existing quantidadeTamanho.
     *
     * @param quantidadeTamanhoDTO the quantidadeTamanhoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quantidadeTamanhoDTO,
     * or with status 400 (Bad Request) if the quantidadeTamanhoDTO is not valid,
     * or with status 500 (Internal Server Error) if the quantidadeTamanhoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quantidade-tamanhos")
    @Timed
    public ResponseEntity<QuantidadeTamanhoDTO> updateQuantidadeTamanho(@Valid @RequestBody QuantidadeTamanhoDTO quantidadeTamanhoDTO) throws URISyntaxException {
        log.debug("REST request to update QuantidadeTamanho : {}", quantidadeTamanhoDTO);
        if (quantidadeTamanhoDTO.getId() == null) {
            return createQuantidadeTamanho(quantidadeTamanhoDTO);
        }
        QuantidadeTamanhoDTO result = quantidadeTamanhoService.save(quantidadeTamanhoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quantidadeTamanhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quantidade-tamanhos : get all the quantidadeTamanhos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quantidadeTamanhos in body
     */
    @GetMapping("/quantidade-tamanhos")
    @Timed
    public ResponseEntity<List<QuantidadeTamanhoDTO>> getAllQuantidadeTamanhos(Pageable pageable) {
        log.debug("REST request to get a page of QuantidadeTamanhos");
        Page<QuantidadeTamanhoDTO> page = quantidadeTamanhoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quantidade-tamanhos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quantidade-tamanhos/:id : get the "id" quantidadeTamanho.
     *
     * @param id the id of the quantidadeTamanhoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quantidadeTamanhoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/quantidade-tamanhos/{id}")
    @Timed
    public ResponseEntity<QuantidadeTamanhoDTO> getQuantidadeTamanho(@PathVariable Long id) {
        log.debug("REST request to get QuantidadeTamanho : {}", id);
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantidadeTamanhoDTO));
    }

    /**
     * DELETE  /quantidade-tamanhos/:id : delete the "id" quantidadeTamanho.
     *
     * @param id the id of the quantidadeTamanhoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quantidade-tamanhos/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuantidadeTamanho(@PathVariable Long id) {
        log.debug("REST request to delete QuantidadeTamanho : {}", id);
        quantidadeTamanhoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
