package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.repository.ModeloVestuarioRepository;
import br.com.vestdesk.service.ModeloVestuarioService;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;
import br.com.vestdesk.service.mapper.ModeloVestuarioMapper;
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
 * Test class for the ModeloVestuarioResource REST controller.
 *
 * @see ModeloVestuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ModeloVestuarioResourceIntTest {

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

    @Autowired
    private ModeloVestuarioRepository modeloVestuarioRepository;

    @Autowired
    private ModeloVestuarioMapper modeloVestuarioMapper;

    @Autowired
    private ModeloVestuarioService modeloVestuarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeloVestuarioMockMvc;

    private ModeloVestuario modeloVestuario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeloVestuarioResource modeloVestuarioResource = new ModeloVestuarioResource(modeloVestuarioService);
        this.restModeloVestuarioMockMvc = MockMvcBuilders.standaloneSetup(modeloVestuarioResource)
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
    public static ModeloVestuario createEntity(EntityManager em) {
        ModeloVestuario modeloVestuario = new ModeloVestuario()
            .preco(DEFAULT_PRECO);
        return modeloVestuario;
    }

    @Before
    public void initTest() {
        modeloVestuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeloVestuario() throws Exception {
        int databaseSizeBeforeCreate = modeloVestuarioRepository.findAll().size();

        // Create the ModeloVestuario
        ModeloVestuarioDTO modeloVestuarioDTO = modeloVestuarioMapper.toDto(modeloVestuario);
        restModeloVestuarioMockMvc.perform(post("/api/modelo-vestuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeloVestuario in the database
        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeCreate + 1);
        ModeloVestuario testModeloVestuario = modeloVestuarioList.get(modeloVestuarioList.size() - 1);
        assertThat(testModeloVestuario.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createModeloVestuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeloVestuarioRepository.findAll().size();

        // Create the ModeloVestuario with an existing ID
        modeloVestuario.setId(1L);
        ModeloVestuarioDTO modeloVestuarioDTO = modeloVestuarioMapper.toDto(modeloVestuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeloVestuarioMockMvc.perform(post("/api/modelo-vestuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModeloVestuario in the database
        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloVestuarioRepository.findAll().size();
        // set the field null
        modeloVestuario.setPreco(null);

        // Create the ModeloVestuario, which fails.
        ModeloVestuarioDTO modeloVestuarioDTO = modeloVestuarioMapper.toDto(modeloVestuario);

        restModeloVestuarioMockMvc.perform(post("/api/modelo-vestuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
            .andExpect(status().isBadRequest());

        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModeloVestuarios() throws Exception {
        // Initialize the database
        modeloVestuarioRepository.saveAndFlush(modeloVestuario);

        // Get all the modeloVestuarioList
        restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeloVestuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
    }

    @Test
    @Transactional
    public void getModeloVestuario() throws Exception {
        // Initialize the database
        modeloVestuarioRepository.saveAndFlush(modeloVestuario);

        // Get the modeloVestuario
        restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios/{id}", modeloVestuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modeloVestuario.getId().intValue()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModeloVestuario() throws Exception {
        // Get the modeloVestuario
        restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeloVestuario() throws Exception {
        // Initialize the database
        modeloVestuarioRepository.saveAndFlush(modeloVestuario);
        int databaseSizeBeforeUpdate = modeloVestuarioRepository.findAll().size();

        // Update the modeloVestuario
        ModeloVestuario updatedModeloVestuario = modeloVestuarioRepository.findOne(modeloVestuario.getId());
        // Disconnect from session so that the updates on updatedModeloVestuario are not directly saved in db
        em.detach(updatedModeloVestuario);
        updatedModeloVestuario
            .preco(UPDATED_PRECO);
        ModeloVestuarioDTO modeloVestuarioDTO = modeloVestuarioMapper.toDto(updatedModeloVestuario);

        restModeloVestuarioMockMvc.perform(put("/api/modelo-vestuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
            .andExpect(status().isOk());

        // Validate the ModeloVestuario in the database
        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeUpdate);
        ModeloVestuario testModeloVestuario = modeloVestuarioList.get(modeloVestuarioList.size() - 1);
        assertThat(testModeloVestuario.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void updateNonExistingModeloVestuario() throws Exception {
        int databaseSizeBeforeUpdate = modeloVestuarioRepository.findAll().size();

        // Create the ModeloVestuario
        ModeloVestuarioDTO modeloVestuarioDTO = modeloVestuarioMapper.toDto(modeloVestuario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModeloVestuarioMockMvc.perform(put("/api/modelo-vestuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeloVestuario in the database
        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModeloVestuario() throws Exception {
        // Initialize the database
        modeloVestuarioRepository.saveAndFlush(modeloVestuario);
        int databaseSizeBeforeDelete = modeloVestuarioRepository.findAll().size();

        // Get the modeloVestuario
        restModeloVestuarioMockMvc.perform(delete("/api/modelo-vestuarios/{id}", modeloVestuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModeloVestuario> modeloVestuarioList = modeloVestuarioRepository.findAll();
        assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloVestuario.class);
        ModeloVestuario modeloVestuario1 = new ModeloVestuario();
        modeloVestuario1.setId(1L);
        ModeloVestuario modeloVestuario2 = new ModeloVestuario();
        modeloVestuario2.setId(modeloVestuario1.getId());
        assertThat(modeloVestuario1).isEqualTo(modeloVestuario2);
        modeloVestuario2.setId(2L);
        assertThat(modeloVestuario1).isNotEqualTo(modeloVestuario2);
        modeloVestuario1.setId(null);
        assertThat(modeloVestuario1).isNotEqualTo(modeloVestuario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloVestuarioDTO.class);
        ModeloVestuarioDTO modeloVestuarioDTO1 = new ModeloVestuarioDTO();
        modeloVestuarioDTO1.setId(1L);
        ModeloVestuarioDTO modeloVestuarioDTO2 = new ModeloVestuarioDTO();
        assertThat(modeloVestuarioDTO1).isNotEqualTo(modeloVestuarioDTO2);
        modeloVestuarioDTO2.setId(modeloVestuarioDTO1.getId());
        assertThat(modeloVestuarioDTO1).isEqualTo(modeloVestuarioDTO2);
        modeloVestuarioDTO2.setId(2L);
        assertThat(modeloVestuarioDTO1).isNotEqualTo(modeloVestuarioDTO2);
        modeloVestuarioDTO1.setId(null);
        assertThat(modeloVestuarioDTO1).isNotEqualTo(modeloVestuarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeloVestuarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeloVestuarioMapper.fromId(null)).isNull();
    }
}
