package br.com.vestdesk.repository;

import br.com.vestdesk.domain.Cor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {

}
