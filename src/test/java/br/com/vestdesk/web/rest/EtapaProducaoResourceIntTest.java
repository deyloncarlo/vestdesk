package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.EtapaProducao;
import br.com.vestdesk.repository.EtapaProducaoRepository;
import br.com.vestdesk.service.EtapaProducaoService;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;
import br.com.vestdesk.service.mapper.EtapaProducaoMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.vestdesk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EtapaProducaoResource REST controller.
 *
 * @see EtapaProducaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class EtapaProducaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRAZO_EXECUCAO = 1;
    private static final Integer UPDATED_PRAZO_EXECUCAO = 2;

    @Autowired
    private EtapaProducaoRepository etapaProducaoRepository;

    @Autowired
    private EtapaProducaoMapper etapaProducaoMapper;

    @Autowired
    private EtapaProducaoService etapaProducaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtapaProducaoMockMvc;

    private EtapaProducao etapaProducao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapaProducaoResource etapaProducaoResource = new EtapaProducaoResource(etapaProducaoService);
        this.restEtapaProducaoMockMvc = MockMvcBuilders.standaloneSetup(etapaProducaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaProducao createEntity(EntityManager em) {
        EtapaProducao etapaProducao = new EtapaProducao()
            .nome(DEFAULT_NOME)
            .prazoExecucao(DEFAULT_PRAZO_EXECUCAO);
        return etapaProducao;
    }

    @Before
    public void initTest() {
        etapaProducao = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapaProducao() throws Exception {
        int databaseSizeBeforeCreate = etapaProducaoRepository.findAll().size();

        // Create the EtapaProducao
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(etapaProducao);
        restEtapaProducaoMockMvc.perform(post("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapaProducao in the database
        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeCreate + 1);
        EtapaProducao testEtapaProducao = etapaProducaoList.get(etapaProducaoList.size() - 1);
        assertThat(testEtapaProducao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEtapaProducao.getPrazoExecucao()).isEqualTo(DEFAULT_PRAZO_EXECUCAO);
    }

    @Test
    @Transactional
    public void createEtapaProducaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapaProducaoRepository.findAll().size();

        // Create the EtapaProducao with an existing ID
        etapaProducao.setId(1L);
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(etapaProducao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaProducaoMockMvc.perform(post("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaProducao in the database
        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etapaProducaoRepository.findAll().size();
        // set the field null
        etapaProducao.setNome(null);

        // Create the EtapaProducao, which fails.
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(etapaProducao);

        restEtapaProducaoMockMvc.perform(post("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrazoExecucaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = etapaProducaoRepository.findAll().size();
        // set the field null
        etapaProducao.setPrazoExecucao(null);

        // Create the EtapaProducao, which fails.
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(etapaProducao);

        restEtapaProducaoMockMvc.perform(post("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtapaProducaos() throws Exception {
        // Initialize the database
        etapaProducaoRepository.saveAndFlush(etapaProducao);

        // Get all the etapaProducaoList
        restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapaProducao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].prazoExecucao").value(hasItem(DEFAULT_PRAZO_EXECUCAO)));
    }

    @Test
    @Transactional
    public void getEtapaProducao() throws Exception {
        // Initialize the database
        etapaProducaoRepository.saveAndFlush(etapaProducao);

        // Get the etapaProducao
        restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos/{id}", etapaProducao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapaProducao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.prazoExecucao").value(DEFAULT_PRAZO_EXECUCAO));
    }

    @Test
    @Transactional
    public void getNonExistingEtapaProducao() throws Exception {
        // Get the etapaProducao
        restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapaProducao() throws Exception {
        // Initialize the database
        etapaProducaoRepository.saveAndFlush(etapaProducao);
        int databaseSizeBeforeUpdate = etapaProducaoRepository.findAll().size();

        // Update the etapaProducao
        EtapaProducao updatedEtapaProducao = etapaProducaoRepository.findOne(etapaProducao.getId());
        // Disconnect from session so that the updates on updatedEtapaProducao are not directly saved in db
        em.detach(updatedEtapaProducao);
        updatedEtapaProducao
            .nome(UPDATED_NOME)
            .prazoExecucao(UPDATED_PRAZO_EXECUCAO);
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(updatedEtapaProducao);

        restEtapaProducaoMockMvc.perform(put("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isOk());

        // Validate the EtapaProducao in the database
        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeUpdate);
        EtapaProducao testEtapaProducao = etapaProducaoList.get(etapaProducaoList.size() - 1);
        assertThat(testEtapaProducao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEtapaProducao.getPrazoExecucao()).isEqualTo(UPDATED_PRAZO_EXECUCAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapaProducao() throws Exception {
        int databaseSizeBeforeUpdate = etapaProducaoRepository.findAll().size();

        // Create the EtapaProducao
        EtapaProducaoDTO etapaProducaoDTO = etapaProducaoMapper.toDto(etapaProducao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtapaProducaoMockMvc.perform(put("/api/etapa-producaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapaProducao in the database
        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtapaProducao() throws Exception {
        // Initialize the database
        etapaProducaoRepository.saveAndFlush(etapaProducao);
        int databaseSizeBeforeDelete = etapaProducaoRepository.findAll().size();

        // Get the etapaProducao
        restEtapaProducaoMockMvc.perform(delete("/api/etapa-producaos/{id}", etapaProducao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EtapaProducao> etapaProducaoList = etapaProducaoRepository.findAll();
        assertThat(etapaProducaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaProducao.class);
        EtapaProducao etapaProducao1 = new EtapaProducao();
        etapaProducao1.setId(1L);
        EtapaProducao etapaProducao2 = new EtapaProducao();
        etapaProducao2.setId(etapaProducao1.getId());
        assertThat(etapaProducao1).isEqualTo(etapaProducao2);
        etapaProducao2.setId(2L);
        assertThat(etapaProducao1).isNotEqualTo(etapaProducao2);
        etapaProducao1.setId(null);
        assertThat(etapaProducao1).isNotEqualTo(etapaProducao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaProducaoDTO.class);
        EtapaProducaoDTO etapaProducaoDTO1 = new EtapaProducaoDTO();
        etapaProducaoDTO1.setId(1L);
        EtapaProducaoDTO etapaProducaoDTO2 = new EtapaProducaoDTO();
        assertThat(etapaProducaoDTO1).isNotEqualTo(etapaProducaoDTO2);
        etapaProducaoDTO2.setId(etapaProducaoDTO1.getId());
        assertThat(etapaProducaoDTO1).isEqualTo(etapaProducaoDTO2);
        etapaProducaoDTO2.setId(2L);
        assertThat(etapaProducaoDTO1).isNotEqualTo(etapaProducaoDTO2);
        etapaProducaoDTO1.setId(null);
        assertThat(etapaProducaoDTO1).isNotEqualTo(etapaProducaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etapaProducaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etapaProducaoMapper.fromId(null)).isNull();
    }
}
