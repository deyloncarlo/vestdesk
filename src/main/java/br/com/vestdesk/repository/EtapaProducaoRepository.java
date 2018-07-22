package br.com.vestdesk.repository;

import br.com.vestdesk.domain.EtapaProducao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EtapaProducao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaProducaoRepository extends JpaRepository<EtapaProducao, Long> {

}
