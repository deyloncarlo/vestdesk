package br.com.vestdesk.repository;

import br.com.vestdesk.domain.FormaPagamento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormaPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
