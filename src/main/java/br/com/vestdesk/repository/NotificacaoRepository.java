package br.com.vestdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vestdesk.domain.Notificacao;

@SuppressWarnings("unused")
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>
{

}
