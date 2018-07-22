package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.FormaPagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FormaPagamento and its DTO FormaPagamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormaPagamentoMapper extends EntityMapper<FormaPagamentoDTO, FormaPagamento> {



    default FormaPagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(id);
        return formaPagamento;
    }
}
