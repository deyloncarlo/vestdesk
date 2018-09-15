package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produto and its DTO ProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = {ConfiguracaoProdutoMapper.class, CorMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

    @Mapping(source = "configuracaoProduto.id", target = "configuracaoProdutoId")
    ProdutoDTO toDto(Produto produto);

    @Mapping(source = "configuracaoProdutoId", target = "configuracaoProduto")
    Produto toEntity(ProdutoDTO produtoDTO);

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
