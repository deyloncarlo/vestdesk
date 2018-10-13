package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;

/**
 * Mapper for the entity MaterialTamanho and its DTO MaterialTamanhoDTO.
 */
@Mapper(componentModel = "spring", uses = { MaterialMapper.class, ProdutoMapper.class })
public interface MaterialTamanhoMapper
{

	@Mapping(source = "produto.id", target = "produtoId")
	@Mapping(source = "material.id", target = "materialId")
	MaterialTamanhoDTO toDto(MaterialTamanho materialTamanho);

	@Mapping(source = "produtoId", target = "produto")
	@Mapping(source = "materialId", target = "material")
	MaterialTamanho toEntity(MaterialTamanhoDTO materialTamanhoDTO);

	default MaterialTamanho fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		MaterialTamanho materialTamanho = new MaterialTamanho();
		materialTamanho.setId(id);
		return materialTamanho;
	}
}
