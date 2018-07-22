package br.com.vestdesk.repository;

import br.com.vestdesk.domain.Layout;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Layout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LayoutRepository extends JpaRepository<Layout, Long> {

}
