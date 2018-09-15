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

import java.math.BigDecimal;
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
import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.enumeration.Tamanho;
import br.com.vestdesk.repository.ConfiguracaoProdutoRepository;
import br.com.vestdesk.service.ConfiguracaoProdutoService;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;
import br.com.vestdesk.service.mapper.ConfiguracaoProdutoMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ConfiguracaoProdutoResource REST controller.
 *
 * @see ConfiguracaoProdutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ConfiguracaoProdutoResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final Tamanho DEFAULT_TAMANHO = Tamanho.P;
	private static final Tamanho UPDATED_TAMANHO = Tamanho.M;

	private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
	private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

	@Autowired
	private ConfiguracaoProdutoRepository configuracaoProdutoRepository;

	@Autowired
	private ConfiguracaoProdutoMapper configuracaoProdutoMapper;

	@Autowired
	private ConfiguracaoProdutoService configuracaoProdutoService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restConfiguracaoProdutoMockMvc;

	private ConfiguracaoProduto configuracaoProduto;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final ConfiguracaoProdutoResource configuracaoProdutoResource = new ConfiguracaoProdutoResource(
				this.configuracaoProdutoService);
		this.restConfiguracaoProdutoMockMvc = MockMvcBuilders.standaloneSetup(configuracaoProdutoResource)
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
	public static ConfiguracaoProduto createEntity(EntityManager em)
	{
		ConfiguracaoProduto configuracaoProduto = new ConfiguracaoProduto().tamanho(DEFAULT_TAMANHO)
				.preco(DEFAULT_PRECO);
		return configuracaoProduto;
	}

	@Before
	public void initTest()
	{
		this.configuracaoProduto = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createConfiguracaoProduto() throws Exception
	{
		int databaseSizeBeforeCreate = this.configuracaoProdutoRepository.findAll().size();

		// Create the ConfiguracaoProduto
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);
		this.restConfiguracaoProdutoMockMvc
				.perform(post("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isCreated());

		// Validate the ConfiguracaoProduto in the database
		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
		ConfiguracaoProduto testConfiguracaoProduto = configuracaoProdutoList.get(configuracaoProdutoList.size() - 1);
		assertThat(testConfiguracaoProduto.getTamanho()).isEqualTo(DEFAULT_TAMANHO);
		assertThat(testConfiguracaoProduto.getPreco()).isEqualTo(DEFAULT_PRECO);
	}

	@Test
	@Transactional
	public void createConfiguracaoProdutoWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.configuracaoProdutoRepository.findAll().size();

		// Create the ConfiguracaoProduto with an existing ID
		this.configuracaoProduto.setId(1L);
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restConfiguracaoProdutoMockMvc
				.perform(post("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isBadRequest());

		// Validate the ConfiguracaoProduto in the database
		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.configuracaoProdutoRepository.findAll().size();
		// set the field null
		// Create the ConfiguracaoProduto, which fails.
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);

		this.restConfiguracaoProdutoMockMvc
				.perform(post("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isBadRequest());

		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkTamanhoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.configuracaoProdutoRepository.findAll().size();
		// set the field null
		this.configuracaoProduto.setTamanho(null);

		// Create the ConfiguracaoProduto, which fails.
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);

		this.restConfiguracaoProdutoMockMvc
				.perform(post("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isBadRequest());

		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkPrecoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.configuracaoProdutoRepository.findAll().size();
		// set the field null
		this.configuracaoProduto.setPreco(null);

		// Create the ConfiguracaoProduto, which fails.
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);

		this.restConfiguracaoProdutoMockMvc
				.perform(post("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isBadRequest());

		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllConfiguracaoProdutos() throws Exception
	{
		// Initialize the database
		this.configuracaoProdutoRepository.saveAndFlush(this.configuracaoProduto);

		// Get all the configuracaoProdutoList
		this.restConfiguracaoProdutoMockMvc.perform(get("/api/configuracao-produtos?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.configuracaoProduto.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].tamanho").value(hasItem(DEFAULT_TAMANHO.toString())))
				.andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
	}

	@Test
	@Transactional
	public void getConfiguracaoProduto() throws Exception
	{
		// Initialize the database
		this.configuracaoProdutoRepository.saveAndFlush(this.configuracaoProduto);

		// Get the configuracaoProduto
		this.restConfiguracaoProdutoMockMvc
				.perform(get("/api/configuracao-produtos/{id}", this.configuracaoProduto.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.configuracaoProduto.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.tamanho").value(DEFAULT_TAMANHO.toString()))
				.andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()));
	}

	@Test
	@Transactional
	public void getNonExistingConfiguracaoProduto() throws Exception
	{
		// Get the configuracaoProduto
		this.restConfiguracaoProdutoMockMvc.perform(get("/api/configuracao-produtos/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateConfiguracaoProduto() throws Exception
	{
		// Initialize the database
		this.configuracaoProdutoRepository.saveAndFlush(this.configuracaoProduto);
		int databaseSizeBeforeUpdate = this.configuracaoProdutoRepository.findAll().size();

		// Update the configuracaoProduto
		ConfiguracaoProduto updatedConfiguracaoProduto = this.configuracaoProdutoRepository
				.findOne(this.configuracaoProduto.getId());
		// Disconnect from session so that the updates on
		// updatedConfiguracaoProduto are not directly saved in db
		this.em.detach(updatedConfiguracaoProduto);
		updatedConfiguracaoProduto.tamanho(UPDATED_TAMANHO).preco(UPDATED_PRECO);
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper
				.toDto(updatedConfiguracaoProduto);

		this.restConfiguracaoProdutoMockMvc
				.perform(put("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isOk());

		// Validate the ConfiguracaoProduto in the database
		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeUpdate);
		ConfiguracaoProduto testConfiguracaoProduto = configuracaoProdutoList.get(configuracaoProdutoList.size() - 1);
		assertThat(testConfiguracaoProduto.getTamanho()).isEqualTo(UPDATED_TAMANHO);
		assertThat(testConfiguracaoProduto.getPreco()).isEqualTo(UPDATED_PRECO);
	}

	@Test
	@Transactional
	public void updateNonExistingConfiguracaoProduto() throws Exception
	{
		int databaseSizeBeforeUpdate = this.configuracaoProdutoRepository.findAll().size();

		// Create the ConfiguracaoProduto
		ConfiguracaoProdutoDTO configuracaoProdutoDTO = this.configuracaoProdutoMapper.toDto(this.configuracaoProduto);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restConfiguracaoProdutoMockMvc
				.perform(put("/api/configuracao-produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(configuracaoProdutoDTO)))
				.andExpect(status().isCreated());

		// Validate the ConfiguracaoProduto in the database
		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteConfiguracaoProduto() throws Exception
	{
		// Initialize the database
		this.configuracaoProdutoRepository.saveAndFlush(this.configuracaoProduto);
		int databaseSizeBeforeDelete = this.configuracaoProdutoRepository.findAll().size();

		// Get the configuracaoProduto
		this.restConfiguracaoProdutoMockMvc
				.perform(delete("/api/configuracao-produtos/{id}", this.configuracaoProduto.getId())
						.accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<ConfiguracaoProduto> configuracaoProdutoList = this.configuracaoProdutoRepository.findAll();
		assertThat(configuracaoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(ConfiguracaoProduto.class);
		ConfiguracaoProduto configuracaoProduto1 = new ConfiguracaoProduto();
		configuracaoProduto1.setId(1L);
		ConfiguracaoProduto configuracaoProduto2 = new ConfiguracaoProduto();
		configuracaoProduto2.setId(configuracaoProduto1.getId());
		assertThat(configuracaoProduto1).isEqualTo(configuracaoProduto2);
		configuracaoProduto2.setId(2L);
		assertThat(configuracaoProduto1).isNotEqualTo(configuracaoProduto2);
		configuracaoProduto1.setId(null);
		assertThat(configuracaoProduto1).isNotEqualTo(configuracaoProduto2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(ConfiguracaoProdutoDTO.class);
		ConfiguracaoProdutoDTO configuracaoProdutoDTO1 = new ConfiguracaoProdutoDTO();
		configuracaoProdutoDTO1.setId(1L);
		ConfiguracaoProdutoDTO configuracaoProdutoDTO2 = new ConfiguracaoProdutoDTO();
		assertThat(configuracaoProdutoDTO1).isNotEqualTo(configuracaoProdutoDTO2);
		configuracaoProdutoDTO2.setId(configuracaoProdutoDTO1.getId());
		assertThat(configuracaoProdutoDTO1).isEqualTo(configuracaoProdutoDTO2);
		configuracaoProdutoDTO2.setId(2L);
		assertThat(configuracaoProdutoDTO1).isNotEqualTo(configuracaoProdutoDTO2);
		configuracaoProdutoDTO1.setId(null);
		assertThat(configuracaoProdutoDTO1).isNotEqualTo(configuracaoProdutoDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.configuracaoProdutoMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.configuracaoProdutoMapper.fromId(null)).isNull();
	}
}
