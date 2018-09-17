package br.com.vestdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vestdesk.domain.ConfiguracaoProduto;

/**
 * Spring Data JPA repository for the ConfiguracaoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfiguracaoProdutoRepository extends JpaRepository<ConfiguracaoProduto, Long>
{

	@Query("SELECT configuracaoProduto FROM ConfiguracaoProduto configuracaoProduto WHERE configuracaoProduto.modeloVestuario.id = ?1")
	List<ConfiguracaoProduto> findByModeloVestuario(Long idModeloVestuario);

}
