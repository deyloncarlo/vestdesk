package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MaterialTamanho and its DTO MaterialTamanhoDTO.
 */
@Mapper(componentModel = "spring", uses = {ConfiguracaoProdutoMapper.class, MaterialMapper.class})
public interface MaterialTamanhoMapper extends EntityMapper<MaterialTamanhoDTO, MaterialTamanho> {

    @Mapping(source = "configuracaoProduto.id", target = "configuracaoProdutoId")
    @Mapping(source = "material.id", target = "materialId")
    MaterialTamanhoDTO toDto(MaterialTamanho materialTamanho);

    @Mapping(source = "configuracaoProdutoId", target = "configuracaoProduto")
    @Mapping(source = "materialId", target = "material")
    MaterialTamanho toEntity(MaterialTamanhoDTO materialTamanhoDTO);

    default MaterialTamanho fromId(Long id) {
        if (id == null) {
            return null;
        }
        MaterialTamanho materialTamanho = new MaterialTamanho();
        materialTamanho.setId(id);
        return materialTamanho;
    }
}
