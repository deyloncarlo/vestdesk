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
import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.repository.CorRepository;
import br.com.vestdesk.service.CorService;
import br.com.vestdesk.service.dto.CorDTO;
import br.com.vestdesk.service.mapper.CorMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the CorResource REST controller.
 *
 * @see CorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class CorResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
	private static final String UPDATED_CODIGO = "BBBBBBBBBB";

	@Autowired
	private CorRepository corRepository;

	@Autowired
	private CorMapper corMapper;

	@Autowired
	private CorService corService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restCorMockMvc;

	private Cor cor;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final CorResource corResource = new CorResource(this.corService);
		this.restCorMockMvc = MockMvcBuilders.standaloneSetup(corResource)
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
	public static Cor createEntity(EntityManager em)
	{
		Cor cor = new Cor().codigo(DEFAULT_CODIGO);
		return cor;
	}

	@Before
	public void initTest()
	{
		this.cor = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createCor() throws Exception
	{
		int databaseSizeBeforeCreate = this.corRepository.findAll().size();

		// Create the Cor
		CorDTO corDTO = this.corMapper.toDto(this.cor);
		this.restCorMockMvc.perform(post("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isCreated());

		// Validate the Cor in the database
		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeCreate + 1);
		Cor testCor = corList.get(corList.size() - 1);
		assertThat(testCor.getCodigo()).isEqualTo(DEFAULT_CODIGO);
	}

	@Test
	@Transactional
	public void createCorWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.corRepository.findAll().size();

		// Create the Cor with an existing ID
		this.cor.setId(1L);
		CorDTO corDTO = this.corMapper.toDto(this.cor);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restCorMockMvc.perform(post("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isBadRequest());

		// Validate the Cor in the database
		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.corRepository.findAll().size();
		// set the field null

		// Create the Cor, which fails.
		CorDTO corDTO = this.corMapper.toDto(this.cor);

		this.restCorMockMvc.perform(post("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isBadRequest());

		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkCodigoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.corRepository.findAll().size();
		// set the field null
		this.cor.setCodigo(null);

		// Create the Cor, which fails.
		CorDTO corDTO = this.corMapper.toDto(this.cor);

		this.restCorMockMvc.perform(post("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isBadRequest());

		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllCors() throws Exception
	{
		// Initialize the database
		this.corRepository.saveAndFlush(this.cor);

		// Get all the corList
		this.restCorMockMvc.perform(get("/api/cors?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.cor.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
	}

	@Test
	@Transactional
	public void getCor() throws Exception
	{
		// Initialize the database
		this.corRepository.saveAndFlush(this.cor);

		// Get the cor
		this.restCorMockMvc.perform(get("/api/cors/{id}", this.cor.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.cor.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingCor() throws Exception
	{
		// Get the cor
		this.restCorMockMvc.perform(get("/api/cors/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateCor() throws Exception
	{
		// Initialize the database
		this.corRepository.saveAndFlush(this.cor);
		int databaseSizeBeforeUpdate = this.corRepository.findAll().size();

		// Update the cor
		Cor updatedCor = this.corRepository.findOne(this.cor.getId());
		// Disconnect from session so that the updates on updatedCor are not
		// directly saved in db
		this.em.detach(updatedCor);
		updatedCor.codigo(UPDATED_CODIGO);
		CorDTO corDTO = this.corMapper.toDto(updatedCor);

		this.restCorMockMvc.perform(put("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isOk());

		// Validate the Cor in the database
		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeUpdate);
		Cor testCor = corList.get(corList.size() - 1);
		assertThat(testCor.getCodigo()).isEqualTo(UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void updateNonExistingCor() throws Exception
	{
		int databaseSizeBeforeUpdate = this.corRepository.findAll().size();

		// Create the Cor
		CorDTO corDTO = this.corMapper.toDto(this.cor);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restCorMockMvc.perform(put("/api/cors").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(corDTO))).andExpect(status().isCreated());

		// Validate the Cor in the database
		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteCor() throws Exception
	{
		// Initialize the database
		this.corRepository.saveAndFlush(this.cor);
		int databaseSizeBeforeDelete = this.corRepository.findAll().size();

		// Get the cor
		this.restCorMockMvc.perform(delete("/api/cors/{id}", this.cor.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Cor> corList = this.corRepository.findAll();
		assertThat(corList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Cor.class);
		Cor cor1 = new Cor();
		cor1.setId(1L);
		Cor cor2 = new Cor();
		cor2.setId(cor1.getId());
		assertThat(cor1).isEqualTo(cor2);
		cor2.setId(2L);
		assertThat(cor1).isNotEqualTo(cor2);
		cor1.setId(null);
		assertThat(cor1).isNotEqualTo(cor2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(CorDTO.class);
		CorDTO corDTO1 = new CorDTO();
		corDTO1.setId(1L);
		CorDTO corDTO2 = new CorDTO();
		assertThat(corDTO1).isNotEqualTo(corDTO2);
		corDTO2.setId(corDTO1.getId());
		assertThat(corDTO1).isEqualTo(corDTO2);
		corDTO2.setId(2L);
		assertThat(corDTO1).isNotEqualTo(corDTO2);
		corDTO1.setId(null);
		assertThat(corDTO1).isNotEqualTo(corDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.corMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.corMapper.fromId(null)).isNull();
	}
}
