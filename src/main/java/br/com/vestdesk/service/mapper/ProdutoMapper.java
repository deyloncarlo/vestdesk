package br.com.vestdesk.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.service.dto.ProdutoDTO;

/**
 * Mapper for the entity Produto and its DTO ProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = { ConfiguracaoProdutoMapper.class, CorMapper.class })
public interface ProdutoMapper
{

	@Mapping(source = "configuracaoProduto.id", target = "configuracaoProdutoId")
	ProdutoDTO toDto(Produto produto);

	@Mapping(source = "configuracaoProdutoId", target = "configuracaoProduto")
	Produto toEntity(ProdutoDTO produtoDTO);

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
