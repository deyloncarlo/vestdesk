package br.com.vestdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vestdesk.domain.Produto;

/**
 * Spring Data JPA repository for the Produto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>
{
	@Query("select distinct produto from Produto produto")
	List<Produto> findAllWithEagerRelationships();

	@Query("select produto from Produto produto where produto.id =:id")
	Produto findOneWithEagerRelationships(@Param("id") Long id);

}
