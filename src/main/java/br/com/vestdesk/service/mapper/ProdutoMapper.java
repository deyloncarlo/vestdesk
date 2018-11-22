package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.service.dto.ProdutoDTO;
import br.com.vestdesk.service.dto.ProdutoMinDTO;

/**
 * Mapper for the entity Produto and its DTO ProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = { CorMapper.class, MaterialTamanhoMapper.class })
public interface ProdutoMapper
{

	ProdutoDTO toDto(Produto produto);

	ProdutoMinDTO toMinDto(Produto produto);

	Produto toEntity(ProdutoDTO produtoDTO);

	Produto toEntity(ProdutoMinDTO produtoDTO);

	default Produto fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Produto produto = new Produto();
		produto.setId(id);
		return produto;
	}
}
