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
import br.com.vestdesk.domain.Material;
import br.com.vestdesk.repository.MaterialRepository;
import br.com.vestdesk.service.MaterialService;
import br.com.vestdesk.service.dto.MaterialDTO;
import br.com.vestdesk.service.mapper.MaterialMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the MaterialResource REST controller.
 *
 * @see MaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class MaterialResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final String DEFAULT_NOME = "AAAAAAAAAA";
	private static final String UPDATED_NOME = "BBBBBBBBBB";

	private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
	private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

	private static final Float DEFAULT_QUANTIDADE_ESTOQUE = 1F;
	private static final Float UPDATED_QUANTIDADE_ESTOQUE = 2F;

	private static final Float DEFAULT_QUANTIDADE_MINIMA = 1F;
	private static final Float UPDATED_QUANTIDADE_MINIMA = 2F;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MaterialMapper materialMapper;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restMaterialMockMvc;

	private Material material;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final MaterialResource materialResource = new MaterialResource(this.materialService);
		this.restMaterialMockMvc = MockMvcBuilders.standaloneSetup(materialResource)
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
	public static Material createEntity(EntityManager em)
	{
		Material material = new Material().nome(DEFAULT_NOME).preco(DEFAULT_PRECO)
				.quantidadeEstoque(DEFAULT_QUANTIDADE_ESTOQUE).quantidadeMinima(DEFAULT_QUANTIDADE_MINIMA);
		return material;
	}

	@Before
	public void initTest()
	{
		this.material = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createMaterial() throws Exception
	{
		int databaseSizeBeforeCreate = this.materialRepository.findAll().size();

		// Create the Material
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);
		this.restMaterialMockMvc.perform(post("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isCreated());

		// Validate the Material in the database
		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
		Material testMaterial = materialList.get(materialList.size() - 1);
		assertThat(testMaterial.getNome()).isEqualTo(DEFAULT_NOME);
		assertThat(testMaterial.getPreco()).isEqualTo(DEFAULT_PRECO);
		assertThat(testMaterial.getQuantidadeEstoque()).isEqualTo(DEFAULT_QUANTIDADE_ESTOQUE);
		assertThat(testMaterial.getQuantidadeMinima()).isEqualTo(DEFAULT_QUANTIDADE_MINIMA);
	}

	@Test
	@Transactional
	public void createMaterialWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.materialRepository.findAll().size();

		// Create the Material with an existing ID
		this.material.setId(1L);
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restMaterialMockMvc.perform(post("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isBadRequest());

		// Validate the Material in the database
		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.materialRepository.findAll().size();
		// set the field null

		// Create the Material, which fails.
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);

		this.restMaterialMockMvc.perform(post("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isBadRequest());

		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNomeIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.materialRepository.findAll().size();
		// set the field null
		this.material.setNome(null);

		// Create the Material, which fails.
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);

		this.restMaterialMockMvc.perform(post("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isBadRequest());

		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkPrecoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.materialRepository.findAll().size();
		// set the field null
		this.material.setPreco(null);

		// Create the Material, which fails.
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);

		this.restMaterialMockMvc.perform(post("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isBadRequest());

		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllMaterials() throws Exception
	{
		// Initialize the database
		this.materialRepository.saveAndFlush(this.material);

		// Get all the materialList
		this.restMaterialMockMvc.perform(get("/api/materials?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.material.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
				.andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())))
				.andExpect(jsonPath("$.[*].quantidadeEstoque").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE.doubleValue())))
				.andExpect(jsonPath("$.[*].quantidadeMinima").value(hasItem(DEFAULT_QUANTIDADE_MINIMA.doubleValue())));
	}

	@Test
	@Transactional
	public void getMaterial() throws Exception
	{
		// Initialize the database
		this.materialRepository.saveAndFlush(this.material);

		// Get the material
		this.restMaterialMockMvc.perform(get("/api/materials/{id}", this.material.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.material.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
				.andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()))
				.andExpect(jsonPath("$.quantidadeEstoque").value(DEFAULT_QUANTIDADE_ESTOQUE.doubleValue()))
				.andExpect(jsonPath("$.quantidadeMinima").value(DEFAULT_QUANTIDADE_MINIMA.doubleValue()));
	}

	@Test
	@Transactional
	public void getNonExistingMaterial() throws Exception
	{
		// Get the material
		this.restMaterialMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateMaterial() throws Exception
	{
		// Initialize the database
		this.materialRepository.saveAndFlush(this.material);
		int databaseSizeBeforeUpdate = this.materialRepository.findAll().size();

		// Update the material
		Material updatedMaterial = this.materialRepository.findOne(this.material.getId());
		// Disconnect from session so that the updates on updatedMaterial are
		// not directly saved in db
		this.em.detach(updatedMaterial);
		updatedMaterial.nome(UPDATED_NOME).preco(UPDATED_PRECO).quantidadeEstoque(UPDATED_QUANTIDADE_ESTOQUE)
				.quantidadeMinima(UPDATED_QUANTIDADE_MINIMA);
		MaterialDTO materialDTO = this.materialMapper.toDto(updatedMaterial);

		this.restMaterialMockMvc.perform(put("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isOk());

		// Validate the Material in the database
		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
		Material testMaterial = materialList.get(materialList.size() - 1);
		assertThat(testMaterial.getNome()).isEqualTo(UPDATED_NOME);
		assertThat(testMaterial.getPreco()).isEqualTo(UPDATED_PRECO);
		assertThat(testMaterial.getQuantidadeEstoque()).isEqualTo(UPDATED_QUANTIDADE_ESTOQUE);
		assertThat(testMaterial.getQuantidadeMinima()).isEqualTo(UPDATED_QUANTIDADE_MINIMA);
	}

	@Test
	@Transactional
	public void updateNonExistingMaterial() throws Exception
	{
		int databaseSizeBeforeUpdate = this.materialRepository.findAll().size();

		// Create the Material
		MaterialDTO materialDTO = this.materialMapper.toDto(this.material);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restMaterialMockMvc.perform(put("/api/materials").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(materialDTO))).andExpect(status().isCreated());

		// Validate the Material in the database
		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteMaterial() throws Exception
	{
		// Initialize the database
		this.materialRepository.saveAndFlush(this.material);
		int databaseSizeBeforeDelete = this.materialRepository.findAll().size();

		// Get the material
		this.restMaterialMockMvc
				.perform(delete("/api/materials/{id}", this.material.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Material> materialList = this.materialRepository.findAll();
		assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Material.class);
		Material material1 = new Material();
		material1.setId(1L);
		Material material2 = new Material();
		material2.setId(material1.getId());
		assertThat(material1).isEqualTo(material2);
		material2.setId(2L);
		assertThat(material1).isNotEqualTo(material2);
		material1.setId(null);
		assertThat(material1).isNotEqualTo(material2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(MaterialDTO.class);
		MaterialDTO materialDTO1 = new MaterialDTO();
		materialDTO1.setId(1L);
		MaterialDTO materialDTO2 = new MaterialDTO();
		assertThat(materialDTO1).isNotEqualTo(materialDTO2);
		materialDTO2.setId(materialDTO1.getId());
		assertThat(materialDTO1).isEqualTo(materialDTO2);
		materialDTO2.setId(2L);
		assertThat(materialDTO1).isNotEqualTo(materialDTO2);
		materialDTO1.setId(null);
		assertThat(materialDTO1).isNotEqualTo(materialDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.materialMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.materialMapper.fromId(null)).isNull();
	}
}
