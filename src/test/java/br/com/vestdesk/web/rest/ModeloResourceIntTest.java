package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.Modelo;
import br.com.vestdesk.repository.ModeloRepository;
import br.com.vestdesk.service.ModeloService;
import br.com.vestdesk.service.dto.ModeloDTO;
import br.com.vestdesk.service.mapper.ModeloMapper;
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
 * Test class for the ModeloResource REST controller.
 *
 * @see ModeloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ModeloResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloMapper modeloMapper;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeloMockMvc;

    private Modelo modelo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeloResource modeloResource = new ModeloResource(modeloService);
        this.restModeloMockMvc = MockMvcBuilders.standaloneSetup(modeloResource)
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
    public static Modelo createEntity(EntityManager em) {
        Modelo modelo = new Modelo()
            .nome(DEFAULT_NOME);
        return modelo;
    }

    @Before
    public void initTest() {
        modelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelo() throws Exception {
        int databaseSizeBeforeCreate = modeloRepository.findAll().size();

        // Create the Modelo
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);
        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isCreated());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeCreate + 1);
        Modelo testModelo = modeloList.get(modeloList.size() - 1);
        assertThat(testModelo.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createModeloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeloRepository.findAll().size();

        // Create the Modelo with an existing ID
        modelo.setId(1L);
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloRepository.findAll().size();
        // set the field null
        modelo.setNome(null);

        // Create the Modelo, which fails.
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelos() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        // Get all the modeloList
        restModeloMockMvc.perform(get("/api/modelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        // Get the modelo
        restModeloMockMvc.perform(get("/api/modelos/{id}", modelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModelo() throws Exception {
        // Get the modelo
        restModeloMockMvc.perform(get("/api/modelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);
        int databaseSizeBeforeUpdate = modeloRepository.findAll().size();

        // Update the modelo
        Modelo updatedModelo = modeloRepository.findOne(modelo.getId());
        // Disconnect from session so that the updates on updatedModelo are not directly saved in db
        em.detach(updatedModelo);
        updatedModelo
            .nome(UPDATED_NOME);
        ModeloDTO modeloDTO = modeloMapper.toDto(updatedModelo);

        restModeloMockMvc.perform(put("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isOk());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeUpdate);
        Modelo testModelo = modeloList.get(modeloList.size() - 1);
        assertThat(testModelo.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingModelo() throws Exception {
        int databaseSizeBeforeUpdate = modeloRepository.findAll().size();

        // Create the Modelo
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModeloMockMvc.perform(put("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isCreated());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);
        int databaseSizeBeforeDelete = modeloRepository.findAll().size();

        // Get the modelo
        restModeloMockMvc.perform(delete("/api/modelos/{id}", modelo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modelo.class);
        Modelo modelo1 = new Modelo();
        modelo1.setId(1L);
        Modelo modelo2 = new Modelo();
        modelo2.setId(modelo1.getId());
        assertThat(modelo1).isEqualTo(modelo2);
        modelo2.setId(2L);
        assertThat(modelo1).isNotEqualTo(modelo2);
        modelo1.setId(null);
        assertThat(modelo1).isNotEqualTo(modelo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloDTO.class);
        ModeloDTO modeloDTO1 = new ModeloDTO();
        modeloDTO1.setId(1L);
        ModeloDTO modeloDTO2 = new ModeloDTO();
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
        modeloDTO2.setId(modeloDTO1.getId());
        assertThat(modeloDTO1).isEqualTo(modeloDTO2);
        modeloDTO2.setId(2L);
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
        modeloDTO1.setId(null);
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeloMapper.fromId(null)).isNull();
    }
}
