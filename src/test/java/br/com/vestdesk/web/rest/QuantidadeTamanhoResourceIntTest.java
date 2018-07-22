package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.QuantidadeTamanho;
import br.com.vestdesk.repository.QuantidadeTamanhoRepository;
import br.com.vestdesk.service.QuantidadeTamanhoService;
import br.com.vestdesk.service.dto.QuantidadeTamanhoDTO;
import br.com.vestdesk.service.mapper.QuantidadeTamanhoMapper;
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
 * Test class for the QuantidadeTamanhoResource REST controller.
 *
 * @see QuantidadeTamanhoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class QuantidadeTamanhoResourceIntTest {

    private static final String DEFAULT_TAMANHO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANHO = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTIDADE_MATERIAL = 1F;
    private static final Float UPDATED_QUANTIDADE_MATERIAL = 2F;

    @Autowired
    private QuantidadeTamanhoRepository quantidadeTamanhoRepository;

    @Autowired
    private QuantidadeTamanhoMapper quantidadeTamanhoMapper;

    @Autowired
    private QuantidadeTamanhoService quantidadeTamanhoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuantidadeTamanhoMockMvc;

    private QuantidadeTamanho quantidadeTamanho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuantidadeTamanhoResource quantidadeTamanhoResource = new QuantidadeTamanhoResource(quantidadeTamanhoService);
        this.restQuantidadeTamanhoMockMvc = MockMvcBuilders.standaloneSetup(quantidadeTamanhoResource)
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
    public static QuantidadeTamanho createEntity(EntityManager em) {
        QuantidadeTamanho quantidadeTamanho = new QuantidadeTamanho()
            .tamanho(DEFAULT_TAMANHO)
            .quantidadeMaterial(DEFAULT_QUANTIDADE_MATERIAL);
        return quantidadeTamanho;
    }

    @Before
    public void initTest() {
        quantidadeTamanho = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuantidadeTamanho() throws Exception {
        int databaseSizeBeforeCreate = quantidadeTamanhoRepository.findAll().size();

        // Create the QuantidadeTamanho
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(quantidadeTamanho);
        restQuantidadeTamanhoMockMvc.perform(post("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isCreated());

        // Validate the QuantidadeTamanho in the database
        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeCreate + 1);
        QuantidadeTamanho testQuantidadeTamanho = quantidadeTamanhoList.get(quantidadeTamanhoList.size() - 1);
        assertThat(testQuantidadeTamanho.getTamanho()).isEqualTo(DEFAULT_TAMANHO);
        assertThat(testQuantidadeTamanho.getQuantidadeMaterial()).isEqualTo(DEFAULT_QUANTIDADE_MATERIAL);
    }

    @Test
    @Transactional
    public void createQuantidadeTamanhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quantidadeTamanhoRepository.findAll().size();

        // Create the QuantidadeTamanho with an existing ID
        quantidadeTamanho.setId(1L);
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(quantidadeTamanho);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantidadeTamanhoMockMvc.perform(post("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuantidadeTamanho in the database
        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTamanhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quantidadeTamanhoRepository.findAll().size();
        // set the field null
        quantidadeTamanho.setTamanho(null);

        // Create the QuantidadeTamanho, which fails.
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(quantidadeTamanho);

        restQuantidadeTamanhoMockMvc.perform(post("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isBadRequest());

        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeMaterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = quantidadeTamanhoRepository.findAll().size();
        // set the field null
        quantidadeTamanho.setQuantidadeMaterial(null);

        // Create the QuantidadeTamanho, which fails.
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(quantidadeTamanho);

        restQuantidadeTamanhoMockMvc.perform(post("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isBadRequest());

        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuantidadeTamanhos() throws Exception {
        // Initialize the database
        quantidadeTamanhoRepository.saveAndFlush(quantidadeTamanho);

        // Get all the quantidadeTamanhoList
        restQuantidadeTamanhoMockMvc.perform(get("/api/quantidade-tamanhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantidadeTamanho.getId().intValue())))
            .andExpect(jsonPath("$.[*].tamanho").value(hasItem(DEFAULT_TAMANHO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeMaterial").value(hasItem(DEFAULT_QUANTIDADE_MATERIAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getQuantidadeTamanho() throws Exception {
        // Initialize the database
        quantidadeTamanhoRepository.saveAndFlush(quantidadeTamanho);

        // Get the quantidadeTamanho
        restQuantidadeTamanhoMockMvc.perform(get("/api/quantidade-tamanhos/{id}", quantidadeTamanho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quantidadeTamanho.getId().intValue()))
            .andExpect(jsonPath("$.tamanho").value(DEFAULT_TAMANHO.toString()))
            .andExpect(jsonPath("$.quantidadeMaterial").value(DEFAULT_QUANTIDADE_MATERIAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuantidadeTamanho() throws Exception {
        // Get the quantidadeTamanho
        restQuantidadeTamanhoMockMvc.perform(get("/api/quantidade-tamanhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuantidadeTamanho() throws Exception {
        // Initialize the database
        quantidadeTamanhoRepository.saveAndFlush(quantidadeTamanho);
        int databaseSizeBeforeUpdate = quantidadeTamanhoRepository.findAll().size();

        // Update the quantidadeTamanho
        QuantidadeTamanho updatedQuantidadeTamanho = quantidadeTamanhoRepository.findOne(quantidadeTamanho.getId());
        // Disconnect from session so that the updates on updatedQuantidadeTamanho are not directly saved in db
        em.detach(updatedQuantidadeTamanho);
        updatedQuantidadeTamanho
            .tamanho(UPDATED_TAMANHO)
            .quantidadeMaterial(UPDATED_QUANTIDADE_MATERIAL);
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(updatedQuantidadeTamanho);

        restQuantidadeTamanhoMockMvc.perform(put("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isOk());

        // Validate the QuantidadeTamanho in the database
        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeUpdate);
        QuantidadeTamanho testQuantidadeTamanho = quantidadeTamanhoList.get(quantidadeTamanhoList.size() - 1);
        assertThat(testQuantidadeTamanho.getTamanho()).isEqualTo(UPDATED_TAMANHO);
        assertThat(testQuantidadeTamanho.getQuantidadeMaterial()).isEqualTo(UPDATED_QUANTIDADE_MATERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingQuantidadeTamanho() throws Exception {
        int databaseSizeBeforeUpdate = quantidadeTamanhoRepository.findAll().size();

        // Create the QuantidadeTamanho
        QuantidadeTamanhoDTO quantidadeTamanhoDTO = quantidadeTamanhoMapper.toDto(quantidadeTamanho);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuantidadeTamanhoMockMvc.perform(put("/api/quantidade-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantidadeTamanhoDTO)))
            .andExpect(status().isCreated());

        // Validate the QuantidadeTamanho in the database
        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuantidadeTamanho() throws Exception {
        // Initialize the database
        quantidadeTamanhoRepository.saveAndFlush(quantidadeTamanho);
        int databaseSizeBeforeDelete = quantidadeTamanhoRepository.findAll().size();

        // Get the quantidadeTamanho
        restQuantidadeTamanhoMockMvc.perform(delete("/api/quantidade-tamanhos/{id}", quantidadeTamanho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuantidadeTamanho> quantidadeTamanhoList = quantidadeTamanhoRepository.findAll();
        assertThat(quantidadeTamanhoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuantidadeTamanho.class);
        QuantidadeTamanho quantidadeTamanho1 = new QuantidadeTamanho();
        quantidadeTamanho1.setId(1L);
        QuantidadeTamanho quantidadeTamanho2 = new QuantidadeTamanho();
        quantidadeTamanho2.setId(quantidadeTamanho1.getId());
        assertThat(quantidadeTamanho1).isEqualTo(quantidadeTamanho2);
        quantidadeTamanho2.setId(2L);
        assertThat(quantidadeTamanho1).isNotEqualTo(quantidadeTamanho2);
        quantidadeTamanho1.setId(null);
        assertThat(quantidadeTamanho1).isNotEqualTo(quantidadeTamanho2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuantidadeTamanhoDTO.class);
        QuantidadeTamanhoDTO quantidadeTamanhoDTO1 = new QuantidadeTamanhoDTO();
        quantidadeTamanhoDTO1.setId(1L);
        QuantidadeTamanhoDTO quantidadeTamanhoDTO2 = new QuantidadeTamanhoDTO();
        assertThat(quantidadeTamanhoDTO1).isNotEqualTo(quantidadeTamanhoDTO2);
        quantidadeTamanhoDTO2.setId(quantidadeTamanhoDTO1.getId());
        assertThat(quantidadeTamanhoDTO1).isEqualTo(quantidadeTamanhoDTO2);
        quantidadeTamanhoDTO2.setId(2L);
        assertThat(quantidadeTamanhoDTO1).isNotEqualTo(quantidadeTamanhoDTO2);
        quantidadeTamanhoDTO1.setId(null);
        assertThat(quantidadeTamanhoDTO1).isNotEqualTo(quantidadeTamanhoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quantidadeTamanhoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quantidadeTamanhoMapper.fromId(null)).isNull();
    }
}
