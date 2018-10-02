package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Material;
import br.com.vestdesk.service.dto.MaterialDTO;

/**
 * Mapper for the entity Material and its DTO MaterialDTO.
 */
@Mapper(componentModel = "spring", uses = { CorMapper.class })
public interface MaterialMapper
{

	// @Mapping(source = "corId", target = "cor.id")
	Material toEntity(MaterialDTO materialDTO);

	// @Mapping(source = "cor", target = "corId")
	MaterialDTO toDto(Material material);

	default Material fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Material material = new Material();
		material.setId(id);
		return material;
	}
}
