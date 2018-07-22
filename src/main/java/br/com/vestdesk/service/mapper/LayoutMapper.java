package br.com.vestdesk.service.mapper;

import br.com.vestdesk.domain.*;
import br.com.vestdesk.service.dto.LayoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Layout and its DTO LayoutDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LayoutMapper extends EntityMapper<LayoutDTO, Layout> {



    default Layout fromId(Long id) {
        if (id == null) {
            return null;
        }
        Layout layout = new Layout();
        layout.setId(id);
        return layout;
    }
}
