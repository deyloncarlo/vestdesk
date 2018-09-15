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
import br.com.vestdesk.domain.EtapaProducao;
import br.com.vestdesk.repository.EtapaProducaoRepository;
import br.com.vestdesk.service.EtapaProducaoService;
import br.com.vestdesk.service.dto.EtapaProducaoDTO;
import br.com.vestdesk.service.mapper.EtapaProducaoMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the EtapaProducaoResource REST controller.
 *
 * @see EtapaProducaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class EtapaProducaoResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

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
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final EtapaProducaoResource etapaProducaoResource = new EtapaProducaoResource(this.etapaProducaoService);
		this.restEtapaProducaoMockMvc = MockMvcBuilders.standaloneSetup(etapaProducaoResource)
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
	public static EtapaProducao createEntity(EntityManager em)
	{
		EtapaProducao etapaProducao = new EtapaProducao().nome(DEFAULT_NOME).prazoExecucao(DEFAULT_PRAZO_EXECUCAO);
		return etapaProducao;
	}

	@Before
	public void initTest()
	{
		this.etapaProducao = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createEtapaProducao() throws Exception
	{
		int databaseSizeBeforeCreate = this.etapaProducaoRepository.findAll().size();

		// Create the EtapaProducao
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);
		this.restEtapaProducaoMockMvc.perform(post("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO))).andExpect(status().isCreated());

		// Validate the EtapaProducao in the database
		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeCreate + 1);
		EtapaProducao testEtapaProducao = etapaProducaoList.get(etapaProducaoList.size() - 1);
		assertThat(testEtapaProducao.getNome()).isEqualTo(DEFAULT_NOME);
		assertThat(testEtapaProducao.getPrazoExecucao()).isEqualTo(DEFAULT_PRAZO_EXECUCAO);
	}

	@Test
	@Transactional
	public void createEtapaProducaoWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.etapaProducaoRepository.findAll().size();

		// Create the EtapaProducao with an existing ID
		this.etapaProducao.setId(1L);
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restEtapaProducaoMockMvc
				.perform(post("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EtapaProducao in the database
		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.etapaProducaoRepository.findAll().size();
		// set the field null

		// Create the EtapaProducao, which fails.
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);

		this.restEtapaProducaoMockMvc
				.perform(post("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
				.andExpect(status().isBadRequest());

		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNomeIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.etapaProducaoRepository.findAll().size();
		// set the field null
		this.etapaProducao.setNome(null);

		// Create the EtapaProducao, which fails.
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);

		this.restEtapaProducaoMockMvc
				.perform(post("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
				.andExpect(status().isBadRequest());

		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkPrazoExecucaoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.etapaProducaoRepository.findAll().size();
		// set the field null
		this.etapaProducao.setPrazoExecucao(null);

		// Create the EtapaProducao, which fails.
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);

		this.restEtapaProducaoMockMvc
				.perform(post("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO)))
				.andExpect(status().isBadRequest());

		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllEtapaProducaos() throws Exception
	{
		// Initialize the database
		this.etapaProducaoRepository.saveAndFlush(this.etapaProducao);

		// Get all the etapaProducaoList
		this.restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.etapaProducao.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
				.andExpect(jsonPath("$.[*].prazoExecucao").value(hasItem(DEFAULT_PRAZO_EXECUCAO)));
	}

	@Test
	@Transactional
	public void getEtapaProducao() throws Exception
	{
		// Initialize the database
		this.etapaProducaoRepository.saveAndFlush(this.etapaProducao);

		// Get the etapaProducao
		this.restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos/{id}", this.etapaProducao.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.etapaProducao.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
				.andExpect(jsonPath("$.prazoExecucao").value(DEFAULT_PRAZO_EXECUCAO));
	}

	@Test
	@Transactional
	public void getNonExistingEtapaProducao() throws Exception
	{
		// Get the etapaProducao
		this.restEtapaProducaoMockMvc.perform(get("/api/etapa-producaos/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateEtapaProducao() throws Exception
	{
		// Initialize the database
		this.etapaProducaoRepository.saveAndFlush(this.etapaProducao);
		int databaseSizeBeforeUpdate = this.etapaProducaoRepository.findAll().size();

		// Update the etapaProducao
		EtapaProducao updatedEtapaProducao = this.etapaProducaoRepository.findOne(this.etapaProducao.getId());
		// Disconnect from session so that the updates on updatedEtapaProducao
		// are not directly saved in db
		this.em.detach(updatedEtapaProducao);
		updatedEtapaProducao.nome(UPDATED_NOME).prazoExecucao(UPDATED_PRAZO_EXECUCAO);
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(updatedEtapaProducao);

		this.restEtapaProducaoMockMvc.perform(put("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO))).andExpect(status().isOk());

		// Validate the EtapaProducao in the database
		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeUpdate);
		EtapaProducao testEtapaProducao = etapaProducaoList.get(etapaProducaoList.size() - 1);
		assertThat(testEtapaProducao.getNome()).isEqualTo(UPDATED_NOME);
		assertThat(testEtapaProducao.getPrazoExecucao()).isEqualTo(UPDATED_PRAZO_EXECUCAO);
	}

	@Test
	@Transactional
	public void updateNonExistingEtapaProducao() throws Exception
	{
		int databaseSizeBeforeUpdate = this.etapaProducaoRepository.findAll().size();

		// Create the EtapaProducao
		EtapaProducaoDTO etapaProducaoDTO = this.etapaProducaoMapper.toDto(this.etapaProducao);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restEtapaProducaoMockMvc.perform(put("/api/etapa-producaos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(etapaProducaoDTO))).andExpect(status().isCreated());

		// Validate the EtapaProducao in the database
		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteEtapaProducao() throws Exception
	{
		// Initialize the database
		this.etapaProducaoRepository.saveAndFlush(this.etapaProducao);
		int databaseSizeBeforeDelete = this.etapaProducaoRepository.findAll().size();

		// Get the etapaProducao
		this.restEtapaProducaoMockMvc.perform(
				delete("/api/etapa-producaos/{id}", this.etapaProducao.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<EtapaProducao> etapaProducaoList = this.etapaProducaoRepository.findAll();
		assertThat(etapaProducaoList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
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
	public void dtoEqualsVerifier() throws Exception
	{
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
	public void testEntityFromId()
	{
		assertThat(this.etapaProducaoMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.etapaProducaoMapper.fromId(null)).isNull();
	}
}
