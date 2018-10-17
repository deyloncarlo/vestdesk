package br.com.vestdesk.web.rest;

import br.com.vestdesk.VestdeskApp;

import br.com.vestdesk.domain.PedidoItem;
import br.com.vestdesk.repository.PedidoItemRepository;
import br.com.vestdesk.service.PedidoItemService;
import br.com.vestdesk.service.dto.PedidoItemDTO;
import br.com.vestdesk.service.mapper.PedidoItemMapper;
import br.com.vestdesk.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.vestdesk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PedidoItemResource REST controller.
 *
 * @see PedidoItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VestdeskApp.class)
public class PedidoItemResourceIntTest {

    private static final String DEFAULT_NOME_ROUPA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ROUPA = "BBBBBBBBBB";

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private PedidoItemMapper pedidoItemMapper;

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPedidoItemMockMvc;

    private PedidoItem pedidoItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoItemResource pedidoItemResource = new PedidoItemResource(pedidoItemService);
        this.restPedidoItemMockMvc = MockMvcBuilders.standaloneSetup(pedidoItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidoItem createEntity(EntityManager em) {
        PedidoItem pedidoItem = new PedidoItem()
            .nomeRoupa(DEFAULT_NOME_ROUPA);
        return pedidoItem;
    }

    @Before
    public void initTest() {
        pedidoItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedidoItem() throws Exception {
        int databaseSizeBeforeCreate = pedidoItemRepository.findAll().size();

        // Create the PedidoItem
        PedidoItemDTO pedidoItemDTO = pedidoItemMapper.toDto(pedidoItem);
        restPedidoItemMockMvc.perform(post("/api/pedido-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PedidoItem in the database
        List<PedidoItem> pedidoItemList = pedidoItemRepository.findAll();
        assertThat(pedidoItemList).hasSize(databaseSizeBeforeCreate + 1);
        PedidoItem testPedidoItem = pedidoItemList.get(pedidoItemList.size() - 1);
        assertThat(testPedidoItem.getNomeRoupa()).isEqualTo(DEFAULT_NOME_ROUPA);
    }

    @Test
    @Transactional
    public void createPedidoItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoItemRepository.findAll().size();

        // Create the PedidoItem with an existing ID
        pedidoItem.setId(1L);
        PedidoItemDTO pedidoItemDTO = pedidoItemMapper.toDto(pedidoItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoItemMockMvc.perform(post("/api/pedido-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PedidoItem in the database
        List<PedidoItem> pedidoItemList = pedidoItemRepository.findAll();
        assertThat(pedidoItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPedidoItems() throws Exception {
        // Initialize the database
        pedidoItemRepository.saveAndFlush(pedidoItem);

        // Get all the pedidoItemList
        restPedidoItemMockMvc.perform(get("/api/pedido-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedidoItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeRoupa").value(hasItem(DEFAULT_NOME_ROUPA.toString())));
    }

    @Test
    @Transactional
    public void getPedidoItem() throws Exception {
        // Initialize the database
        pedidoItemRepository.saveAndFlush(pedidoItem);

        // Get the pedidoItem
        restPedidoItemMockMvc.perform(get("/api/pedido-items/{id}", pedidoItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedidoItem.getId().intValue()))
            .andExpect(jsonPath("$.nomeRoupa").value(DEFAULT_NOME_ROUPA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPedidoItem() throws Exception {
        // Get the pedidoItem
        restPedidoItemMockMvc.perform(get("/api/pedido-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedidoItem() throws Exception {
        // Initialize the database
        pedidoItemRepository.saveAndFlush(pedidoItem);
        int databaseSizeBeforeUpdate = pedidoItemRepository.findAll().size();

        // Update the pedidoItem
        PedidoItem updatedPedidoItem = pedidoItemRepository.findOne(pedidoItem.getId());
        // Disconnect from session so that the updates on updatedPedidoItem are not directly saved in db
        em.detach(updatedPedidoItem);
        updatedPedidoItem
            .nomeRoupa(UPDATED_NOME_ROUPA);
        PedidoItemDTO pedidoItemDTO = pedidoItemMapper.toDto(updatedPedidoItem);

        restPedidoItemMockMvc.perform(put("/api/pedido-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoItemDTO)))
            .andExpect(status().isOk());

        // Validate the PedidoItem in the database
        List<PedidoItem> pedidoItemList = pedidoItemRepository.findAll();
        assertThat(pedidoItemList).hasSize(databaseSizeBeforeUpdate);
        PedidoItem testPedidoItem = pedidoItemList.get(pedidoItemList.size() - 1);
        assertThat(testPedidoItem.getNomeRoupa()).isEqualTo(UPDATED_NOME_ROUPA);
    }

    @Test
    @Transactional
    public void updateNonExistingPedidoItem() throws Exception {
        int databaseSizeBeforeUpdate = pedidoItemRepository.findAll().size();

        // Create the PedidoItem
        PedidoItemDTO pedidoItemDTO = pedidoItemMapper.toDto(pedidoItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPedidoItemMockMvc.perform(put("/api/pedido-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PedidoItem in the database
        List<PedidoItem> pedidoItemList = pedidoItemRepository.findAll();
        assertThat(pedidoItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePedidoItem() throws Exception {
        // Initialize the database
        pedidoItemRepository.saveAndFlush(pedidoItem);
        int databaseSizeBeforeDelete = pedidoItemRepository.findAll().size();

        // Get the pedidoItem
        restPedidoItemMockMvc.perform(delete("/api/pedido-items/{id}", pedidoItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PedidoItem> pedidoItemList = pedidoItemRepository.findAll();
        assertThat(pedidoItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoItem.class);
        PedidoItem pedidoItem1 = new PedidoItem();
        pedidoItem1.setId(1L);
        PedidoItem pedidoItem2 = new PedidoItem();
        pedidoItem2.setId(pedidoItem1.getId());
        assertThat(pedidoItem1).isEqualTo(pedidoItem2);
        pedidoItem2.setId(2L);
        assertThat(pedidoItem1).isNotEqualTo(pedidoItem2);
        pedidoItem1.setId(null);
        assertThat(pedidoItem1).isNotEqualTo(pedidoItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoItemDTO.class);
        PedidoItemDTO pedidoItemDTO1 = new PedidoItemDTO();
        pedidoItemDTO1.setId(1L);
        PedidoItemDTO pedidoItemDTO2 = new PedidoItemDTO();
        assertThat(pedidoItemDTO1).isNotEqualTo(pedidoItemDTO2);
        pedidoItemDTO2.setId(pedidoItemDTO1.getId());
        assertThat(pedidoItemDTO1).isEqualTo(pedidoItemDTO2);
        pedidoItemDTO2.setId(2L);
        assertThat(pedidoItemDTO1).isNotEqualTo(pedidoItemDTO2);
        pedidoItemDTO1.setId(null);
        assertThat(pedidoItemDTO1).isNotEqualTo(pedidoItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pedidoItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pedidoItemMapper.fromId(null)).isNull();
    }
}
