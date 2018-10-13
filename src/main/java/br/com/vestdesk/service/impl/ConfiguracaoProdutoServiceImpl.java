package br.com.vestdesk.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.repository.ConfiguracaoProdutoRepository;
import br.com.vestdesk.service.ConfiguracaoProdutoService;
import br.com.vestdesk.service.MaterialTamanhoService;
import br.com.vestdesk.service.ProdutoService;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;
import br.com.vestdesk.service.mapper.ConfiguracaoProdutoMapper;

/**
 * Service Implementation for managing ConfiguracaoProduto.
 */
@Service
@Transactional
public class ConfiguracaoProdutoServiceImpl implements ConfiguracaoProdutoService
{

	private final Logger log = LoggerFactory.getLogger(ConfiguracaoProdutoServiceImpl.class);

	private final ConfiguracaoProdutoRepository configuracaoProdutoRepository;

	private final ConfiguracaoProdutoMapper configuracaoProdutoMapper;

	private final MaterialTamanhoService materialTamanhoService;

	private final ProdutoService produtoService;

	public ConfiguracaoProdutoServiceImpl(ConfiguracaoProdutoRepository configuracaoProdutoRepository,
			ConfiguracaoProdutoMapper configuracaoProdutoMapper, MaterialTamanhoService materialTamanhoService,
			ProdutoService produtoService)
	{
		this.configuracaoProdutoRepository = configuracaoProdutoRepository;
		this.configuracaoProdutoMapper = configuracaoProdutoMapper;
		this.materialTamanhoService = materialTamanhoService;
		this.produtoService = produtoService;
	}

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ConfiguracaoProdutoDTO save(ConfiguracaoProdutoDTO configuracaoProdutoDTO)
	{
		this.log.debug("Request to save ConfiguracaoProduto : {}", configuracaoProdutoDTO);
		ConfiguracaoProduto configuracaoProduto = this.configuracaoProdutoMapper.toEntity(configuracaoProdutoDTO);
		configuracaoProduto = this.configuracaoProdutoRepository.save(configuracaoProduto);
		return this.configuracaoProdutoMapper.toDto(configuracaoProduto);
	}

	/**
	 * Get all the configuracaoProdutos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ConfiguracaoProdutoDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all ConfiguracaoProdutos");
		return this.configuracaoProdutoRepository.findAll(pageable).map(this.configuracaoProdutoMapper::toDto);
	}

	/**
	 * Get one configuracaoProduto by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public ConfiguracaoProdutoDTO findOne(Long id)
	{
		this.log.debug("Request to get ConfiguracaoProduto : {}", id);
		ConfiguracaoProduto configuracaoProduto = this.configuracaoProdutoRepository.findOne(id);
		return this.configuracaoProdutoMapper.toDto(configuracaoProduto);
	}

	/**
	 * Delete the configuracaoProduto by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id)
	{
		this.log.debug("Request to delete ConfiguracaoProduto : {}", id);
		this.configuracaoProdutoRepository.delete(id);
	}

	@Override
	public ConfiguracaoProdutoDTO saveList(Set<ConfiguracaoProduto> listaConfiguracaoProduto,
			ModeloVestuario modeloVestuario)
	{
		// for (ConfiguracaoProduto configuracaoProduto :
		// listaConfiguracaoProduto)
		// {
		// configuracaoProduto.setModeloVestuario(modeloVestuario);
		// if (configuracaoProduto.getId() != null)
		// {
		// ConfiguracaoProduto v_configuracaoProdutoEncontrado =
		// this.configuracaoProdutoRepository
		// .findOne(configuracaoProduto.getId());
		//
		// Set<MaterialTamanho> listaMaterialTamanhoRemovidos = new HashSet<>();
		//
		// for (MaterialTamanho v_materialTamanho :
		// v_configuracaoProdutoEncontrado.getListaMaterialTamanhos())
		// {
		// if (v_materialTamanho.getId() != null
		// &&
		// !configuracaoProduto.getListaMaterialTamanhos().contains(v_materialTamanho))
		// {
		// listaMaterialTamanhoRemovidos.add(v_materialTamanho);
		// }
		// }
		// this.materialTamanhoService.delete(listaMaterialTamanhoRemovidos);
		// }
		// if (configuracaoProduto.getListaMaterialTamanhos() != null)
		// {
		// this.materialTamanhoService.save(configuracaoProduto.getListaMaterialTamanhos(),
		// configuracaoProduto);
		// }
		// this.configuracaoProdutoRepository.save(configuracaoProduto);
		// }
		return null;
	}

	@Override
	public void delete(Set<ConfiguracaoProduto> listaConfiguracaoProduto)
	{
		// for (ConfiguracaoProduto configuracaoProduto :
		// listaConfiguracaoProduto)
		// {
		// this.materialTamanhoService.delete(configuracaoProduto.getListaMaterialTamanhos());
		// this.configuracaoProdutoRepository.delete(configuracaoProduto);
		// }

	}

	@Override
	public List<ConfiguracaoProdutoDTO> filtrar(Long idModeloVestuario)
	{
		List<ConfiguracaoProduto> listaConfiguracaoProduto = this.configuracaoProdutoRepository
				.findByModeloVestuario(idModeloVestuario);

		return this.configuracaoProdutoMapper.toListDto(listaConfiguracaoProduto);
	}
}
