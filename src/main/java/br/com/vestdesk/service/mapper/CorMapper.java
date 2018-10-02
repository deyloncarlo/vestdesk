package br.com.vestdesk.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.service.dto.CorDTO;

/**
 * Mapper for the entity Cor and its DTO CorDTO.
 */
@Mapper(componentModel = "spring", uses = { ProdutoMapper.class })
public interface CorMapper
{

	Cor toEntity(CorDTO corDTO);

	List<Cor> toEntity(List<CorDTO> listaCorDTO);

	// Cor toEntityMin(CorMinDTO corMinDto);

	CorDTO toDto(Cor cor);

	List<CorDTO> toListDto(List<Cor> listaCor);

	// CorMinDTO toMinDto(Cor cor);

	default Cor fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Cor cor = new Cor();
		cor.setId(id);
		return cor;
	}
}
