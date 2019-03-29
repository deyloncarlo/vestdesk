package br.com.vestdesk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Notificacao;
import br.com.vestdesk.domain.User;
import br.com.vestdesk.repository.NotificacaoRepository;
import br.com.vestdesk.service.dto.NotificacaoDTO;
import br.com.vestdesk.service.mapper.NotificacaoMapper;

@Service
@Transactional
public class NotificacaoService
{

	private final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

	private final NotificacaoRepository notificacaoRepository;

	private final NotificacaoMapper notificacaoMapper;

	private final UserService userService;

	private final EntityManager em;

	public NotificacaoService(NotificacaoRepository notificacaoRepository, UserService userService, EntityManager em,
			NotificacaoMapper notificacaoMapper)
	{
		this.notificacaoRepository = notificacaoRepository;
		this.userService = userService;
		this.em = em;
		this.notificacaoMapper = notificacaoMapper;
	}

	public Page<NotificacaoDTO> findAll(Pageable pageable, Long userId)
	{
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Notificacao> criteria = criteriaBuilder.createQuery(Notificacao.class);
		Root<Notificacao> root = criteria.from(Notificacao.class);

		Predicate userPredicate = criteriaBuilder.equal(root.get("usuario"), this.userService.getCurrentUser());

		criteria.where(userPredicate);
		List<Notificacao> listNotificacao = this.em.createQuery(criteria).getResultList();
		Page<Notificacao> page = new PageImpl<>(listNotificacao, pageable, listNotificacao.size());
		return page.map(this.notificacaoMapper::toDto);
	}

	public Page<NotificacaoDTO> setReadNotifications(List<NotificacaoDTO> list)
	{
		return new PageImpl<>(new ArrayList());
	}

	public void generateNotifications(String notificationTitle)
	{
		List<Notificacao> listNotification = new ArrayList<>();
		List<User> listUser = this.userService.getAllActivesUser();
		listUser.forEach(new Consumer<User>()
		{
			@Override
			public void accept(User user)
			{
				Notificacao newNotification = new Notificacao();
				newNotification.setTextoNotificacao(notificationTitle);
				newNotification.setDataCriacao(LocalDate.now());
				newNotification.setVisualizado(false);
				newNotification.setUsuario(user);
				listNotification.add(newNotification);
			}
		});

		this.notificacaoRepository.save(listNotification);
	}

}
