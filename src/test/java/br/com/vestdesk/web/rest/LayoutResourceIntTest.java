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
import org.springframework.util.Base64Utils;

import br.com.vestdesk.VestdeskApp;
import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.repository.LayoutRepository;
import br.com.vestdesk.service.LayoutService;
import br.com.vestdesk.service.dto.LayoutDTO;
import br.com.vestdesk.service.mapper.LayoutMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the LayoutResource REST controller.
 *
 * @see LayoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class LayoutResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final String DEFAULT_NOME = "AAAAAAAAAA";
	private static final String UPDATED_NOME = "BBBBBBBBBB";

	private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
	private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(2, "1");
	private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
	private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

	@Autowired
	private LayoutRepository layoutRepository;

	@Autowired
	private LayoutMapper layoutMapper;

	@Autowired
	private LayoutService layoutService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restLayoutMockMvc;

	private Layout layout;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final LayoutResource layoutResource = new LayoutResource(this.layoutService);
		this.restLayoutMockMvc = MockMvcBuilders.standaloneSetup(layoutResource)
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
	public static Layout createEntity(EntityManager em)
	{
		Layout layout = new Layout().nome(DEFAULT_NOME).imagem(DEFAULT_IMAGEM)
				.imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE);
		return layout;
	}

	@Before
	public void initTest()
	{
		this.layout = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createLayout() throws Exception
	{
		int databaseSizeBeforeCreate = this.layoutRepository.findAll().size();

		// Create the Layout
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);
		this.restLayoutMockMvc.perform(post("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isCreated());

		// Validate the Layout in the database
		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeCreate + 1);
		Layout testLayout = layoutList.get(layoutList.size() - 1);
		assertThat(testLayout.getNome()).isEqualTo(DEFAULT_NOME);
		assertThat(testLayout.getImagem()).isEqualTo(DEFAULT_IMAGEM);
		assertThat(testLayout.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
	}

	@Test
	@Transactional
	public void createLayoutWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.layoutRepository.findAll().size();

		// Create the Layout with an existing ID
		this.layout.setId(1L);
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restLayoutMockMvc.perform(post("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isBadRequest());

		// Validate the Layout in the database
		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.layoutRepository.findAll().size();
		// set the field null

		// Create the Layout, which fails.
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);

		this.restLayoutMockMvc.perform(post("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isBadRequest());

		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNomeIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.layoutRepository.findAll().size();
		// set the field null
		this.layout.setNome(null);

		// Create the Layout, which fails.
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);

		this.restLayoutMockMvc.perform(post("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isBadRequest());

		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkImagemIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.layoutRepository.findAll().size();
		// set the field null
		this.layout.setImagem(null);

		// Create the Layout, which fails.
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);

		this.restLayoutMockMvc.perform(post("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isBadRequest());

		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllLayouts() throws Exception
	{
		// Initialize the database
		this.layoutRepository.saveAndFlush(this.layout);

		// Get all the layoutList
		this.restLayoutMockMvc.perform(get("/api/layouts?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.layout.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
				.andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))));
	}

	@Test
	@Transactional
	public void getLayout() throws Exception
	{
		// Initialize the database
		this.layoutRepository.saveAndFlush(this.layout);

		// Get the layout
		this.restLayoutMockMvc.perform(get("/api/layouts/{id}", this.layout.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.layout.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
				.andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
				.andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)));
	}

	@Test
	@Transactional
	public void getNonExistingLayout() throws Exception
	{
		// Get the layout
		this.restLayoutMockMvc.perform(get("/api/layouts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateLayout() throws Exception
	{
		// Initialize the database
		this.layoutRepository.saveAndFlush(this.layout);
		int databaseSizeBeforeUpdate = this.layoutRepository.findAll().size();

		// Update the layout
		Layout updatedLayout = this.layoutRepository.findOne(this.layout.getId());
		// Disconnect from session so that the updates on updatedLayout are not
		// directly saved in db
		this.em.detach(updatedLayout);
		updatedLayout.nome(UPDATED_NOME).imagem(UPDATED_IMAGEM).imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE);
		LayoutDTO layoutDTO = this.layoutMapper.toDto(updatedLayout);

		this.restLayoutMockMvc.perform(put("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isOk());

		// Validate the Layout in the database
		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeUpdate);
		Layout testLayout = layoutList.get(layoutList.size() - 1);
		assertThat(testLayout.getNome()).isEqualTo(UPDATED_NOME);
		assertThat(testLayout.getImagem()).isEqualTo(UPDATED_IMAGEM);
		assertThat(testLayout.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
	}

	@Test
	@Transactional
	public void updateNonExistingLayout() throws Exception
	{
		int databaseSizeBeforeUpdate = this.layoutRepository.findAll().size();

		// Create the Layout
		LayoutDTO layoutDTO = this.layoutMapper.toDto(this.layout);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restLayoutMockMvc.perform(put("/api/layouts").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(layoutDTO))).andExpect(status().isCreated());

		// Validate the Layout in the database
		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteLayout() throws Exception
	{
		// Initialize the database
		this.layoutRepository.saveAndFlush(this.layout);
		int databaseSizeBeforeDelete = this.layoutRepository.findAll().size();

		// Get the layout
		this.restLayoutMockMvc
				.perform(delete("/api/layouts/{id}", this.layout.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Layout> layoutList = this.layoutRepository.findAll();
		assertThat(layoutList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Layout.class);
		Layout layout1 = new Layout();
		layout1.setId(1L);
		Layout layout2 = new Layout();
		layout2.setId(layout1.getId());
		assertThat(layout1).isEqualTo(layout2);
		layout2.setId(2L);
		assertThat(layout1).isNotEqualTo(layout2);
		layout1.setId(null);
		assertThat(layout1).isNotEqualTo(layout2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(LayoutDTO.class);
		LayoutDTO layoutDTO1 = new LayoutDTO();
		layoutDTO1.setId(1L);
		LayoutDTO layoutDTO2 = new LayoutDTO();
		assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
		layoutDTO2.setId(layoutDTO1.getId());
		assertThat(layoutDTO1).isEqualTo(layoutDTO2);
		layoutDTO2.setId(2L);
		assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
		layoutDTO1.setId(null);
		assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.layoutMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.layoutMapper.fromId(null)).isNull();
	}
}
