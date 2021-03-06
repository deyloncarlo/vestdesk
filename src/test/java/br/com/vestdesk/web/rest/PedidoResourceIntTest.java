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

import java.time.LocalDate;
import java.time.ZoneId;
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
import br.com.vestdesk.domain.Pedido;
import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoPedido;
import br.com.vestdesk.repository.PedidoRepository;
import br.com.vestdesk.service.PedidoService;
import br.com.vestdesk.service.dto.PedidoDTO;
import br.com.vestdesk.service.mapper.PedidoMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the PedidoResource REST controller.
 *
 * @see PedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class PedidoResourceIntTest
{

	private static final String DEFAULT_NOME = "AAAAAAAAAA";
	private static final String UPDATED_NOME = "BBBBBBBBBB";

	private static final TipoPedido DEFAULT_TIPO_PEDIDO = TipoPedido.PRODUCAO;
	private static final TipoPedido UPDATED_TIPO_PEDIDO = TipoPedido.VENDA;

	private static final TipoEstampa DEFAULT_TIPO_ESTAMPA_FRENTE = TipoEstampa.BORDADO;
	private static final TipoEstampa UPDATED_TIPO_ESTAMPA_FRENTE = TipoEstampa.SILK;

	private static final TipoEstampa DEFAULT_TIPO_ESTAMPA_COSTA = TipoEstampa.BORDADO;
	private static final TipoEstampa UPDATED_TIPO_ESTAMPA_COSTA = TipoEstampa.SILK;

	private static final TipoEstampa DEFAULT_TIPO_ESTAMPA_MANGA_DIREITA = TipoEstampa.BORDADO;
	private static final TipoEstampa UPDATED_TIPO_ESTAMPA_MANGA_DIREITA = TipoEstampa.SILK;

	private static final TipoEstampa DEFAULT_TIPO_ESTAMPA_MANGA_ESQUERDA = TipoEstampa.BORDADO;
	private static final TipoEstampa UPDATED_TIPO_ESTAMPA_MANGA_ESQUERDA = TipoEstampa.SILK;

	private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoMapper pedidoMapper;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restPedidoMockMvc;

	private Pedido pedido;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final PedidoResource pedidoResource = new PedidoResource(this.pedidoService);
		this.restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
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
	public static Pedido createEntity(EntityManager em)
	{
		Pedido pedido = new Pedido().tipoPedido(DEFAULT_TIPO_PEDIDO).tipoEstampaFrente(DEFAULT_TIPO_ESTAMPA_FRENTE)
				.tipoEstampaCosta(DEFAULT_TIPO_ESTAMPA_COSTA)
				.tipoEstampaMangaDireita(DEFAULT_TIPO_ESTAMPA_MANGA_DIREITA)
				.tipoEstampaMangaEsquerda(DEFAULT_TIPO_ESTAMPA_MANGA_ESQUERDA).dataCriacao(DEFAULT_DATA_CRIACAO);
		return pedido;
	}

	@Before
	public void initTest()
	{
		this.pedido = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createPedido() throws Exception
	{
		int databaseSizeBeforeCreate = this.pedidoRepository.findAll().size();

		// Create the Pedido
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(this.pedido);
		this.restPedidoMockMvc.perform(post("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isCreated());

		// Validate the Pedido in the database
		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
		Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
		assertThat(testPedido.getTipoPedido()).isEqualTo(DEFAULT_TIPO_PEDIDO);
		assertThat(testPedido.getTipoEstampaFrente()).isEqualTo(DEFAULT_TIPO_ESTAMPA_FRENTE);
		assertThat(testPedido.getTipoEstampaCosta()).isEqualTo(DEFAULT_TIPO_ESTAMPA_COSTA);
		assertThat(testPedido.getTipoEstampaMangaDireita()).isEqualTo(DEFAULT_TIPO_ESTAMPA_MANGA_DIREITA);
		assertThat(testPedido.getTipoEstampaMangaEsquerda()).isEqualTo(DEFAULT_TIPO_ESTAMPA_MANGA_ESQUERDA);
		assertThat(testPedido.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
	}

	@Test
	@Transactional
	public void createPedidoWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.pedidoRepository.findAll().size();

		// Create the Pedido with an existing ID
		this.pedido.setId(1L);
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(this.pedido);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restPedidoMockMvc.perform(post("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isBadRequest());

		// Validate the Pedido in the database
		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkNomeIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.pedidoRepository.findAll().size();
		// set the field null

		// Create the Pedido, which fails.
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(this.pedido);

		this.restPedidoMockMvc.perform(post("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isBadRequest());

		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkTipoPedidoIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.pedidoRepository.findAll().size();
		// set the field null
		this.pedido.setTipoPedido(null);

		// Create the Pedido, which fails.
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(this.pedido);

		this.restPedidoMockMvc.perform(post("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isBadRequest());

		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllPedidos() throws Exception
	{
		// Initialize the database
		this.pedidoRepository.saveAndFlush(this.pedido);

		// Get all the pedidoList
		this.restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.pedido.getId().intValue())))
				.andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
				.andExpect(jsonPath("$.[*].tipoPedido").value(hasItem(DEFAULT_TIPO_PEDIDO.toString())))
				.andExpect(jsonPath("$.[*].tipoEstampaFrente").value(hasItem(DEFAULT_TIPO_ESTAMPA_FRENTE.toString())))
				.andExpect(jsonPath("$.[*].tipoEstampaCosta").value(hasItem(DEFAULT_TIPO_ESTAMPA_COSTA.toString())))
				.andExpect(jsonPath("$.[*].tipoEstampaMangaDireita")
						.value(hasItem(DEFAULT_TIPO_ESTAMPA_MANGA_DIREITA.toString())))
				.andExpect(jsonPath("$.[*].tipoEstampaMangaEsquerda")
						.value(hasItem(DEFAULT_TIPO_ESTAMPA_MANGA_ESQUERDA.toString())))
				.andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
	}

	@Test
	@Transactional
	public void getPedido() throws Exception
	{
		// Initialize the database
		this.pedidoRepository.saveAndFlush(this.pedido);

		// Get the pedido
		this.restPedidoMockMvc.perform(get("/api/pedidos/{id}", this.pedido.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.pedido.getId().intValue()))
				.andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
				.andExpect(jsonPath("$.tipoPedido").value(DEFAULT_TIPO_PEDIDO.toString()))
				.andExpect(jsonPath("$.tipoEstampaFrente").value(DEFAULT_TIPO_ESTAMPA_FRENTE.toString()))
				.andExpect(jsonPath("$.tipoEstampaCosta").value(DEFAULT_TIPO_ESTAMPA_COSTA.toString()))
				.andExpect(jsonPath("$.tipoEstampaMangaDireita").value(DEFAULT_TIPO_ESTAMPA_MANGA_DIREITA.toString()))
				.andExpect(jsonPath("$.tipoEstampaMangaEsquerda").value(DEFAULT_TIPO_ESTAMPA_MANGA_ESQUERDA.toString()))
				.andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingPedido() throws Exception
	{
		// Get the pedido
		this.restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updatePedido() throws Exception
	{
		// Initialize the database
		this.pedidoRepository.saveAndFlush(this.pedido);
		int databaseSizeBeforeUpdate = this.pedidoRepository.findAll().size();

		// Update the pedido
		Pedido updatedPedido = this.pedidoRepository.findOne(this.pedido.getId());
		// Disconnect from session so that the updates on updatedPedido are not
		// directly saved in db
		this.em.detach(updatedPedido);
		updatedPedido.tipoPedido(UPDATED_TIPO_PEDIDO).tipoEstampaFrente(UPDATED_TIPO_ESTAMPA_FRENTE)
				.tipoEstampaCosta(UPDATED_TIPO_ESTAMPA_COSTA)
				.tipoEstampaMangaDireita(UPDATED_TIPO_ESTAMPA_MANGA_DIREITA)
				.tipoEstampaMangaEsquerda(UPDATED_TIPO_ESTAMPA_MANGA_ESQUERDA).dataCriacao(UPDATED_DATA_CRIACAO);
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(updatedPedido);

		this.restPedidoMockMvc.perform(put("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isOk());

		// Validate the Pedido in the database
		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
		Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
		assertThat(testPedido.getTipoPedido()).isEqualTo(UPDATED_TIPO_PEDIDO);
		assertThat(testPedido.getTipoEstampaFrente()).isEqualTo(UPDATED_TIPO_ESTAMPA_FRENTE);
		assertThat(testPedido.getTipoEstampaCosta()).isEqualTo(UPDATED_TIPO_ESTAMPA_COSTA);
		assertThat(testPedido.getTipoEstampaMangaDireita()).isEqualTo(UPDATED_TIPO_ESTAMPA_MANGA_DIREITA);
		assertThat(testPedido.getTipoEstampaMangaEsquerda()).isEqualTo(UPDATED_TIPO_ESTAMPA_MANGA_ESQUERDA);
		assertThat(testPedido.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
	}

	@Test
	@Transactional
	public void updateNonExistingPedido() throws Exception
	{
		int databaseSizeBeforeUpdate = this.pedidoRepository.findAll().size();

		// Create the Pedido
		PedidoDTO pedidoDTO = this.pedidoMapper.toDto(this.pedido);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restPedidoMockMvc.perform(put("/api/pedidos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(pedidoDTO))).andExpect(status().isCreated());

		// Validate the Pedido in the database
		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deletePedido() throws Exception
	{
		// Initialize the database
		this.pedidoRepository.saveAndFlush(this.pedido);
		int databaseSizeBeforeDelete = this.pedidoRepository.findAll().size();

		// Get the pedido
		this.restPedidoMockMvc
				.perform(delete("/api/pedidos/{id}", this.pedido.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Pedido> pedidoList = this.pedidoRepository.findAll();
		assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Pedido.class);
		Pedido pedido1 = new Pedido();
		pedido1.setId(1L);
		Pedido pedido2 = new Pedido();
		pedido2.setId(pedido1.getId());
		assertThat(pedido1).isEqualTo(pedido2);
		pedido2.setId(2L);
		assertThat(pedido1).isNotEqualTo(pedido2);
		pedido1.setId(null);
		assertThat(pedido1).isNotEqualTo(pedido2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(PedidoDTO.class);
		PedidoDTO pedidoDTO1 = new PedidoDTO();
		pedidoDTO1.setId(1L);
		PedidoDTO pedidoDTO2 = new PedidoDTO();
		assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
		pedidoDTO2.setId(pedidoDTO1.getId());
		assertThat(pedidoDTO1).isEqualTo(pedidoDTO2);
		pedidoDTO2.setId(2L);
		assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
		pedidoDTO1.setId(null);
		assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.pedidoMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.pedidoMapper.fromId(null)).isNull();
	}
}
