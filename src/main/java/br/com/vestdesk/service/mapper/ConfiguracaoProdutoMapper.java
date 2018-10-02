package br.com.vestdesk.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;

/**
 * Mapper for the entity ConfiguracaoProduto and its DTO ConfiguracaoProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = { ModeloVestuarioMapper.class, MaterialTamanhoMapper.class })
public interface ConfiguracaoProdutoMapper
{

	@Mapping(source = "modeloVestuario.id", target = "modeloVestuarioId")
	ConfiguracaoProdutoDTO toDto(ConfiguracaoProduto configuracaoProduto);

	// @Mapping(target = "listaMaterialTamanhos", ignore = true)
	@Mapping(source = "modeloVestuarioId", target = "modeloVestuario")
	ConfiguracaoProduto toEntity(ConfiguracaoProdutoDTO configuracaoProdutoDTO);

	List<ConfiguracaoProdutoDTO> toListDto(List<ConfiguracaoProduto> configuracaoProduto);

	List<ConfiguracaoProduto> toListEntity(List<ConfiguracaoProdutoDTO> configuracaoProduto);

	default ConfiguracaoProduto fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		ConfiguracaoProduto configuracaoProduto = new ConfiguracaoProduto();
		configuracaoProduto.setId(id);
		return configuracaoProduto;
	}
}
