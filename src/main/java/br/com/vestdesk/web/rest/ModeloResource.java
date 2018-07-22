package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.ModeloService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.ModeloDTO;
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
 * REST controller for managing Modelo.
 */
@RestController
@RequestMapping("/api")
public class ModeloResource {

    private final Logger log = LoggerFactory.getLogger(ModeloResource.class);

    private static final String ENTITY_NAME = "modelo";

    private final ModeloService modeloService;

    public ModeloResource(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * POST  /modelos : Create a new modelo.
     *
     * @param modeloDTO the modeloDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modeloDTO, or with status 400 (Bad Request) if the modelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modelos")
    @Timed
    public ResponseEntity<ModeloDTO> createModelo(@Valid @RequestBody ModeloDTO modeloDTO) throws URISyntaxException {
        log.debug("REST request to save Modelo : {}", modeloDTO);
        if (modeloDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeloDTO result = modeloService.save(modeloDTO);
        return ResponseEntity.created(new URI("/api/modelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modelos : Updates an existing modelo.
     *
     * @param modeloDTO the modeloDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modeloDTO,
     * or with status 400 (Bad Request) if the modeloDTO is not valid,
     * or with status 500 (Internal Server Error) if the modeloDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modelos")
    @Timed
    public ResponseEntity<ModeloDTO> updateModelo(@Valid @RequestBody ModeloDTO modeloDTO) throws URISyntaxException {
        log.debug("REST request to update Modelo : {}", modeloDTO);
        if (modeloDTO.getId() == null) {
            return createModelo(modeloDTO);
        }
        ModeloDTO result = modeloService.save(modeloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modeloDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modelos : get all the modelos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modelos in body
     */
    @GetMapping("/modelos")
    @Timed
    public ResponseEntity<List<ModeloDTO>> getAllModelos(Pageable pageable) {
        log.debug("REST request to get a page of Modelos");
        Page<ModeloDTO> page = modeloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modelos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /modelos/:id : get the "id" modelo.
     *
     * @param id the id of the modeloDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modeloDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modelos/{id}")
    @Timed
    public ResponseEntity<ModeloDTO> getModelo(@PathVariable Long id) {
        log.debug("REST request to get Modelo : {}", id);
        ModeloDTO modeloDTO = modeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modeloDTO));
    }

    /**
     * DELETE  /modelos/:id : delete the "id" modelo.
     *
     * @param id the id of the modeloDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modelos/{id}")
    @Timed
    public ResponseEntity<Void> deleteModelo(@PathVariable Long id) {
        log.debug("REST request to delete Modelo : {}", id);
        modeloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
