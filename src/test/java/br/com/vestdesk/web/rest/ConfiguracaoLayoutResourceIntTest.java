package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import br.com.vestdesk.repository.ConfiguracaoLayoutRepository;
import br.com.vestdesk.service.dto.ConfiguracaoLayoutDTO;
import br.com.vestdesk.service.mapper.ConfiguracaoLayoutMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.vestdesk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConfiguracaoLayoutResource REST controller.
 *
 * @see ConfiguracaoLayoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ConfiguracaoLayoutResourceIntTest {

    private static final LocalDate DEFAULT_DATA_CRICAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRICAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ConfiguracaoLayoutRepository configuracaoLayoutRepository;

    @Autowired
    private ConfiguracaoLayoutMapper configuracaoLayoutMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfiguracaoLayoutMockMvc;

    private ConfiguracaoLayout configuracaoLayout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfiguracaoLayoutResource configuracaoLayoutResource = new ConfiguracaoLayoutResource(configuracaoLayoutRepository, configuracaoLayoutMapper);
        this.restConfiguracaoLayoutMockMvc = MockMvcBuilders.standaloneSetup(configuracaoLayoutResource)
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
    public static ConfiguracaoLayout createEntity(EntityManager em) {
        ConfiguracaoLayout configuracaoLayout = new ConfiguracaoLayout()
            .dataCricao(DEFAULT_DATA_CRICAO);
        return configuracaoLayout;
    }

    @Before
    public void initTest() {
        configuracaoLayout = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfiguracaoLayout() throws Exception {
        int databaseSizeBeforeCreate = configuracaoLayoutRepository.findAll().size();

        // Create the ConfiguracaoLayout
        ConfiguracaoLayoutDTO configuracaoLayoutDTO = configuracaoLayoutMapper.toDto(configuracaoLayout);
        restConfiguracaoLayoutMockMvc.perform(post("/api/configuracao-layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configuracaoLayoutDTO)))
            .andExpect(status().isCreated());

        // Validate the ConfiguracaoLayout in the database
        List<ConfiguracaoLayout> configuracaoLayoutList = configuracaoLayoutRepository.findAll();
        assertThat(configuracaoLayoutList).hasSize(databaseSizeBeforeCreate + 1);
        ConfiguracaoLayout testConfiguracaoLayout = configuracaoLayoutList.get(configuracaoLayoutList.size() - 1);
        assertThat(testConfiguracaoLayout.getDataCricao()).isEqualTo(DEFAULT_DATA_CRICAO);
    }

    @Test
    @Transactional
    public void createConfiguracaoLayoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configuracaoLayoutRepository.findAll().size();

        // Create the ConfiguracaoLayout with an existing ID
        configuracaoLayout.setId(1L);
        ConfiguracaoLayoutDTO configuracaoLayoutDTO = configuracaoLayoutMapper.toDto(configuracaoLayout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfiguracaoLayoutMockMvc.perform(post("/api/configuracao-layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configuracaoLayoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConfiguracaoLayout in the database
        List<ConfiguracaoLayout> configuracaoLayoutList = configuracaoLayoutRepository.findAll();
        assertThat(configuracaoLayoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConfiguracaoLayouts() throws Exception {
        // Initialize the database
        configuracaoLayoutRepository.saveAndFlush(configuracaoLayout);

        // Get all the configuracaoLayoutList
        restConfiguracaoLayoutMockMvc.perform(get("/api/configuracao-layouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configuracaoLayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCricao").value(hasItem(DEFAULT_DATA_CRICAO.toString())));
    }

    @Test
    @Transactional
    public void getConfiguracaoLayout() throws Exception {
        // Initialize the database
        configuracaoLayoutRepository.saveAndFlush(configuracaoLayout);

        // Get the configuracaoLayout
        restConfiguracaoLayoutMockMvc.perform(get("/api/configuracao-layouts/{id}", configuracaoLayout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configuracaoLayout.getId().intValue()))
            .andExpect(jsonPath("$.dataCricao").value(DEFAULT_DATA_CRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfiguracaoLayout() throws Exception {
        // Get the configuracaoLayout
        restConfiguracaoLayoutMockMvc.perform(get("/api/configuracao-layouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfiguracaoLayout() throws Exception {
        // Initialize the database
        configuracaoLayoutRepository.saveAndFlush(configuracaoLayout);
        int databaseSizeBeforeUpdate = configuracaoLayoutRepository.findAll().size();

        // Update the configuracaoLayout
        ConfiguracaoLayout updatedConfiguracaoLayout = configuracaoLayoutRepository.findOne(configuracaoLayout.getId());
        // Disconnect from session so that the updates on updatedConfiguracaoLayout are not directly saved in db
        em.detach(updatedConfiguracaoLayout);
        updatedConfiguracaoLayout
            .dataCricao(UPDATED_DATA_CRICAO);
        ConfiguracaoLayoutDTO configuracaoLayoutDTO = configuracaoLayoutMapper.toDto(updatedConfiguracaoLayout);

        restConfiguracaoLayoutMockMvc.perform(put("/api/configuracao-layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configuracaoLayoutDTO)))
            .andExpect(status().isOk());

        // Validate the ConfiguracaoLayout in the database
        List<ConfiguracaoLayout> configuracaoLayoutList = configuracaoLayoutRepository.findAll();
        assertThat(configuracaoLayoutList).hasSize(databaseSizeBeforeUpdate);
        ConfiguracaoLayout testConfiguracaoLayout = configuracaoLayoutList.get(configuracaoLayoutList.size() - 1);
        assertThat(testConfiguracaoLayout.getDataCricao()).isEqualTo(UPDATED_DATA_CRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingConfiguracaoLayout() throws Exception {
        int databaseSizeBeforeUpdate = configuracaoLayoutRepository.findAll().size();

        // Create the ConfiguracaoLayout
        ConfiguracaoLayoutDTO configuracaoLayoutDTO = configuracaoLayoutMapper.toDto(configuracaoLayout);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConfiguracaoLayoutMockMvc.perform(put("/api/configuracao-layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configuracaoLayoutDTO)))
            .andExpect(status().isCreated());

        // Validate the ConfiguracaoLayout in the database
        List<ConfiguracaoLayout> configuracaoLayoutList = configuracaoLayoutRepository.findAll();
        assertThat(configuracaoLayoutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConfiguracaoLayout() throws Exception {
        // Initialize the database
        configuracaoLayoutRepository.saveAndFlush(configuracaoLayout);
        int databaseSizeBeforeDelete = configuracaoLayoutRepository.findAll().size();

        // Get the configuracaoLayout
        restConfiguracaoLayoutMockMvc.perform(delete("/api/configuracao-layouts/{id}", configuracaoLayout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConfiguracaoLayout> configuracaoLayoutList = configuracaoLayoutRepository.findAll();
        assertThat(configuracaoLayoutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfiguracaoLayout.class);
        ConfiguracaoLayout configuracaoLayout1 = new ConfiguracaoLayout();
        configuracaoLayout1.setId(1L);
        ConfiguracaoLayout configuracaoLayout2 = new ConfiguracaoLayout();
        configuracaoLayout2.setId(configuracaoLayout1.getId());
        assertThat(configuracaoLayout1).isEqualTo(configuracaoLayout2);
        configuracaoLayout2.setId(2L);
        assertThat(configuracaoLayout1).isNotEqualTo(configuracaoLayout2);
        configuracaoLayout1.setId(null);
        assertThat(configuracaoLayout1).isNotEqualTo(configuracaoLayout2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfiguracaoLayoutDTO.class);
        ConfiguracaoLayoutDTO configuracaoLayoutDTO1 = new ConfiguracaoLayoutDTO();
        configuracaoLayoutDTO1.setId(1L);
        ConfiguracaoLayoutDTO configuracaoLayoutDTO2 = new ConfiguracaoLayoutDTO();
        assertThat(configuracaoLayoutDTO1).isNotEqualTo(configuracaoLayoutDTO2);
        configuracaoLayoutDTO2.setId(configuracaoLayoutDTO1.getId());
        assertThat(configuracaoLayoutDTO1).isEqualTo(configuracaoLayoutDTO2);
        configuracaoLayoutDTO2.setId(2L);
        assertThat(configuracaoLayoutDTO1).isNotEqualTo(configuracaoLayoutDTO2);
        configuracaoLayoutDTO1.setId(null);
        assertThat(configuracaoLayoutDTO1).isNotEqualTo(configuracaoLayoutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(configuracaoLayoutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(configuracaoLayoutMapper.fromId(null)).isNull();
    }
}
