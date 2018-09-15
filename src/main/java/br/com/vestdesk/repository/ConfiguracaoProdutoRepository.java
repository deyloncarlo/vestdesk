package br.com.vestdesk.repository;

import br.com.vestdesk.domain.ConfiguracaoProduto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConfiguracaoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfiguracaoProdutoRepository extends JpaRepository<ConfiguracaoProduto, Long> {

}
