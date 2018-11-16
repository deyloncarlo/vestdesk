package br.com.vestdesk.repository;

import br.com.vestdesk.domain.ConfiguracaoLayout;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConfiguracaoLayout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfiguracaoLayoutRepository extends JpaRepository<ConfiguracaoLayout, Long> {

}
