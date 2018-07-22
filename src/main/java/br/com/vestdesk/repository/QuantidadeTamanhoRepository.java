package br.com.vestdesk.repository;

import br.com.vestdesk.domain.QuantidadeTamanho;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the QuantidadeTamanho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuantidadeTamanhoRepository extends JpaRepository<QuantidadeTamanho, Long> {

}
