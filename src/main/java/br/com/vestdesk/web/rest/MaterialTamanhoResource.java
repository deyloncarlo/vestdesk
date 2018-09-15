package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.MaterialTamanhoService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
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
 * REST controller for managing MaterialTamanho.
 */
@RestController
@RequestMapping("/api")
public class MaterialTamanhoResource {

    private final Logger log = LoggerFactory.getLogger(MaterialTamanhoResource.class);

    private static final String ENTITY_NAME = "materialTamanho";

    private final MaterialTamanhoService materialTamanhoService;

    public MaterialTamanhoResource(MaterialTamanhoService materialTamanhoService) {
        this.materialTamanhoService = materialTamanhoService;
    }

    /**
     * POST  /material-tamanhos : Create a new materialTamanho.
     *
     * @param materialTamanhoDTO the materialTamanhoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new materialTamanhoDTO, or with status 400 (Bad Request) if the materialTamanho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/material-tamanhos")
    @Timed
    public ResponseEntity<MaterialTamanhoDTO> createMaterialTamanho(@Valid @RequestBody MaterialTamanhoDTO materialTamanhoDTO) throws URISyntaxException {
        log.debug("REST request to save MaterialTamanho : {}", materialTamanhoDTO);
        if (materialTamanhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialTamanho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialTamanhoDTO result = materialTamanhoService.save(materialTamanhoDTO);
        return ResponseEntity.created(new URI("/api/material-tamanhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /material-tamanhos : Updates an existing materialTamanho.
     *
     * @param materialTamanhoDTO the materialTamanhoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated materialTamanhoDTO,
     * or with status 400 (Bad Request) if the materialTamanhoDTO is not valid,
     * or with status 500 (Internal Server Error) if the materialTamanhoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/material-tamanhos")
    @Timed
    public ResponseEntity<MaterialTamanhoDTO> updateMaterialTamanho(@Valid @RequestBody MaterialTamanhoDTO materialTamanhoDTO) throws URISyntaxException {
        log.debug("REST request to update MaterialTamanho : {}", materialTamanhoDTO);
        if (materialTamanhoDTO.getId() == null) {
            return createMaterialTamanho(materialTamanhoDTO);
        }
        MaterialTamanhoDTO result = materialTamanhoService.save(materialTamanhoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materialTamanhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /material-tamanhos : get all the materialTamanhos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of materialTamanhos in body
     */
    @GetMapping("/material-tamanhos")
    @Timed
    public ResponseEntity<List<MaterialTamanhoDTO>> getAllMaterialTamanhos(Pageable pageable) {
        log.debug("REST request to get a page of MaterialTamanhos");
        Page<MaterialTamanhoDTO> page = materialTamanhoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/material-tamanhos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /material-tamanhos/:id : get the "id" materialTamanho.
     *
     * @param id the id of the materialTamanhoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the materialTamanhoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/material-tamanhos/{id}")
    @Timed
    public ResponseEntity<MaterialTamanhoDTO> getMaterialTamanho(@PathVariable Long id) {
        log.debug("REST request to get MaterialTamanho : {}", id);
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materialTamanhoDTO));
    }

    /**
     * DELETE  /material-tamanhos/:id : delete the "id" materialTamanho.
     *
     * @param id the id of the materialTamanhoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/material-tamanhos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaterialTamanho(@PathVariable Long id) {
        log.debug("REST request to delete MaterialTamanho : {}", id);
        materialTamanhoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
