package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.service.EtapaProducaoService;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;
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
 * REST controller for managing EtapaProducao.
 */
@RestController
@RequestMapping("/api")
public class EtapaProducaoResource {

    private final Logger log = LoggerFactory.getLogger(EtapaProducaoResource.class);

    private static final String ENTITY_NAME = "etapaProducao";

    private final EtapaProducaoService etapaProducaoService;

    public EtapaProducaoResource(EtapaProducaoService etapaProducaoService) {
        this.etapaProducaoService = etapaProducaoService;
    }

    /**
     * POST  /etapa-producaos : Create a new etapaProducao.
     *
     * @param etapaProducaoDTO the etapaProducaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapaProducaoDTO, or with status 400 (Bad Request) if the etapaProducao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etapa-producaos")
    @Timed
    public ResponseEntity<EtapaProducaoDTO> createEtapaProducao(@Valid @RequestBody EtapaProducaoDTO etapaProducaoDTO) throws URISyntaxException {
        log.debug("REST request to save EtapaProducao : {}", etapaProducaoDTO);
        if (etapaProducaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapaProducao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapaProducaoDTO result = etapaProducaoService.save(etapaProducaoDTO);
        return ResponseEntity.created(new URI("/api/etapa-producaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etapa-producaos : Updates an existing etapaProducao.
     *
     * @param etapaProducaoDTO the etapaProducaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapaProducaoDTO,
     * or with status 400 (Bad Request) if the etapaProducaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the etapaProducaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etapa-producaos")
    @Timed
    public ResponseEntity<EtapaProducaoDTO> updateEtapaProducao(@Valid @RequestBody EtapaProducaoDTO etapaProducaoDTO) throws URISyntaxException {
        log.debug("REST request to update EtapaProducao : {}", etapaProducaoDTO);
        if (etapaProducaoDTO.getId() == null) {
            return createEtapaProducao(etapaProducaoDTO);
        }
        EtapaProducaoDTO result = etapaProducaoService.save(etapaProducaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapaProducaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etapa-producaos : get all the etapaProducaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etapaProducaos in body
     */
    @GetMapping("/etapa-producaos")
    @Timed
    public ResponseEntity<List<EtapaProducaoDTO>> getAllEtapaProducaos(Pageable pageable) {
        log.debug("REST request to get a page of EtapaProducaos");
        Page<EtapaProducaoDTO> page = etapaProducaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etapa-producaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etapa-producaos/:id : get the "id" etapaProducao.
     *
     * @param id the id of the etapaProducaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapaProducaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etapa-producaos/{id}")
    @Timed
    public ResponseEntity<EtapaProducaoDTO> getEtapaProducao(@PathVariable Long id) {
        log.debug("REST request to get EtapaProducao : {}", id);
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etapaProducaoDTO));
    }

    /**
     * DELETE  /etapa-producaos/:id : delete the "id" etapaProducao.
     *
     * @param id the id of the etapaProducaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etapa-producaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtapaProducao(@PathVariable Long id) {
        log.debug("REST request to delete EtapaProducao : {}", id);
        etapaProducaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
