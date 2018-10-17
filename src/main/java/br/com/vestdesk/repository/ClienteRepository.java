package br.com.vestdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vestdesk.domain.Cliente;

/**
 * Spring Data JPA repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>
{

	@Query("SELECT cliente FROM Cliente cliente WHERE nome = :nome")
	Page<Cliente> findAll(Pageable pageable, String nome);

}
