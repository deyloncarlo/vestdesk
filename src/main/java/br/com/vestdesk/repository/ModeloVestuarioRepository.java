package br.com.vestdesk.repository;

import br.com.vestdesk.domain.ModeloVestuario;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ModeloVestuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeloVestuarioRepository extends JpaRepository<ModeloVestuario, Long> {

}
