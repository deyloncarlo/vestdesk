package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.ModeloVestuario;
import br.com.vestdesk.service.dto.ModeloVestuarioDTO;

/**
 * Mapper for the entity ModeloVestuario and its DTO ModeloVestuarioDTO.
 */
@Mapper(componentModel = "spring", uses = { ConfiguracaoProdutoMapper.class })
public interface ModeloVestuarioMapper extends EntityMapper<ModeloVestuarioDTO, ModeloVestuario>
{

	@Override
	// @Mapping(target = "listaConfiguracaoProdutos", ignore = true)
	ModeloVestuario toEntity(ModeloVestuarioDTO modeloVestuarioDTO);

	default ModeloVestuario fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		ModeloVestuario modeloVestuario = new ModeloVestuario();
		modeloVestuario.setId(id);
		return modeloVestuario;
	}
}
