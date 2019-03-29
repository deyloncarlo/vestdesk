package br.com.vestdesk.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.vestdesk.service.NotificacaoService;
import br.com.vestdesk.service.dto.NotificacaoDTO;
import br.com.vestdesk.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class NotificacaoResource
{
	private final Logger log = LoggerFactory.getLogger(NotificacaoResource.class);

	private static final String ENTITY_NAME = "notificacao";

	private final NotificacaoService notificacaoService;

	public NotificacaoResource(NotificacaoService notificacaoService)
	{
		this.notificacaoService = notificacaoService;
	}

	@GetMapping("/notificacaos")
	@Timed
	public ResponseEntity<List<NotificacaoDTO>> getNotifications(Pageable pageable,
			@RequestParam(name = "userId", required = false) Long userId)
	{
		this.log.debug("REST request to get a page of Notifications");
		Page<NotificacaoDTO> page = this.notificacaoService.findAll(pageable, userId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notificacaos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@PostMapping("/notificacaos/setReadNotifications")
	@Timed
	public ResponseEntity<List<NotificacaoDTO>> setReadNotifications(Pageable pageable,
			@RequestParam(name = "listNotification", required = false) List<NotificacaoDTO> list)
	{
		this.log.debug("REST request to get a page of Notifications");
		Page<NotificacaoDTO> page = this.notificacaoService.setReadNotifications(list);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notificacaos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

}
