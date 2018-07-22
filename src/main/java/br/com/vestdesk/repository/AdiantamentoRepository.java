package br.com.vestdesk.repository;

import br.com.vestdesk.domain.Adiantamento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Adiantamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdiantamentoRepository extends JpaRepository<Adiantamento, Long> {

}
