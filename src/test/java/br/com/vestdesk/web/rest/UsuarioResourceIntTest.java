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
import br.com.vestdesk.domain.Usuario;
import br.com.vestdesk.domain.enumeration.Perfil;
import br.com.vestdesk.repository.UsuarioRepository;
import br.com.vestdesk.service.UsuarioService;
import br.com.vestdesk.service.dto.UsuarioDTO;
import br.com.vestdesk.service.mapper.UsuarioMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the UsuarioResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class UsuarioResourceIntTest
{

	private static final Long DEFAULT_OID = 1L;
	private static final Long UPDATED_OID = 2L;

	private static final String DEFAULT_NOME = "AAAAAAAAAA";
	private static final String UPDATED_NOME = "BBBBBBBBBB";

	private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
	private static final String UPDATED_SIGLA = "BBBBBBBBBB";

	private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
	private static final String UPDATED_EMAIL = "BBBBBBBBBB";

	private static final String DEFAULT_SENHA = "AAAAAAAAAA";
	private static final String UPDATED_SENHA = "BBBBBBBBBB";

	private static final Perfil DEFAULT_PERFIL = Perfil.ADMINISTRADOR;
	private static final Perfil UPDATED_PERFIL = Perfil.VENDEDOR;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restUsuarioMockMvc;

	private Usuario usuario;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		final UsuarioResource usuarioResource = new UsuarioResource(this.usuarioService);
		this.restUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usuarioResource)
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
	public static Usuario createEntity(EntityManager em)
	{
		Usuario usuario = new Usuario().nome(DEFAULT_NOME).sigla(DEFAULT_SIGLA).email(DEFAULT_EMAIL)
				.senha(DEFAULT_SENHA).perfil(DEFAULT_PERFIL);
		return usuario;
	}

	@Before
	public void initTest()
	{
		this.usuario = createEntity(this.em);
	}

	@Test
	@Transactional
	public void createUsuario() throws Exception
	{
		int databaseSizeBeforeCreate = this.usuarioRepository.findAll().size();

		// Create the Usuario
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);
		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isCreated());

		// Validate the Usuario in the database
		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
		Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
		assertThat(testUsuario.getNome()).isEqualTo(DEFAULT_NOME);
		assertThat(testUsuario.getSigla()).isEqualTo(DEFAULT_SIGLA);
		assertThat(testUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
		assertThat(testUsuario.getSenha()).isEqualTo(DEFAULT_SENHA);
		assertThat(testUsuario.getPerfil()).isEqualTo(DEFAULT_PERFIL);
	}

	@Test
	@Transactional
	public void createUsuarioWithExistingId() throws Exception
	{
		int databaseSizeBeforeCreate = this.usuarioRepository.findAll().size();

		// Create the Usuario with an existing ID
		this.usuario.setId(1L);
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		// An entity with an existing ID cannot be created, so this API call
		// must fail
		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		// Validate the Usuario in the database
		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkOidIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.usuarioRepository.findAll().size();
		// set the field null

		// Create the Usuario, which fails.
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNomeIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.usuarioRepository.findAll().size();
		// set the field null
		this.usuario.setNome(null);

		// Create the Usuario, which fails.
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkSiglaIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.usuarioRepository.findAll().size();
		// set the field null
		this.usuario.setSigla(null);

		// Create the Usuario, which fails.
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkSenhaIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.usuarioRepository.findAll().size();
		// set the field null
		this.usuario.setSenha(null);

		// Create the Usuario, which fails.
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkPerfilIsRequired() throws Exception
	{
		int databaseSizeBeforeTest = this.usuarioRepository.findAll().size();
		// set the field null
		this.usuario.setPerfil(null);

		// Create the Usuario, which fails.
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		this.restUsuarioMockMvc.perform(post("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isBadRequest());

		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllUsuarios() throws Exception
	{
		// Initialize the database
		this.usuarioRepository.saveAndFlush(this.usuario);

		// Get all the usuarioList
		this.restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(this.usuario.getId().intValue())))
				.andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.intValue())))
				.andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
				.andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
				.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
				.andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
				.andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL.toString())));
	}

	@Test
	@Transactional
	public void getUsuario() throws Exception
	{
		// Initialize the database
		this.usuarioRepository.saveAndFlush(this.usuario);

		// Get the usuario
		this.restUsuarioMockMvc.perform(get("/api/usuarios/{id}", this.usuario.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(this.usuario.getId().intValue()))
				.andExpect(jsonPath("$.oid").value(DEFAULT_OID.intValue()))
				.andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
				.andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
				.andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
				.andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
				.andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingUsuario() throws Exception
	{
		// Get the usuario
		this.restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateUsuario() throws Exception
	{
		// Initialize the database
		this.usuarioRepository.saveAndFlush(this.usuario);
		int databaseSizeBeforeUpdate = this.usuarioRepository.findAll().size();

		// Update the usuario
		Usuario updatedUsuario = this.usuarioRepository.findOne(this.usuario.getId());
		// Disconnect from session so that the updates on updatedUsuario are not
		// directly saved in db
		this.em.detach(updatedUsuario);
		updatedUsuario.nome(UPDATED_NOME).sigla(UPDATED_SIGLA).email(UPDATED_EMAIL).senha(UPDATED_SENHA)
				.perfil(UPDATED_PERFIL);
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(updatedUsuario);

		this.restUsuarioMockMvc.perform(put("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isOk());

		// Validate the Usuario in the database
		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
		Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
		assertThat(testUsuario.getNome()).isEqualTo(UPDATED_NOME);
		assertThat(testUsuario.getSigla()).isEqualTo(UPDATED_SIGLA);
		assertThat(testUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
		assertThat(testUsuario.getSenha()).isEqualTo(UPDATED_SENHA);
		assertThat(testUsuario.getPerfil()).isEqualTo(UPDATED_PERFIL);
	}

	@Test
	@Transactional
	public void updateNonExistingUsuario() throws Exception
	{
		int databaseSizeBeforeUpdate = this.usuarioRepository.findAll().size();

		// Create the Usuario
		UsuarioDTO usuarioDTO = this.usuarioMapper.toDto(this.usuario);

		// If the entity doesn't have an ID, it will be created instead of just
		// being updated
		this.restUsuarioMockMvc.perform(put("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(usuarioDTO))).andExpect(status().isCreated());

		// Validate the Usuario in the database
		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteUsuario() throws Exception
	{
		// Initialize the database
		this.usuarioRepository.saveAndFlush(this.usuario);
		int databaseSizeBeforeDelete = this.usuarioRepository.findAll().size();

		// Get the usuario
		this.restUsuarioMockMvc
				.perform(delete("/api/usuarios/{id}", this.usuario.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Usuario> usuarioList = this.usuarioRepository.findAll();
		assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(Usuario.class);
		Usuario usuario1 = new Usuario();
		usuario1.setId(1L);
		Usuario usuario2 = new Usuario();
		usuario2.setId(usuario1.getId());
		assertThat(usuario1).isEqualTo(usuario2);
		usuario2.setId(2L);
		assertThat(usuario1).isNotEqualTo(usuario2);
		usuario1.setId(null);
		assertThat(usuario1).isNotEqualTo(usuario2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception
	{
		TestUtil.equalsVerifier(UsuarioDTO.class);
		UsuarioDTO usuarioDTO1 = new UsuarioDTO();
		usuarioDTO1.setId(1L);
		UsuarioDTO usuarioDTO2 = new UsuarioDTO();
		assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
		usuarioDTO2.setId(usuarioDTO1.getId());
		assertThat(usuarioDTO1).isEqualTo(usuarioDTO2);
		usuarioDTO2.setId(2L);
		assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
		usuarioDTO1.setId(null);
		assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId()
	{
		assertThat(this.usuarioMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(this.usuarioMapper.fromId(null)).isNull();
	}
}
