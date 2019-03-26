package br.com.vestdesk.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificacaoResource
{
	private final Logger log = LoggerFactory.getLogger(NotificacaoResource.class);

	private static final String ENTITY_NAME = "notificacao";

	public NotificacaoResource()
	{
	}
}
