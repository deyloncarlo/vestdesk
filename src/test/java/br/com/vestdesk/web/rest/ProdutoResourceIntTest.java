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
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.repository.ProdutoRepository;
import br.com.vestdesk.service.CorService;
import br.com.vestdesk.service.ProdutoService;
import br.com.vestdesk.service.dto.ProdutoDTO;
import br.com.vestdesk.service.mapper.ProdutoMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class ProdutoResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final Integer DEFAULT_QUANTIDADE_ESTOQUE = 1;
	private static final Integer UPDATED_QUANTIDADE_ESTOQUE = 2;

	private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
	private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoMapper produtoMapper;

	@Autowired
	private ProdutoService produtoService;

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

	private MockMvc restProdutoMockMvc;

	private Produto produto;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final ProdutoResource produtoResource = new ProdutoResource(this.produtoService, this.produtoMapper,
				this.corService);
		this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
	public static Produto createEntity(EntityManager em)
	{
		Produto produto = new Produto().quantidadeEstoque(DEFAULT_QUANTIDADE_ESTOQUE).descricao(DEFAULT_DESCRICAO);
		return produto;
	}

	@Before
	public void initTest()
	{
		this.produto = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createProduto() throws Exception
	{
		int databaseSizeBeforeCreate = this.produtoRepository.findAll().size();

		// Create the Produto
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);
		this.restProdutoMockMvc.perform(post("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isCreated());

		// Validate the Produto in the database
		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
		Produto testProduto = produtoList.get(produtoList.size() - 1);
		assertThat(testProduto.getQuantidadeEstoque()).isEqualTo(DEFAULT_QUANTIDADE_ESTOQUE);
		assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
	}

	@Test
	@Transactional
	public void createProdutoWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.produtoRepository.findAll().size();

		// Create the Produto with an existing ID
		this.produto.setId(1L);
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restProdutoMockMvc.perform(post("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isBadRequest());

		// Validate the Produto in the database
		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.produtoRepository.findAll().size();
		// set the field null

		// Create the Produto, which fails.
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);

		this.restProdutoMockMvc.perform(post("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isBadRequest());

		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkQuantidadeEstoqueIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.produtoRepository.findAll().size();
		// set the field null
		this.produto.setQuantidadeEstoque(null);

		// Create the Produto, which fails.
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);

		this.restProdutoMockMvc.perform(post("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isBadRequest());

		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkDescricaoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.produtoRepository.findAll().size();
		// set the field null
		this.produto.setDescricao(null);

		// Create the Produto, which fails.
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);

		this.restProdutoMockMvc.perform(post("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isBadRequest());

		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllProdutos() throws Exception
	{
		// Initialize the database
		this.produtoRepository.saveAndFlush(this.produto);

		// Get all the produtoList
		this.restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.produto.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].quantidadeEstoque").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE)))
				.andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
	}

	@Test
	@Transactional
	public void getProduto() throws Exception
	{
		// Initialize the database
		this.produtoRepository.saveAndFlush(this.produto);

		// Get the produto
		this.restProdutoMockMvc.perform(get("/api/produtos/{id}", this.produto.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.produto.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.quantidadeEstoque").value(DEFAULT_QUANTIDADE_ESTOQUE))
				.andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingProduto() throws Exception
	{
		// Get the produto
		this.restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateProduto() throws Exception
	{
		// Initialize the database
		this.produtoRepository.saveAndFlush(this.produto);
		int databaseSizeBeforeUpdate = this.produtoRepository.findAll().size();

		// Update the produto
		Produto updatedProduto = this.produtoRepository.findOne(this.produto.getId());
		// Disconnect from session so that the updates on updatedProduto are not
		// directly saved in db
		this.em.detach(updatedProduto);
		updatedProduto.quantidadeEstoque(UPDATED_QUANTIDADE_ESTOQUE).descricao(UPDATED_DESCRICAO);
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(updatedProduto);

		this.restProdutoMockMvc.perform(put("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isOk());

		// Validate the Produto in the database
		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
		Produto testProduto = produtoList.get(produtoList.size() - 1);
		assertThat(testProduto.getQuantidadeEstoque()).isEqualTo(UPDATED_QUANTIDADE_ESTOQUE);
		assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
	}

	@Test
	@Transactional
	public void updateNonExistingProduto() throws Exception
	{
		int databaseSizeBeforeUpdate = this.produtoRepository.findAll().size();

		// Create the Produto
		ProdutoDTO produtoDTO = this.produtoMapper.toDto(this.produto);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restProdutoMockMvc.perform(put("/api/produtos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(produtoDTO))).andExpect(status().isCreated());

		// Validate the Produto in the database
		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteProduto() throws Exception
	{
		// Initialize the database
		this.produtoRepository.saveAndFlush(this.produto);
		int databaseSizeBeforeDelete = this.produtoRepository.findAll().size();

		// Get the produto
		this.restProdutoMockMvc
				.perform(delete("/api/produtos/{id}", this.produto.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Produto> produtoList = this.produtoRepository.findAll();
		assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Produto.class);
		Produto produto1 = new Produto();
		produto1.setId(1L);
		Produto produto2 = new Produto();
		produto2.setId(produto1.getId());
		assertThat(produto1).isEqualTo(produto2);
		produto2.setId(2L);
		assertThat(produto1).isNotEqualTo(produto2);
		produto1.setId(null);
		assertThat(produto1).isNotEqualTo(produto2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(ProdutoDTO.class);
		ProdutoDTO produtoDTO1 = new ProdutoDTO();
		produtoDTO1.setId(1L);
		ProdutoDTO produtoDTO2 = new ProdutoDTO();
		assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
		produtoDTO2.setId(produtoDTO1.getId());
		assertThat(produtoDTO1).isEqualTo(produtoDTO2);
		produtoDTO2.setId(2L);
		assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
		produtoDTO1.setId(null);
		assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.produtoMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.produtoMapper.fromId(null)).isNull();
	}
}
