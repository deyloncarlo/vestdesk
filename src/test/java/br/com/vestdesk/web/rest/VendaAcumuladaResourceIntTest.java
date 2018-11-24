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
import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.repository.VendaAcumuladaRepository;
import br.com.vestdesk.service.VendaAcumuladaService;
import br.com.vestdesk.service.dto.VendaAcumuladaDTO;
import br.com.vestdesk.service.mapper.VendaAcumuladaMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the VendaAcumuladaResource REST controller.
 *
 * @see VendaAcumuladaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class VendaAcumuladaResourceIntTest
{

	private static final Integer DEFAULT_QUANTIDADE_ACUMULADA = 1;
	private static final Integer UPDATED_QUANTIDADE_ACUMULADA = 2;

	@Autowired
	private VendaAcumuladaRepository vendaAcumuladaRepository;

	@Autowired
	private VendaAcumuladaMapper vendaAcumuladaMapper;

	@Autowired
	private VendaAcumuladaService vendaAcumuladaService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restVendaAcumuladaMockMvc;

	private VendaAcumulada vendaAcumulada;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final VendaAcumuladaResource vendaAcumuladaResource = new VendaAcumuladaResource(this.vendaAcumuladaRepository,
				this.vendaAcumuladaMapper, this.vendaAcumuladaService);
		this.restVendaAcumuladaMockMvc = MockMvcBuilders.standaloneSetup(vendaAcumuladaResource)
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
	public static VendaAcumulada createEntity(EntityManager em)
	{
		VendaAcumulada vendaAcumulada = new VendaAcumulada().quantidadeAcumulada(DEFAULT_QUANTIDADE_ACUMULADA);
		return vendaAcumulada;
	}

	@Before
	public void initTest()
	{
		this.vendaAcumulada = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createVendaAcumulada() throws Exception
	{
		int databaseSizeBeforeCreate = this.vendaAcumuladaRepository.findAll().size();

		// Create the VendaAcumulada
		VendaAcumuladaDTO vendaAcumuladaDTO = this.vendaAcumuladaMapper.toDto(this.vendaAcumulada);
		this.restVendaAcumuladaMockMvc.perform(post("/api/venda-acumuladas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(vendaAcumuladaDTO))).andExpect(status().isCreated());

		// Validate the VendaAcumulada in the database
		List<VendaAcumulada> vendaAcumuladaList = this.vendaAcumuladaRepository.findAll();
		assertThat(vendaAcumuladaList).hasSize(databaseSizeBeforeCreate + 1);
		VendaAcumulada testVendaAcumulada = vendaAcumuladaList.get(vendaAcumuladaList.size() - 1);
		assertThat(testVendaAcumulada.getQuantidadeAcumulada()).isEqualTo(DEFAULT_QUANTIDADE_ACUMULADA);
	}

	@Test
	@Transactional
	public void createVendaAcumuladaWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.vendaAcumuladaRepository.findAll().size();

		// Create the VendaAcumulada with an existing ID
		this.vendaAcumulada.setId(1L);
		VendaAcumuladaDTO vendaAcumuladaDTO = this.vendaAcumuladaMapper.toDto(this.vendaAcumulada);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restVendaAcumuladaMockMvc
				.perform(post("/api/venda-acumuladas").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(vendaAcumuladaDTO)))
				.andExpect(status().isBadRequest());

		// Validate the VendaAcumulada in the database
		List<VendaAcumulada> vendaAcumuladaList = this.vendaAcumuladaRepository.findAll();
		assertThat(vendaAcumuladaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllVendaAcumuladas() throws Exception
	{
		// Initialize the database
		this.vendaAcumuladaRepository.saveAndFlush(this.vendaAcumulada);

		// Get all the vendaAcumuladaList
		this.restVendaAcumuladaMockMvc.perform(get("/api/venda-acumuladas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.vendaAcumulada.getId().intValue())))
				.andExpect(jsonPath("$.[*].quantidadeAcumulada").value(hasItem(DEFAULT_QUANTIDADE_ACUMULADA)));
	}

	@Test
	@Transactional
	public void getVendaAcumulada() throws Exception
	{
		// Initialize the database
		this.vendaAcumuladaRepository.saveAndFlush(this.vendaAcumulada);

		// Get the vendaAcumulada
		this.restVendaAcumuladaMockMvc.perform(get("/api/venda-acumuladas/{id}", this.vendaAcumulada.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.vendaAcumulada.getId().intValue()))
				.andExpect(jsonPath("$.quantidadeAcumulada").value(DEFAULT_QUANTIDADE_ACUMULADA));
	}

	@Test
	@Transactional
	public void getNonExistingVendaAcumulada() throws Exception
	{
		// Get the vendaAcumulada
		this.restVendaAcumuladaMockMvc.perform(get("/api/venda-acumuladas/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateVendaAcumulada() throws Exception
	{
		// Initialize the database
		this.vendaAcumuladaRepository.saveAndFlush(this.vendaAcumulada);
		int databaseSizeBeforeUpdate = this.vendaAcumuladaRepository.findAll().size();

		// Update the vendaAcumulada
		VendaAcumulada updatedVendaAcumulada = this.vendaAcumuladaRepository.findOne(this.vendaAcumulada.getId());
		// Disconnect from session so that the updates on updatedVendaAcumulada
		// are not directly saved in db
		this.em.detach(updatedVendaAcumulada);
		updatedVendaAcumulada.quantidadeAcumulada(UPDATED_QUANTIDADE_ACUMULADA);
		VendaAcumuladaDTO vendaAcumuladaDTO = this.vendaAcumuladaMapper.toDto(updatedVendaAcumulada);

		this.restVendaAcumuladaMockMvc.perform(put("/api/venda-acumuladas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(vendaAcumuladaDTO))).andExpect(status().isOk());

		// Validate the VendaAcumulada in the database
		List<VendaAcumulada> vendaAcumuladaList = this.vendaAcumuladaRepository.findAll();
		assertThat(vendaAcumuladaList).hasSize(databaseSizeBeforeUpdate);
		VendaAcumulada testVendaAcumulada = vendaAcumuladaList.get(vendaAcumuladaList.size() - 1);
		assertThat(testVendaAcumulada.getQuantidadeAcumulada()).isEqualTo(UPDATED_QUANTIDADE_ACUMULADA);
	}

	@Test
	@Transactional
	public void updateNonExistingVendaAcumulada() throws Exception
	{
		int databaseSizeBeforeUpdate = this.vendaAcumuladaRepository.findAll().size();

		// Create the VendaAcumulada
		VendaAcumuladaDTO vendaAcumuladaDTO = this.vendaAcumuladaMapper.toDto(this.vendaAcumulada);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restVendaAcumuladaMockMvc.perform(put("/api/venda-acumuladas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(vendaAcumuladaDTO))).andExpect(status().isCreated());

		// Validate the VendaAcumulada in the database
		List<VendaAcumulada> vendaAcumuladaList = this.vendaAcumuladaRepository.findAll();
		assertThat(vendaAcumuladaList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteVendaAcumulada() throws Exception
	{
		// Initialize the database
		this.vendaAcumuladaRepository.saveAndFlush(this.vendaAcumulada);
		int databaseSizeBeforeDelete = this.vendaAcumuladaRepository.findAll().size();

		// Get the vendaAcumulada
		this.restVendaAcumuladaMockMvc.perform(delete("/api/venda-acumuladas/{id}", this.vendaAcumulada.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

		// Validate the database is empty
		List<VendaAcumulada> vendaAcumuladaList = this.vendaAcumuladaRepository.findAll();
		assertThat(vendaAcumuladaList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(VendaAcumulada.class);
		VendaAcumulada vendaAcumulada1 = new VendaAcumulada();
		vendaAcumulada1.setId(1L);
		VendaAcumulada vendaAcumulada2 = new VendaAcumulada();
		vendaAcumulada2.setId(vendaAcumulada1.getId());
		assertThat(vendaAcumulada1).isEqualTo(vendaAcumulada2);
		vendaAcumulada2.setId(2L);
		assertThat(vendaAcumulada1).isNotEqualTo(vendaAcumulada2);
		vendaAcumulada1.setId(null);
		assertThat(vendaAcumulada1).isNotEqualTo(vendaAcumulada2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(VendaAcumuladaDTO.class);
		VendaAcumuladaDTO vendaAcumuladaDTO1 = new VendaAcumuladaDTO();
		vendaAcumuladaDTO1.setId(1L);
		VendaAcumuladaDTO vendaAcumuladaDTO2 = new VendaAcumuladaDTO();
		assertThat(vendaAcumuladaDTO1).isNotEqualTo(vendaAcumuladaDTO2);
		vendaAcumuladaDTO2.setId(vendaAcumuladaDTO1.getId());
		assertThat(vendaAcumuladaDTO1).isEqualTo(vendaAcumuladaDTO2);
		vendaAcumuladaDTO2.setId(2L);
		assertThat(vendaAcumuladaDTO1).isNotEqualTo(vendaAcumuladaDTO2);
		vendaAcumuladaDTO1.setId(null);
		assertThat(vendaAcumuladaDTO1).isNotEqualTo(vendaAcumuladaDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.vendaAcumuladaMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.vendaAcumuladaMapper.fromId(null)).isNull();
	}
}
