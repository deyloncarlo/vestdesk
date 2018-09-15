package br.com.vestdesk.repository;

import br.com.vestdesk.domain.MaterialTamanho;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MaterialTamanho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialTamanhoRepository extends JpaRepository<MaterialTamanho, Long> {

}
