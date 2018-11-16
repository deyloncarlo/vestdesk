package br.com.vestdesk.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import br.com.vestdesk.service.dto.ConfiguracaoLayoutDTO;

/**
 * Mapper for the entity ConfiguracaoLayout and its DTO ConfiguracaoLayoutDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfiguracaoLayoutMapper
{

	default ConfiguracaoLayout fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		ConfiguracaoLayout configuracaoLayout = new ConfiguracaoLayout();
		configuracaoLayout.setId(id);
		return configuracaoLayout;
	}

	ConfiguracaoLayout toEntity(ConfiguracaoLayoutDTO configuracaoLayoutDTO);

	ConfiguracaoLayoutDTO toDto(ConfiguracaoLayout configuracaoLayout);

	List<ConfiguracaoLayoutDTO> toDto(List<ConfiguracaoLayout> content);
}
