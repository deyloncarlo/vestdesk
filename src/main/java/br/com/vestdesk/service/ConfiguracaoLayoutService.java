package br.com.vestdesk.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.repository.ConfiguracaoLayoutRepository;
import br.com.vestdesk.service.dto.ConfiguracaoLayoutDTO;

/**
 * Service for managing ConfiguracaoProduto.
 */
@Service
@Transactional
public class ConfiguracaoLayoutService
{

	private final LayoutService layoutService;

	private final ConfiguracaoLayoutRepository configuracaoLayoutRepository;

	public ConfiguracaoLayoutService(LayoutService layoutService,
			ConfiguracaoLayoutRepository configuracaoLayoutRepository)
	{
		this.layoutService = layoutService;
		this.configuracaoLayoutRepository = configuracaoLayoutRepository;
	}

	/**
	 * Save a configuracaoProduto.
	 *
	 * @param configuracaoProdutoDTO the entity to save
	 * @return the persisted entity
	 */
	public ConfiguracaoLayoutDTO save(ConfiguracaoLayoutDTO configuracaoLayoutDto)
	{
		return null;
	}

	public void save(Set<ConfiguracaoLayout> listaConfiguracaoLayout)
	{
		for (ConfiguracaoLayout configuracaoLayout : listaConfiguracaoLayout)
		{
			Layout layoutEncontrado = this.layoutService.getById(configuracaoLayout.getLayout().getId());
			configuracaoLayout.setLayout(layoutEncontrado);
			this.configuracaoLayoutRepository.save(configuracaoLayout);
		}
	}

	/**
	 * Delete the "id" configuracaoProduto.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
	}

	public void delete(Set<ConfiguracaoLayout> listaConfiguracaoLayout)
	{
		for (ConfiguracaoLayout configuracaoLayout : listaConfiguracaoLayout)
		{
			this.configuracaoLayoutRepository.delete(configuracaoLayout);
		}
	}
}
