package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.repository.MaterialTamanhoRepository;
import br.com.vestdesk.service.MaterialTamanhoService;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
import br.com.vestdesk.service.mapper.MaterialTamanhoMapper;
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
 * Test class for the MaterialTamanhoResource REST controller.
 *
 * @see MaterialTamanhoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class MaterialTamanhoResourceIntTest {

    private static final Float DEFAULT_QUANTIDADE_MATERIAL = 1F;
    private static final Float UPDATED_QUANTIDADE_MATERIAL = 2F;

    @Autowired
    private MaterialTamanhoRepository materialTamanhoRepository;

    @Autowired
    private MaterialTamanhoMapper materialTamanhoMapper;

    @Autowired
    private MaterialTamanhoService materialTamanhoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMaterialTamanhoMockMvc;

    private MaterialTamanho materialTamanho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialTamanhoResource materialTamanhoResource = new MaterialTamanhoResource(materialTamanhoService);
        this.restMaterialTamanhoMockMvc = MockMvcBuilders.standaloneSetup(materialTamanhoResource)
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
    public static MaterialTamanho createEntity(EntityManager em) {
        MaterialTamanho materialTamanho = new MaterialTamanho()
            .quantidadeMaterial(DEFAULT_QUANTIDADE_MATERIAL);
        return materialTamanho;
    }

    @Before
    public void initTest() {
        materialTamanho = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialTamanho() throws Exception {
        int databaseSizeBeforeCreate = materialTamanhoRepository.findAll().size();

        // Create the MaterialTamanho
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoMapper.toDto(materialTamanho);
        restMaterialTamanhoMockMvc.perform(post("/api/material-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialTamanhoDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialTamanho in the database
        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialTamanho testMaterialTamanho = materialTamanhoList.get(materialTamanhoList.size() - 1);
        assertThat(testMaterialTamanho.getQuantidadeMaterial()).isEqualTo(DEFAULT_QUANTIDADE_MATERIAL);
    }

    @Test
    @Transactional
    public void createMaterialTamanhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialTamanhoRepository.findAll().size();

        // Create the MaterialTamanho with an existing ID
        materialTamanho.setId(1L);
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoMapper.toDto(materialTamanho);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialTamanhoMockMvc.perform(post("/api/material-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialTamanhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialTamanho in the database
        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantidadeMaterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialTamanhoRepository.findAll().size();
        // set the field null
        materialTamanho.setQuantidadeMaterial(null);

        // Create the MaterialTamanho, which fails.
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoMapper.toDto(materialTamanho);

        restMaterialTamanhoMockMvc.perform(post("/api/material-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialTamanhoDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialTamanhos() throws Exception {
        // Initialize the database
        materialTamanhoRepository.saveAndFlush(materialTamanho);

        // Get all the materialTamanhoList
        restMaterialTamanhoMockMvc.perform(get("/api/material-tamanhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialTamanho.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeMaterial").value(hasItem(DEFAULT_QUANTIDADE_MATERIAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getMaterialTamanho() throws Exception {
        // Initialize the database
        materialTamanhoRepository.saveAndFlush(materialTamanho);

        // Get the materialTamanho
        restMaterialTamanhoMockMvc.perform(get("/api/material-tamanhos/{id}", materialTamanho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materialTamanho.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeMaterial").value(DEFAULT_QUANTIDADE_MATERIAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialTamanho() throws Exception {
        // Get the materialTamanho
        restMaterialTamanhoMockMvc.perform(get("/api/material-tamanhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialTamanho() throws Exception {
        // Initialize the database
        materialTamanhoRepository.saveAndFlush(materialTamanho);
        int databaseSizeBeforeUpdate = materialTamanhoRepository.findAll().size();

        // Update the materialTamanho
        MaterialTamanho updatedMaterialTamanho = materialTamanhoRepository.findOne(materialTamanho.getId());
        // Disconnect from session so that the updates on updatedMaterialTamanho are not directly saved in db
        em.detach(updatedMaterialTamanho);
        updatedMaterialTamanho
            .quantidadeMaterial(UPDATED_QUANTIDADE_MATERIAL);
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoMapper.toDto(updatedMaterialTamanho);

        restMaterialTamanhoMockMvc.perform(put("/api/material-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialTamanhoDTO)))
            .andExpect(status().isOk());

        // Validate the MaterialTamanho in the database
        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeUpdate);
        MaterialTamanho testMaterialTamanho = materialTamanhoList.get(materialTamanhoList.size() - 1);
        assertThat(testMaterialTamanho.getQuantidadeMaterial()).isEqualTo(UPDATED_QUANTIDADE_MATERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialTamanho() throws Exception {
        int databaseSizeBeforeUpdate = materialTamanhoRepository.findAll().size();

        // Create the MaterialTamanho
        MaterialTamanhoDTO materialTamanhoDTO = materialTamanhoMapper.toDto(materialTamanho);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaterialTamanhoMockMvc.perform(put("/api/material-tamanhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialTamanhoDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialTamanho in the database
        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMaterialTamanho() throws Exception {
        // Initialize the database
        materialTamanhoRepository.saveAndFlush(materialTamanho);
        int databaseSizeBeforeDelete = materialTamanhoRepository.findAll().size();

        // Get the materialTamanho
        restMaterialTamanhoMockMvc.perform(delete("/api/material-tamanhos/{id}", materialTamanho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MaterialTamanho> materialTamanhoList = materialTamanhoRepository.findAll();
        assertThat(materialTamanhoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialTamanho.class);
        MaterialTamanho materialTamanho1 = new MaterialTamanho();
        materialTamanho1.setId(1L);
        MaterialTamanho materialTamanho2 = new MaterialTamanho();
        materialTamanho2.setId(materialTamanho1.getId());
        assertThat(materialTamanho1).isEqualTo(materialTamanho2);
        materialTamanho2.setId(2L);
        assertThat(materialTamanho1).isNotEqualTo(materialTamanho2);
        materialTamanho1.setId(null);
        assertThat(materialTamanho1).isNotEqualTo(materialTamanho2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialTamanhoDTO.class);
        MaterialTamanhoDTO materialTamanhoDTO1 = new MaterialTamanhoDTO();
        materialTamanhoDTO1.setId(1L);
        MaterialTamanhoDTO materialTamanhoDTO2 = new MaterialTamanhoDTO();
        assertThat(materialTamanhoDTO1).isNotEqualTo(materialTamanhoDTO2);
        materialTamanhoDTO2.setId(materialTamanhoDTO1.getId());
        assertThat(materialTamanhoDTO1).isEqualTo(materialTamanhoDTO2);
        materialTamanhoDTO2.setId(2L);
        assertThat(materialTamanhoDTO1).isNotEqualTo(materialTamanhoDTO2);
        materialTamanhoDTO1.setId(null);
        assertThat(materialTamanhoDTO1).isNotEqualTo(materialTamanhoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialTamanhoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialTamanhoMapper.fromId(null)).isNull();
    }
}
