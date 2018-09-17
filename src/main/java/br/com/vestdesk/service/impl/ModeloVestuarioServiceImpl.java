package br.com.vestdesk.service.impl;

import java.util.HashSet;
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
import br.com.vestdesk.repository.ModeloVestuarioRepository;
import br.com.vestdesk.service.ConfiguracaoProdutoService;
import br.com.vestdesk.service.ModeloVestuarioService;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;
import br.com.vestdesk.service.mapper.ModeloVestuarioMapper;

/**
 * Service Implementation for managing ModeloVestuario.
 */
@Service
@Transactional
public class ModeloVestuarioServiceImpl implements ModeloVestuarioService
{

	private final Logger log = LoggerFactory.getLogger(ModeloVestuarioServiceImpl.class);

	private final ModeloVestuarioRepository modeloVestuarioRepository;

	private final ModeloVestuarioMapper modeloVestuarioMapper;

	private final ConfiguracaoProdutoService configuracaoProdutoService;

	private final ConfiguracaoProdutoRepository configuracaoProdutoRepository;

	public ModeloVestuarioServiceImpl(ModeloVestuarioRepository modeloVestuarioRepository,
			ModeloVestuarioMapper modeloVestuarioMapper, ConfiguracaoProdutoService configuracaoProdutoService,
			ConfiguracaoProdutoRepository configuracaoProdutoRepository)
	{
		this.modeloVestuarioRepository = modeloVestuarioRepository;
		this.modeloVestuarioMapper = modeloVestuarioMapper;
		this.configuracaoProdutoService = configuracaoProdutoService;
		this.configuracaoProdutoRepository = configuracaoProdutoRepository;
	}

	/**
	 * Save a modeloVestuario.
	 *
	 * @param modeloVestuarioDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ModeloVestuarioDTO save(ModeloVestuarioDTO modeloVestuarioDTO)
	{
		this.log.debug("Request to save ModeloVestuario : {}", modeloVestuarioDTO);
		ModeloVestuario modeloVestuario = this.modeloVestuarioMapper.toEntity(modeloVestuarioDTO);

		Set<ConfiguracaoProduto> v_listaConfiguracaoProduto = null;
		if (modeloVestuario.getListaConfiguracaoProdutos() != null)
		{
			v_listaConfiguracaoProduto = new HashSet<>(modeloVestuario.getListaConfiguracaoProdutos());
			modeloVestuario.getListaConfiguracaoProdutos().clear();
		}
		else
		{
			v_listaConfiguracaoProduto = new HashSet<>();
		}

		Set<ConfiguracaoProduto> v_listaConfiguracaoProdutoRemovidos = new HashSet<>();

		if (modeloVestuario.getId() != null)
		{
			ModeloVestuario v_modeloEncontrado = this.modeloVestuarioRepository.findOne(modeloVestuario.getId());

			for (ConfiguracaoProduto configuracaoProduto : v_modeloEncontrado.getListaConfiguracaoProdutos())
			{
				if (configuracaoProduto.getId() != null && !v_listaConfiguracaoProduto.contains(configuracaoProduto))
				{
					v_listaConfiguracaoProdutoRemovidos.add(configuracaoProduto);
				}
			}

			this.configuracaoProdutoService.delete(v_listaConfiguracaoProdutoRemovidos);

		}

		modeloVestuario = this.modeloVestuarioRepository.save(modeloVestuario);

		this.configuracaoProdutoService.saveList(v_listaConfiguracaoProduto, modeloVestuario);

		return this.modeloVestuarioMapper.toDto(modeloVestuario);
	}

	/**
	 * Get all the modeloVestuarios.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ModeloVestuarioDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all ModeloVestuarios");
		return this.modeloVestuarioRepository.findAll(pageable).map(this.modeloVestuarioMapper::toDto);
	}

	/**
	 * Get one modeloVestuario by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public ModeloVestuarioDTO findOne(Long id)
	{
		this.log.debug("Request to get ModeloVestuario : {}", id);
		ModeloVestuario modeloVestuario = this.modeloVestuarioRepository.findOne(id);
		return this.modeloVestuarioMapper.toDto(modeloVestuario);
	}

	/**
	 * Delete the modeloVestuario by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id)
	{
		this.log.debug("Request to delete ModeloVestuario : {}", id);
		Set<ConfiguracaoProduto> v_listaConfiguracao = this.modeloVestuarioRepository.findOne(id)
				.getListaConfiguracaoProdutos();
		this.configuracaoProdutoService.delete(v_listaConfiguracao);
		this.modeloVestuarioRepository.delete(id);
	}
}
