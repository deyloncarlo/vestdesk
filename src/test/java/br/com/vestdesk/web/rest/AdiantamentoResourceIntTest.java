package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.Adiantamento;
import br.com.vestdesk.repository.AdiantamentoRepository;
import br.com.vestdesk.service.AdiantamentoService;
import br.com.vestdesk.service.dto.AdiantamentoDTO;
import br.com.vestdesk.service.mapper.AdiantamentoMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static br.com.vestdesk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdiantamentoResource REST controller.
 *
 * @see AdiantamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class AdiantamentoResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private AdiantamentoRepository adiantamentoRepository;

    @Autowired
    private AdiantamentoMapper adiantamentoMapper;

    @Autowired
    private AdiantamentoService adiantamentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdiantamentoMockMvc;

    private Adiantamento adiantamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdiantamentoResource adiantamentoResource = new AdiantamentoResource(adiantamentoService);
        this.restAdiantamentoMockMvc = MockMvcBuilders.standaloneSetup(adiantamentoResource)
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
    public static Adiantamento createEntity(EntityManager em) {
        Adiantamento adiantamento = new Adiantamento()
            .valor(DEFAULT_VALOR);
        return adiantamento;
    }

    @Before
    public void initTest() {
        adiantamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdiantamento() throws Exception {
        int databaseSizeBeforeCreate = adiantamentoRepository.findAll().size();

        // Create the Adiantamento
        AdiantamentoDTO adiantamentoDTO = adiantamentoMapper.toDto(adiantamento);
        restAdiantamentoMockMvc.perform(post("/api/adiantamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adiantamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Adiantamento in the database
        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Adiantamento testAdiantamento = adiantamentoList.get(adiantamentoList.size() - 1);
        assertThat(testAdiantamento.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createAdiantamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adiantamentoRepository.findAll().size();

        // Create the Adiantamento with an existing ID
        adiantamento.setId(1L);
        AdiantamentoDTO adiantamentoDTO = adiantamentoMapper.toDto(adiantamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdiantamentoMockMvc.perform(post("/api/adiantamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adiantamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adiantamento in the database
        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = adiantamentoRepository.findAll().size();
        // set the field null
        adiantamento.setValor(null);

        // Create the Adiantamento, which fails.
        AdiantamentoDTO adiantamentoDTO = adiantamentoMapper.toDto(adiantamento);

        restAdiantamentoMockMvc.perform(post("/api/adiantamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adiantamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdiantamentos() throws Exception {
        // Initialize the database
        adiantamentoRepository.saveAndFlush(adiantamento);

        // Get all the adiantamentoList
        restAdiantamentoMockMvc.perform(get("/api/adiantamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adiantamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void getAdiantamento() throws Exception {
        // Initialize the database
        adiantamentoRepository.saveAndFlush(adiantamento);

        // Get the adiantamento
        restAdiantamentoMockMvc.perform(get("/api/adiantamentos/{id}", adiantamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adiantamento.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdiantamento() throws Exception {
        // Get the adiantamento
        restAdiantamentoMockMvc.perform(get("/api/adiantamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdiantamento() throws Exception {
        // Initialize the database
        adiantamentoRepository.saveAndFlush(adiantamento);
        int databaseSizeBeforeUpdate = adiantamentoRepository.findAll().size();

        // Update the adiantamento
        Adiantamento updatedAdiantamento = adiantamentoRepository.findOne(adiantamento.getId());
        // Disconnect from session so that the updates on updatedAdiantamento are not directly saved in db
        em.detach(updatedAdiantamento);
        updatedAdiantamento
            .valor(UPDATED_VALOR);
        AdiantamentoDTO adiantamentoDTO = adiantamentoMapper.toDto(updatedAdiantamento);

        restAdiantamentoMockMvc.perform(put("/api/adiantamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adiantamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Adiantamento in the database
        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeUpdate);
        Adiantamento testAdiantamento = adiantamentoList.get(adiantamentoList.size() - 1);
        assertThat(testAdiantamento.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAdiantamento() throws Exception {
        int databaseSizeBeforeUpdate = adiantamentoRepository.findAll().size();

        // Create the Adiantamento
        AdiantamentoDTO adiantamentoDTO = adiantamentoMapper.toDto(adiantamento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdiantamentoMockMvc.perform(put("/api/adiantamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adiantamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Adiantamento in the database
        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdiantamento() throws Exception {
        // Initialize the database
        adiantamentoRepository.saveAndFlush(adiantamento);
        int databaseSizeBeforeDelete = adiantamentoRepository.findAll().size();

        // Get the adiantamento
        restAdiantamentoMockMvc.perform(delete("/api/adiantamentos/{id}", adiantamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Adiantamento> adiantamentoList = adiantamentoRepository.findAll();
        assertThat(adiantamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adiantamento.class);
        Adiantamento adiantamento1 = new Adiantamento();
        adiantamento1.setId(1L);
        Adiantamento adiantamento2 = new Adiantamento();
        adiantamento2.setId(adiantamento1.getId());
        assertThat(adiantamento1).isEqualTo(adiantamento2);
        adiantamento2.setId(2L);
        assertThat(adiantamento1).isNotEqualTo(adiantamento2);
        adiantamento1.setId(null);
        assertThat(adiantamento1).isNotEqualTo(adiantamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdiantamentoDTO.class);
        AdiantamentoDTO adiantamentoDTO1 = new AdiantamentoDTO();
        adiantamentoDTO1.setId(1L);
        AdiantamentoDTO adiantamentoDTO2 = new AdiantamentoDTO();
        assertThat(adiantamentoDTO1).isNotEqualTo(adiantamentoDTO2);
        adiantamentoDTO2.setId(adiantamentoDTO1.getId());
        assertThat(adiantamentoDTO1).isEqualTo(adiantamentoDTO2);
        adiantamentoDTO2.setId(2L);
        assertThat(adiantamentoDTO1).isNotEqualTo(adiantamentoDTO2);
        adiantamentoDTO1.setId(null);
        assertThat(adiantamentoDTO1).isNotEqualTo(adiantamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adiantamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adiantamentoMapper.fromId(null)).isNull();
    }
}
