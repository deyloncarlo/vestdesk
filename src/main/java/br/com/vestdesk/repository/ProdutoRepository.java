package br.com.vestdesk.repository;

import br.com.vestdesk.domain.Produto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Produto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("select distinct produto from Produto produto left join fetch produto.listaCors")
    List<Produto> findAllWithEagerRelationships();

    @Query("select produto from Produto produto left join fetch produto.listaCors where produto.id =:id")
    Produto findOneWithEagerRelationships(@Param("id") Long id);

}
