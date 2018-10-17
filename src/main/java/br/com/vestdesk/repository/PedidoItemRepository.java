package br.com.vestdesk.repository;

import br.com.vestdesk.domain.PedidoItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PedidoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

}
