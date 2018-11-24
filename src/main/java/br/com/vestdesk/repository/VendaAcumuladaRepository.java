package br.com.vestdesk.repository;

import br.com.vestdesk.domain.VendaAcumulada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VendaAcumulada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendaAcumuladaRepository extends JpaRepository<VendaAcumulada, Long> {

}
