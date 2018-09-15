package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConfiguracaoProduto and its DTO ConfiguracaoProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeloVestuarioMapper.class})
public interface ConfiguracaoProdutoMapper extends EntityMapper<ConfiguracaoProdutoDTO, ConfiguracaoProduto> {

    @Mapping(source = "modeloVestuario.id", target = "modeloVestuarioId")
    ConfiguracaoProdutoDTO toDto(ConfiguracaoProduto configuracaoProduto);

    @Mapping(target = "listaMaterialTamanhos", ignore = true)
    @Mapping(source = "modeloVestuarioId", target = "modeloVestuario")
    ConfiguracaoProduto toEntity(ConfiguracaoProdutoDTO configuracaoProdutoDTO);

    default ConfiguracaoProduto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConfiguracaoProduto configuracaoProduto = new ConfiguracaoProduto();
        configuracaoProduto.setId(id);
        return configuracaoProduto;
    }
}
