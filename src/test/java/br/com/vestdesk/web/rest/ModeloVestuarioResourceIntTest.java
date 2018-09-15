package br.com.vestdesk.web.rest;

import static br.com.vestdesk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import br.com.vestdesk.VestdeskApp;
import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.repository.ModeloVestuarioRepository;
import br.com.vestdesk.service.ModeloVestuarioService;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;
import br.com.vestdesk.service.mapper.ModeloVestuarioMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ModeloVestuarioResource REST controller.
 *
 * @see ModeloVestuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ModeloVestuarioResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final Modelo DEFAULT_MODELO = Modelo.MOLETOM;
	private static final Modelo UPDATED_MODELO = Modelo.POLO;

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
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final ModeloVestuarioResource modeloVestuarioResource = new ModeloVestuarioResource(
				this.modeloVestuarioService);
		this.restModeloVestuarioMockMvc = MockMvcBuilders.standaloneSetup(modeloVestuarioResource)
				.setCustomArgumentResolvers(this.pageableArgumentResolver).setControllerAdvice(this.exceptionTranslator)
				.setConversionService(createFormattingConversionService())
				.setMessageConverters(this.jacksonMessageConverter).build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it,
	 * if they test an entity which requires the current entity.
	 */
	public static ModeloVestuario createEntity(EntityManager em)
	{
		ModeloVestuario modeloVestuario = new ModeloVestuario().modelo(DEFAULT_MODELO);
		return modeloVestuario;
	}

	@Before
	public void initTest()
	{
		this.modeloVestuario = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createModeloVestuario() throws Exception
	{
		int databaseSizeBeforeCreate = this.modeloVestuarioRepository.findAll().size();

		// Create the ModeloVestuario
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(this.modeloVestuario);
		this.restModeloVestuarioMockMvc
				.perform(post("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isCreated());

		// Validate the ModeloVestuario in the database
		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeCreate + 1);
		ModeloVestuario testModeloVestuario = modeloVestuarioList.get(modeloVestuarioList.size() - 1);
		assertThat(testModeloVestuario.getModelo()).isEqualTo(DEFAULT_MODELO);
	}

	@Test
	@Transactional
	public void createModeloVestuarioWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.modeloVestuarioRepository.findAll().size();

		// Create the ModeloVestuario with an existing ID
		this.modeloVestuario.setId(1L);
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(this.modeloVestuario);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restModeloVestuarioMockMvc
				.perform(post("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isBadRequest());

		// Validate the ModeloVestuario in the database
		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.modeloVestuarioRepository.findAll().size();
		// set the field null

		// Create the ModeloVestuario, which fails.
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(this.modeloVestuario);

		this.restModeloVestuarioMockMvc
				.perform(post("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isBadRequest());

		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkModeloIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.modeloVestuarioRepository.findAll().size();
		// set the field null
		this.modeloVestuario.setModelo(null);

		// Create the ModeloVestuario, which fails.
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(this.modeloVestuario);

		this.restModeloVestuarioMockMvc
				.perform(post("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isBadRequest());

		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllModeloVestuarios() throws Exception
	{
		// Initialize the database
		this.modeloVestuarioRepository.saveAndFlush(this.modeloVestuario);

		// Get all the modeloVestuarioList
		this.restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.modeloVestuario.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())));
	}

	@Test
	@Transactional
	public void getModeloVestuario() throws Exception
	{
		// Initialize the database
		this.modeloVestuarioRepository.saveAndFlush(this.modeloVestuario);

		// Get the modeloVestuario
		this.restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios/{id}", this.modeloVestuario.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.modeloVestuario.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingModeloVestuario() throws Exception
	{
		// Get the modeloVestuario
		this.restModeloVestuarioMockMvc.perform(get("/api/modelo-vestuarios/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateModeloVestuario() throws Exception
	{
		// Initialize the database
		this.modeloVestuarioRepository.saveAndFlush(this.modeloVestuario);
		int databaseSizeBeforeUpdate = this.modeloVestuarioRepository.findAll().size();

		// Update the modeloVestuario
		ModeloVestuario updatedModeloVestuario = this.modeloVestuarioRepository.findOne(this.modeloVestuario.getId());
		// Disconnect from session so that the updates on updatedModeloVestuario
		// are not directly saved in db
		this.em.detach(updatedModeloVestuario);
		updatedModeloVestuario.modelo(UPDATED_MODELO);
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(updatedModeloVestuario);

		this.restModeloVestuarioMockMvc
				.perform(put("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isOk());

		// Validate the ModeloVestuario in the database
		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeUpdate);
		ModeloVestuario testModeloVestuario = modeloVestuarioList.get(modeloVestuarioList.size() - 1);
		assertThat(testModeloVestuario.getModelo()).isEqualTo(UPDATED_MODELO);
	}

	@Test
	@Transactional
	public void updateNonExistingModeloVestuario() throws Exception
	{
		int databaseSizeBeforeUpdate = this.modeloVestuarioRepository.findAll().size();

		// Create the ModeloVestuario
		ModeloVestuarioDTO modeloVestuarioDTO = this.modeloVestuarioMapper.toDto(this.modeloVestuario);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restModeloVestuarioMockMvc
				.perform(put("/api/modelo-vestuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(modeloVestuarioDTO)))
				.andExpect(status().isCreated());

		// Validate the ModeloVestuario in the database
		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteModeloVestuario() throws Exception
	{
		// Initialize the database
		this.modeloVestuarioRepository.saveAndFlush(this.modeloVestuario);
		int databaseSizeBeforeDelete = this.modeloVestuarioRepository.findAll().size();

		// Get the modeloVestuario
		this.restModeloVestuarioMockMvc.perform(delete("/api/modelo-vestuarios/{id}", this.modeloVestuario.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

		// Validate the database is empty
		List<ModeloVestuario> modeloVestuarioList = this.modeloVestuarioRepository.findAll();
		assertThat(modeloVestuarioList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
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
	public void dtoEqualsVerifier() throws Exception
	{
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
	public void testEntityFromId()
	{
		assertThat(this.modeloVestuarioMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.modeloVestuarioMapper.fromId(null)).isNull();
	}
}
