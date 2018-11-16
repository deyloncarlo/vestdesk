package br.com.vestdesk.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vestdesk.domain.ConfiguracaoLayout;

import br.com.vestdesk.repository.ConfiguracaoLayoutRepository;
import br.com.vestdesk.web.rest.errors.BadRequestAlertException;
import br.com.vestdesk.web.rest.util.HeaderUtil;
import br.com.vestdesk.web.rest.util.PaginationUtil;
import br.com.vestdesk.service.dto.ConfiguracaoLayoutDTO;
import br.com.vestdesk.service.mapper.ConfiguracaoLayoutMapper;
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
 * REST controller for managing ConfiguracaoLayout.
 */
@RestController
@RequestMapping("/api")
public class ConfiguracaoLayoutResource {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoLayoutResource.class);

    private static final String ENTITY_NAME = "configuracaoLayout";

    private final ConfiguracaoLayoutRepository configuracaoLayoutRepository;

    private final ConfiguracaoLayoutMapper configuracaoLayoutMapper;

    public ConfiguracaoLayoutResource(ConfiguracaoLayoutRepository configuracaoLayoutRepository, ConfiguracaoLayoutMapper configuracaoLayoutMapper) {
        this.configuracaoLayoutRepository = configuracaoLayoutRepository;
        this.configuracaoLayoutMapper = configuracaoLayoutMapper;
    }

    /**
     * POST  /configuracao-layouts : Create a new configuracaoLayout.
     *
     * @param configuracaoLayoutDTO the configuracaoLayoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configuracaoLayoutDTO, or with status 400 (Bad Request) if the configuracaoLayout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configuracao-layouts")
    @Timed
    public ResponseEntity<ConfiguracaoLayoutDTO> createConfiguracaoLayout(@RequestBody ConfiguracaoLayoutDTO configuracaoLayoutDTO) throws URISyntaxException {
        log.debug("REST request to save ConfiguracaoLayout : {}", configuracaoLayoutDTO);
        if (configuracaoLayoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new configuracaoLayout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfiguracaoLayout configuracaoLayout = configuracaoLayoutMapper.toEntity(configuracaoLayoutDTO);
        configuracaoLayout = configuracaoLayoutRepository.save(configuracaoLayout);
        ConfiguracaoLayoutDTO result = configuracaoLayoutMapper.toDto(configuracaoLayout);
        return ResponseEntity.created(new URI("/api/configuracao-layouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configuracao-layouts : Updates an existing configuracaoLayout.
     *
     * @param configuracaoLayoutDTO the configuracaoLayoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configuracaoLayoutDTO,
     * or with status 400 (Bad Request) if the configuracaoLayoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the configuracaoLayoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configuracao-layouts")
    @Timed
    public ResponseEntity<ConfiguracaoLayoutDTO> updateConfiguracaoLayout(@RequestBody ConfiguracaoLayoutDTO configuracaoLayoutDTO) throws URISyntaxException {
        log.debug("REST request to update ConfiguracaoLayout : {}", configuracaoLayoutDTO);
        if (configuracaoLayoutDTO.getId() == null) {
            return createConfiguracaoLayout(configuracaoLayoutDTO);
        }
        ConfiguracaoLayout configuracaoLayout = configuracaoLayoutMapper.toEntity(configuracaoLayoutDTO);
        configuracaoLayout = configuracaoLayoutRepository.save(configuracaoLayout);
        ConfiguracaoLayoutDTO result = configuracaoLayoutMapper.toDto(configuracaoLayout);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configuracaoLayoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configuracao-layouts : get all the configuracaoLayouts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of configuracaoLayouts in body
     */
    @GetMapping("/configuracao-layouts")
    @Timed
    public ResponseEntity<List<ConfiguracaoLayoutDTO>> getAllConfiguracaoLayouts(Pageable pageable) {
        log.debug("REST request to get a page of ConfiguracaoLayouts");
        Page<ConfiguracaoLayout> page = configuracaoLayoutRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/configuracao-layouts");
        return new ResponseEntity<>(configuracaoLayoutMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /configuracao-layouts/:id : get the "id" configuracaoLayout.
     *
     * @param id the id of the configuracaoLayoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configuracaoLayoutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/configuracao-layouts/{id}")
    @Timed
    public ResponseEntity<ConfiguracaoLayoutDTO> getConfiguracaoLayout(@PathVariable Long id) {
        log.debug("REST request to get ConfiguracaoLayout : {}", id);
        ConfiguracaoLayout configuracaoLayout = configuracaoLayoutRepository.findOne(id);
        ConfiguracaoLayoutDTO configuracaoLayoutDTO = configuracaoLayoutMapper.toDto(configuracaoLayout);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(configuracaoLayoutDTO));
    }

    /**
     * DELETE  /configuracao-layouts/:id : delete the "id" configuracaoLayout.
     *
     * @param id the id of the configuracaoLayoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configuracao-layouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfiguracaoLayout(@PathVariable Long id) {
        log.debug("REST request to delete ConfiguracaoLayout : {}", id);
        configuracaoLayoutRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
